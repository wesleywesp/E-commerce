package com.wesploja.lojaweb.doman.order;

import com.wesploja.lojaweb.doman.loja.produtos.Produtos;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Produtos product;

    private Integer quantity;
    private BigDecimal price;

    public void setProdutos(Produtos produtos) {
        this.product = produtos;
    }
}
