package com.wesploja.lojaweb.domain.address;

import com.wesploja.lojaweb.controller.dto.adress.CadastrarAddressDTO;
import com.wesploja.lojaweb.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity @Table(name = "addresses")
public class Address {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String city;
    private String state;
    private String district;
    private String codepostal;
    private String number;
    private String complement;

    @ManyToOne
    @JoinColumn(name = "user_id") // Define a chave estrangeira "user_id" na tabela Address
    private User user;

    public Address(@Valid CadastrarAddressDTO dto) {
        this.street = dto.street();
        this.city = dto.city();
        this.state = dto.state();
        this.district = dto.district();
        this.codepostal = dto.codepostal();
        this.number = dto.number();
        this.complement = dto.complement();
    }

    public Address(String decrypt1, String decrypt2) {
        this.street = decrypt1;
        this.city = decrypt2;
    }
}
