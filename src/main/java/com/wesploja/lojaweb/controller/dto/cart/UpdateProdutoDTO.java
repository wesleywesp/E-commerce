package com.wesploja.lojaweb.controller.dto.cart;

import jakarta.validation.constraints.NotNull;

public record UpdateProdutoDTO(@NotNull Integer quantity) {
}
