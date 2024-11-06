package com.wesploja.lojaweb.controller.dto.produtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AddProdutosDTO(@NotBlank(message ="Name is required")
                                String name,
                                @NotBlank(message ="Description is required")
                                String description,
                                @NotBlank(message ="Image URL is required")
                                String imageUrl,
                                @NotNull(message ="Price is required")
                                BigDecimal price,
                                @NotNull(message ="Quantity is required")
                                Integer quantity,
                                @NotNull(message = "Category ID is required")
                                long categoryId
                             ) {
}
