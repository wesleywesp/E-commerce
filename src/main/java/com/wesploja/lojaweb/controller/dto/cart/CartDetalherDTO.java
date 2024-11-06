package com.wesploja.lojaweb.controller.dto.cart;

import com.wesploja.lojaweb.doman.loja.cart.Cart;
import com.wesploja.lojaweb.doman.loja.cart.CartItem;

import java.math.BigDecimal;
import java.util.List;
public record CartDetalherDTO(Long productId, String productName, int quantity, BigDecimal price) {


    public CartDetalherDTO(Integer id, String name, Integer quantity, BigDecimal price) {
        this((long) id, name, quantity, price);
    }
}

