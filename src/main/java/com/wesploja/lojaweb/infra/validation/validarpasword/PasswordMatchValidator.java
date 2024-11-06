package com.wesploja.lojaweb.infra.validation.validarpasword;


import com.wesploja.lojaweb.controller.dto.user.CadastrarUserDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, CadastrarUserDTO> {
    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
    }
    @Override
    public boolean isValid(CadastrarUserDTO dto, ConstraintValidatorContext context) {
        return dto.password().equals(dto.passwordConfirmation());
    }
}
