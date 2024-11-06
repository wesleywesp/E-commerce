package com.wesploja.lojaweb.controller.dto.cart;

import java.math.BigDecimal;

public record CartDetalherDTO(Long productId, String productName, int quantity, BigDecimal price) {


    public CartDetalherDTO(Integer id, String name, Integer quantity, BigDecimal price) {
        this((long) id, name, quantity, price);
    }
}

