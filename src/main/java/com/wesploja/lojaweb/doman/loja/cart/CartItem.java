package com.wesploja.lojaweb.doman.loja.cart;

import com.wesploja.lojaweb.doman.loja.produtos.Produtos;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
public class CartItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Cada item de carrinho está associado a um carrinho
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    // Cada item de carrinho está relacionado a um produto específico
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Produtos produtos;

    private Integer quantity; // Quantidade do produto no carrinho

    private BigDecimal price; // Preço do produto no momento da adição ao carrinho

    public CartItem(Produtos produto, @NotNull Integer quantity) {
        this.produtos = produto;
        this.quantity = quantity;
        this.price = produto.getPrice();
    }
}
