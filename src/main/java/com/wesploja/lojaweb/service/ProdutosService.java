package com.wesploja.lojaweb.service;

import com.wesploja.lojaweb.controller.dto.produtos.AddProdutosDTO;
import com.wesploja.lojaweb.controller.dto.produtos.AtualizarProdutoTO;
import com.wesploja.lojaweb.controller.dto.produtos.DadosprodutosDTO;
import com.wesploja.lojaweb.domain.loja.produtos.Category;
import com.wesploja.lojaweb.domain.loja.produtos.Produtos;
import com.wesploja.lojaweb.repository.CategoryRepository;
import com.wesploja.lojaweb.repository.ProdutosRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;



@Service
public class ProdutosService {
    @Autowired
    private ProdutosRepository produtosRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    public ResponseEntity<?> addProdutos(@Valid AddProdutosDTO produto, UriComponentsBuilder uri) {
        Category category = categoryRepository.findById(produto.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
       var produtos = new Produtos(produto, category);
        produtosRepository.save(produtos);
        var uriProdutos = uri.path("/produtos/{id}").buildAndExpand(produtos.getId()).toUri();
        return ResponseEntity.created(uriProdutos).body(new DadosprodutosDTO(produtos));
    }

    public ResponseEntity<?> getAllProdutos(Pageable pageable) {
        var produtos = produtosRepository.findAll(pageable);
        return ResponseEntity.ok(produtos.stream().map(DadosprodutosDTO::new));

    }

    public ResponseEntity<?> updateProdutos(@Valid AtualizarProdutoTO produto) {
        var produtos = produtosRepository.findById(produto.id())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produtos.update(produto);
        return ResponseEntity.ok(new DadosprodutosDTO(produtos));
    }

    public ResponseEntity<?> getProdutosById(long id) {
        var produtos = produtosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return ResponseEntity.ok(new DadosprodutosDTO(produtos));
    }
}
