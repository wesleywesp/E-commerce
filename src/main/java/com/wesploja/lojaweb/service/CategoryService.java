package com.wesploja.lojaweb.service;

import com.wesploja.lojaweb.controller.dto.produtos.categoria.AddCategoryDTO;
import com.wesploja.lojaweb.domain.loja.produtos.Category;
import com.wesploja.lojaweb.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity<?> addCategory(AddCategoryDTO addCategoryDTO , UriComponentsBuilder uri) {
        var category = new Category(addCategoryDTO);
        categoryRepository.save(category);
        var uriProdutos = uri.path("/produtos/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uriProdutos).body(category);
    }
}
