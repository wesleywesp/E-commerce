package com.wesploja.lojaweb.repository;

import com.wesploja.lojaweb.domain.loja.produtos.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<Produtos, Long> {

}
