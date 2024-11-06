package com.wesploja.lojaweb.controller.dto.adress;

import com.wesploja.lojaweb.doman.address.Address;

import java.util.List;

public record AddressSavedDTO(String street, String city, String state, String district, String codepostal, String number, String complement) {


    public AddressSavedDTO(String street, String city, String state, String district, String codepostal, String number, String complement) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.district = district;
        this.codepostal = codepostal;
        this.number = number;
        this.complement = complement;
    }

    public AddressSavedDTO(List<Address> addresses) {
        this(addresses.get(0).getStreet(),
             addresses.get(0).getCity(),
             addresses.get(0).getState(),
             addresses.get(0).getDistrict(),
             addresses.get(0).getCodepostal(),
             addresses.get(0).getNumber(),
             addresses.get(0).getComplement());
    }
}
