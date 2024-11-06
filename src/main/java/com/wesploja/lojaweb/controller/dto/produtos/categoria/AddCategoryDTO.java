package com.wesploja.lojaweb.controller.dto.produtos.categoria;

import com.wesploja.lojaweb.doman.loja.produtos.MainCategory;
import com.wesploja.lojaweb.doman.loja.produtos.SubCategory;
import jakarta.validation.constraints.NotBlank;

public record AddCategoryDTO(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Main Category is required")
        MainCategory mainCategory,

        @NotBlank(message = "Sub Category is required")
        SubCategory subCategory,

        String description
) {
}
