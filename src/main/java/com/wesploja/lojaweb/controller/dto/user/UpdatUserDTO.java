package com.wesploja.lojaweb.controller.dto.user;

import jakarta.validation.constraints.NotNull;

public record UpdatUserDTO(String name,
                           String email,
                           String phone,
                           String username,
                           String password) {
}
