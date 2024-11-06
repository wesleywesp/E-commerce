package com.wesploja.lojaweb.controller.dto.produtos;

import com.wesploja.lojaweb.doman.loja.produtos.Produtos;

public record DadosprodutosDTO(Long id,
                                String name,
                                String description,
                                String imageUrl,
                                Integer quantity,
                                String categoryName,
                                String mainCategoryName,
                                String subCategoryName) {
    public DadosprodutosDTO(Produtos produtos) {
        this(produtos.getId(),
             produtos.getName(),
             produtos.getDescription(),
             produtos.getImageUrl(),
             produtos.getQuantity(),
             produtos.getCategory().getName(),
                produtos.getCategory().getMainCategory().name(),
                produtos.getCategory().getSubCategory().name());
    }
}
