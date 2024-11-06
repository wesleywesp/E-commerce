package com.wesploja.lojaweb.doman.loja.produtos;

import com.wesploja.lojaweb.controller.dto.produtos.categoria.AddCategoryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private MainCategory mainCategory;
    @Enumerated(EnumType.STRING)
    private SubCategory subCategory;

    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produtos> produtos = new ArrayList<>();

    public Category(AddCategoryDTO addCategoryDTO) {
        this.name = addCategoryDTO.name();
        this.mainCategory = addCategoryDTO.mainCategory();
        this.subCategory = addCategoryDTO.subCategory();
        this.description = addCategoryDTO.description();
    }
}
