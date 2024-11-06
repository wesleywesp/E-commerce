package com.wesploja.lojaweb.domain.loja.cart;

import com.wesploja.lojaweb.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalPrice = BigDecimal.ZERO;

    // Um carrinho pode ter muitos itens, mas cada item no carrinho está relacionado a um produto específico
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Cart(User user) {
        this.user = user;
    }

    public Class<CartItem> getProdutos() {
        return this.items.get(0).getCart().getProdutos();
    }
}
