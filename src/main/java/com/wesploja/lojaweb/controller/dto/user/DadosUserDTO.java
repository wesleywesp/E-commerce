package com.wesploja.lojaweb.controller.dto.user;

import com.wesploja.lojaweb.domain.user.User;

public record DadosUserDTO(String name, String email, String phone) {

    public DadosUserDTO(User user, String email, String phone) {
     this(user.getName(), email , phone);
    }
}
