package com.wesploja.lojaweb.controller;

import com.wesploja.lojaweb.controller.dto.produtos.AddProdutosDTO;
import com.wesploja.lojaweb.controller.dto.produtos.AtualizarProdutoTO;
import com.wesploja.lojaweb.controller.dto.produtos.categoria.AddCategoryDTO;
import com.wesploja.lojaweb.service.CategoryService;
import com.wesploja.lojaweb.service.ProdutosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/categorias")
public class ProdutosController {

    @Autowired
    private ProdutosService produtosService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> addCategory(@RequestBody AddCategoryDTO addCategoryDTO, UriComponentsBuilder uri) {
        return categoryService.addCategory(addCategoryDTO, uri);
    }

    @PostMapping("/produtos")
    @Transactional
    public ResponseEntity<?> addProdutos(@RequestBody @Valid AddProdutosDTO produto , UriComponentsBuilder uri) {
        return produtosService.addProdutos(produto,uri);
    }

    @GetMapping("/produtos")
    public ResponseEntity<?> getAllProdutos(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        return produtosService.getAllProdutos(pageable);
    }
    @PutMapping("/produtos")
    @Transactional
    public ResponseEntity<?> updateProdutos(@RequestBody @Valid AtualizarProdutoTO produto) {
        return produtosService.updateProdutos(produto);
    }
    @GetMapping("/produtos/{id}")
    public ResponseEntity<?> getProdutosById(@PathVariable Long id) {
        return produtosService.getProdutosById(id);
    }
}
