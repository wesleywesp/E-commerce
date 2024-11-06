package com.wesploja.lojaweb.controller.dto.order;

import com.wesploja.lojaweb.domain.order.OrderItem;

import java.math.BigDecimal;

public record DetalharOrderItemsDTO(String productName, int quantity, BigDecimal price) {
    public DetalharOrderItemsDTO(OrderItem item) {
        this(item.getProduct().getName(), item.getQuantity(), item.getPrice());
    }
}
