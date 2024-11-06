package com.wesploja.lojaweb.controller.dto.produtos;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record  AtualizarProdutoTO(@NotNull
                                  long id,
                                  String name,
                                  String description,
                                  String imageUrl,
                                  BigDecimal price,
                                  Integer quantity) {
}
