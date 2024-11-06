package com.wesploja.lojaweb.controller.dto.cart;

import com.wesploja.lojaweb.repository.ProdutosRepository;
import com.wesploja.lojaweb.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.UserDetails;

public record AddCartDTO(@NotNull
                         Long productId,
                         @NotNull
                         Integer quantity) {

}
