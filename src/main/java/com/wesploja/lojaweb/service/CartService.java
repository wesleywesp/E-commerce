package com.wesploja.lojaweb.service;

import com.wesploja.lojaweb.controller.dto.cart.AddCartDTO;
import com.wesploja.lojaweb.controller.dto.cart.CartDetalherDTO;
import com.wesploja.lojaweb.controller.dto.cart.CartTotal;
import com.wesploja.lojaweb.controller.dto.cart.UpdateProdutoDTO;
import com.wesploja.lojaweb.doman.loja.cart.Cart;
import com.wesploja.lojaweb.doman.loja.cart.CartItem;
import com.wesploja.lojaweb.doman.order.Order;
import com.wesploja.lojaweb.doman.order.OrderItem;
import com.wesploja.lojaweb.doman.order.OrderStatus;
import com.wesploja.lojaweb.infra.exception.TratarCkechoutCart;
import com.wesploja.lojaweb.infra.validation.validarchechoutcart.ValidarCartEUser;
import com.wesploja.lojaweb.infra.validation.validarchechoutcart.ValidarUserChechoutCart;
import com.wesploja.lojaweb.repository.CartRepository;
import com.wesploja.lojaweb.repository.OrderRepository;
import com.wesploja.lojaweb.repository.ProdutosRepository;
import com.wesploja.lojaweb.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

        @Autowired
        private CartRepository cartRepository;
        @Autowired
        private ProdutosRepository productRepository;
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private List<ValidarUserChechoutCart> validarUserChechoutCart;

        @Autowired
        private ValidarCartEUser validarCartEUser;
        @Autowired
        private OrderRepository orderRepository;


        public ResponseEntity<?> createCart(@Valid AddCartDTO dto, UriComponentsBuilder uriBuilder, UserDetails users) {
                // 1. Validar o produto solicitado
                var produto = productRepository.findById(dto.productId())
                        .orElseThrow(() -> new IllegalArgumentException("product not found"));

                // 2. Verificar apenas o estoque disponível, sem atualizar a quantidade
                if (produto.getQuantity() < dto.quantity()) {
                        throw new IllegalArgumentException("quantity not available");
                }

                // 3. Obter ou criar o carrinho do usuário
                var user = userRepository.findByUsername(users.getUsername())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
                var cart = cartRepository.findByUser(user).orElseGet(() -> new Cart(user));

                // 4. Adicionar ou atualizar item no carrinho
                var item = cart.getItems().stream()
                        .filter(cartItem -> cartItem.getProdutos().equals(produto))
                        .findFirst();

                if (item.isPresent()) {
                        // Atualizar quantidade do item se o produto já está no carrinho
                        item.get().setQuantity(item.get().getQuantity() + dto.quantity());
                } else {
                        // Adicionar novo item ao carrinho e associar o cart
                        CartItem newItem = new CartItem(produto, dto.quantity());
                        newItem.setCart(cart); // Define o carrinho como o pai deste item
                        cart.getItems().add(newItem);
                }

                // 5. Recalcular lor total do carrinho
                BigDecimal totalPrice = BigDecimal.valueOf(cart.getItems().stream()
                        .mapToDouble(cartItem -> cartItem.getProdutos().getPrice().doubleValue() * cartItem.getQuantity())
                        .sum());
                cart.setTotalPrice(totalPrice);

                // 6. Salvar o carrinho atualizado
                cartRepository.save(cart);
                var uri = uriBuilder.path("/cart/{id}").buildAndExpand(user.getId()).toUri();

                // 7. Retornar resposta
                return ResponseEntity.created(uri).body("Produto adicionado ao carrinho com sucesso.");
        }



         public ResponseEntity<?> getCart(UserDetails userDetails) {
                // Encontrar o usuário autenticado e o carrinho
                var cart = (Cart)validarCartEUser.AutenticarEencontrarUserECart(userDetails);
                        // Mapeia os itens do carrinho para CartItemDTO
                        List<CartDetalherDTO> cartItems = cart.getItems().stream()
                                .map(item -> new CartDetalherDTO(
                                        item.getProdutos().getId(),
                                        item.getProdutos().getName(),
                                        item.getQuantity(),
                                        item.getProdutos().getPrice()
                                ))
                                .collect(Collectors.toList());

                        return ResponseEntity.ok(new CartTotal(cartItems, cart.getTotalPrice()));
        }

        public ResponseEntity<?> clearCart(UserDetails users) {
                // Encontrar o usuário autenticado e o carrinho
                var cart = (Cart) validarCartEUser.AutenticarEencontrarUserECart(users);
                //Limpar o carrinho

                cart.getItems().clear();
                cart.setTotalPrice(BigDecimal.ZERO);
                cartRepository.save(cart);
                return ResponseEntity.noContent().build();

        }

        public ResponseEntity<?> updateCart(Long productId, UpdateProdutoDTO quantity, UserDetails user) {

                // Encontrar o usuário autenticado e o carrinho
              var cart = (Cart) validarCartEUser.AutenticarEencontrarUserECart(user);
                // Encontrar o item no carrinho
                var item = cart.getItems().stream()
                        .filter(cartItem -> cartItem.getProdutos().getId().equals(productId))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
                var atualizar = quantity.quantity();
                if (atualizar < 0) {
                        throw new IllegalArgumentException("quantity must be greater than zero");
                } else if (atualizar == 0) {
                        cart.getItems().remove(item);
                } else {
                        item.setQuantity(atualizar);
                }
                return ResponseEntity.ok("Carrinho atualizado com sucesso.");
        }

        public ResponseEntity<?> checkout(UserDetails userDetails) {
                //1. Encontrar o usuário autenticado e o carrinho
                var user = userRepository.findByUsername(userDetails.getUsername())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
                var cart = (Cart) validarCartEUser.AutenticarEencontrarUserECart(userDetails);
                //2.verificar se carinho possui itens
                //3.verificar se o usuário possui endereço
                validarUserChechoutCart.forEach(validar -> validar.validar(userDetails));
                //4.validar estoque dos produtos e calcular o total
                BigDecimal totalPrice = BigDecimal.ZERO;
                for (CartItem item : cart.getItems()) {
                        var produto = item.getProdutos();
                        if (produto.getQuantity() < item.getQuantity()) {
                                throw new TratarCkechoutCart("quantity not available");
                        }
                        produto.setQuantity(produto.getQuantity() - item.getQuantity());
                        totalPrice = totalPrice.add(produto.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                }
                //5.criar pedido
                Order order = new Order();
                order.setUser(user);
                order.setTotalAmount(totalPrice);
                order.setStatus(OrderStatus.PENDING);
                order.setDateOrder(java.time.LocalDate.now());

                //6.obter os itens do carrinho e associar ao pedido
                List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setOrder(order);
                        orderItem.setProdutos(cartItem.getProdutos());
                        orderItem.setQuantity(cartItem.getQuantity());
                        orderItem.setPrice(cartItem.getProdutos().getPrice());
                        return orderItem;
                }).collect(Collectors.toList());
                //6.salvar pedido
                order.setItems(orderItems);
                orderRepository.save(order);

                return ResponseEntity.ok("Checkout realizado com sucesso.");
        }


}

