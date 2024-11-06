package com.wesploja.lojaweb.controller.dto.login;

import jakarta.validation.constraints.NotBlank;

public record LoginInputDTO(@NotBlank(message = "username is required")
                            String username,
                            @NotBlank(message = "password is required")
                            String password) {
}
