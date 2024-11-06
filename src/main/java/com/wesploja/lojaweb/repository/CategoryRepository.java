package com.wesploja.lojaweb.repository;

import com.wesploja.lojaweb.doman.loja.produtos.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
