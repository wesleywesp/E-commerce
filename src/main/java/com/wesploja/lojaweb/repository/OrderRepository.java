package com.wesploja.lojaweb.repository;

import com.wesploja.lojaweb.doman.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
