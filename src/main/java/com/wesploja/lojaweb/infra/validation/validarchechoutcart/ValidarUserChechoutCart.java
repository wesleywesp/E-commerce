package com.wesploja.lojaweb.infra.validation.validarchechoutcart;


import org.springframework.security.core.userdetails.UserDetails;

public interface ValidarUserChechoutCart {
    void validar(UserDetails userDetails);
}
