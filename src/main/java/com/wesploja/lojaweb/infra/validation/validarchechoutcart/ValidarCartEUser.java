package com.wesploja.lojaweb.infra.validation.validarchechoutcart;

import com.wesploja.lojaweb.doman.user.User;
import com.wesploja.lojaweb.repository.CartRepository;
import com.wesploja.lojaweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ValidarCartEUser {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

    public Object AutenticarEencontrarUserECart(UserDetails user) {
        // Encontrar o usuário autenticado
        var users = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        var cart = cartRepository.findByUser((User) users)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
        return cart;
    }

}
