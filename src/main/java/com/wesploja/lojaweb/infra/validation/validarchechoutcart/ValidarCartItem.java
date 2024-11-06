package com.wesploja.lojaweb.infra.validation.validarchechoutcart;

import com.wesploja.lojaweb.domain.loja.cart.Cart;
import com.wesploja.lojaweb.infra.exception.TratarCkechoutCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class ValidarCartItem implements ValidarUserChechoutCart {
    @Autowired
    private ValidarCartEUser validarCartEUser;

    @Override
    public void validar(UserDetails userDetails) {
        var user =(Cart)validarCartEUser.AutenticarEencontrarUserECart(userDetails);

        var cart = user.getItems();

        if (cart.isEmpty()) {
            throw new TratarCkechoutCart("cart is empty");
        }

    }
}
