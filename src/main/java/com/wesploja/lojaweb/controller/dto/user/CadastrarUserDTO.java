package com.wesploja.lojaweb.controller.dto.user;

import com.wesploja.lojaweb.doman.user.Perfil;
import com.wesploja.lojaweb.infra.validation.validarpasword.PasswordMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@PasswordMatch(message = "Passwords must match")
public record CadastrarUserDTO(@NotBlank(message = "Name is required")
                               String name,
                               @NotBlank(message = "Email is required")
                               @Email(message = "Email must be valid")
                               String email,
                               @NotBlank(message = "Username is required")
                               String username,
                               @NotBlank(message = "Password is required")
                               String password,
                               @NotBlank(message = "Phone number is required")
                               @Pattern(regexp = "^\\+?\\d{1,3}?[-.\\s]?\\(?(\\d{1,4})\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$",
                                       message = "Phone number must be valid")
                                String phone,
                               @NotBlank(message = "Password confirmation is required")
                               String passwordConfirmation,
                               String perfil){
}
