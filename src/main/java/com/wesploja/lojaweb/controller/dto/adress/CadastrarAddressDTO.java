package com.wesploja.lojaweb.controller.dto.adress;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastrarAddressDTO(String street,
                                  String number,
                                  String complement,
                                  @NotBlank(message = "District is required")
                                  String district,
                                  @NotBlank(message = "City is required")
                                  String city,
                                  @NotBlank(message = "State is required")
                                  String state,
                                  @NotBlank(message = "Postal code is required")
                                  String codepostal) {
}
