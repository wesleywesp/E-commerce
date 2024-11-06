package com.wesploja.lojaweb.infra.validation.validarchechoutcart;

import com.wesploja.lojaweb.infra.exception.TratarCkechoutCart;

import com.wesploja.lojaweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class ValidarAddressUserChechoutCart implements ValidarUserChechoutCart {

    @Autowired
    private UserRepository userRepository;
    @Override
    public void validar(UserDetails userDetails) {
        var users = userRepository.findByUsername(userDetails.getUsername());
        var user = users.get();
        if (user.getAddresses().isEmpty()) {
            throw new TratarCkechoutCart("user has no address");
        }else if(user.getAddresses().stream().anyMatch(a -> a.getStreet().isEmpty() || a.getCity().isEmpty() || a.getState().isEmpty() || a.getDistrict().isEmpty() || a.getCodepostal().isEmpty() || a.getNumber().isEmpty())){
            throw new TratarCkechoutCart("address is incomplete");
        }

    }
}
