package com.wesploja.lojaweb.controller.dto.user;

import com.wesploja.lojaweb.controller.dto.adress.AddressSavedDTO;
import com.wesploja.lojaweb.doman.user.User;

import java.util.List;

public record DethalharDadosUserDTO(String name,
                                    String email,
                                    String phone,
                                    String username,
                                    AddressSavedDTO adress) {

    public DethalharDadosUserDTO(User user, String decrypt, String decrypt1, List<AddressSavedDTO> addressesDTO) {
        this(user.getName(), decrypt, decrypt1, user.getUsername(), addressesDTO.get(0));
    }
}
