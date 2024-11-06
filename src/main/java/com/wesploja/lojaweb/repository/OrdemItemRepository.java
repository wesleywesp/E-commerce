package com.wesploja.lojaweb.repository;

import com.wesploja.lojaweb.doman.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdemItemRepository extends JpaRepository<OrderItem, Long> {
}
