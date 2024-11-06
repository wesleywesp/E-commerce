package com.wesploja.lojaweb.repository;

import com.wesploja.lojaweb.domain.loja.produtos.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
