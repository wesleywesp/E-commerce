package com.wesploja.lojaweb.controller;

import com.wesploja.lojaweb.controller.dto.cart.AddCartDTO;
import com.wesploja.lojaweb.controller.dto.cart.UpdateProdutoDTO;
import com.wesploja.lojaweb.service.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cart")
@SecurityRequirement(name = "bearer-key")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createCart(@RequestBody @Valid AddCartDTO dto, UriComponentsBuilder uriBuilder
    , @AuthenticationPrincipal UserDetails user) {
        return cartService.createCart(dto, uriBuilder
        , user);
    }

    @GetMapping
    public ResponseEntity<?> getCart(@AuthenticationPrincipal UserDetails user) {
        return cartService.getCart(user);
    }

    @DeleteMapping
    @Transactional
    //Limpar carrinho
    public ResponseEntity<?> clearCart(@AuthenticationPrincipal UserDetails user) {
        return cartService.clearCart(user);
    }
    @PatchMapping("/{productId}")
    @Transactional
    public ResponseEntity<?> updateCart(@PathVariable Long productId, @RequestBody UpdateProdutoDTO quantity,
                                        @AuthenticationPrincipal UserDetails user) {
        return cartService.updateCart(productId, quantity , user);
    }
    @PostMapping("/checkout")
    @Transactional
    public ResponseEntity<?> checkout(@AuthenticationPrincipal UserDetails user) {
        return cartService.checkout(user);
    }
}
