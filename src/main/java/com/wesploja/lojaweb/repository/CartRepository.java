package com.wesploja.lojaweb.repository;

import com.wesploja.lojaweb.domain.loja.cart.Cart;
import com.wesploja.lojaweb.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
