package com.wesploja.lojaweb.domain.loja.produtos;

import com.wesploja.lojaweb.controller.dto.produtos.AddProdutosDTO;
import com.wesploja.lojaweb.controller.dto.produtos.AtualizarProdutoTO;
import jakarta.persistence.*;

import jakarta.validation.Valid;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Produtos {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String imageUrl;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Produtos(@Valid AddProdutosDTO produto , Category category) {
        this.name = produto.name();
        this.price = produto.price();
        this.description = produto.description();
        this.imageUrl = produto.imageUrl();
        this.quantity = produto.quantity();
        this.category = category;
    }

    public void update(@Valid AtualizarProdutoTO produto) {
        if(produto.name() != null) {
            this.name = produto.name();
        }
        if(produto.price() != null) {
            this.price = produto.price();
        }
        if(produto.description() != null) {
            this.description = produto.description();
        }
        if(produto.imageUrl() != null) {
            this.imageUrl = produto.imageUrl();
        }
        if (produto.quantity() != null) {
            this.quantity = produto.quantity();
        }
    }
}
