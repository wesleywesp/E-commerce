package com.wesploja.lojaweb.doman.order;

import com.wesploja.lojaweb.doman.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
@Getter
@Setter
public class Order {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    private BigDecimal totalAmount;
    private LocalDate dateOrder;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


}
