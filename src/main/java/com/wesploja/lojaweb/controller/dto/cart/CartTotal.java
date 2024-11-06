package com.wesploja.lojaweb.controller.dto.cart;

import java.math.BigDecimal;
import java.util.List;

public record CartTotal(List<CartDetalherDTO> cartItems, BigDecimal totalPrice) {

    public CartTotal(List<CartDetalherDTO> cartItems, BigDecimal totalPrice) {
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
    }
}
