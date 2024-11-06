package com.wesploja.lojaweb.domain.user;

import com.wesploja.lojaweb.controller.dto.user.CadastrarUserDTO;
import com.wesploja.lojaweb.controller.dto.user.UpdatUserDTO;
import com.wesploja.lojaweb.domain.address.Address;
import com.wesploja.lojaweb.infra.security.EncryptionService;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String username;
    private String password;
    private String phone;
    private boolean ativo;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil")
    private Perfil perfil= Perfil.CLIENT;// define o tipo de usuario como cliente por padrão

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id") // Cria uma chave estrangeira "user_id" na tabela Address
    private List<Address> addresses = new ArrayList<>();

    public User(CadastrarUserDTO dto) {
        this.ativo=true;
        this.name = dto.name();
        this.email = dto.email();
        this.username = dto.username();
        this.password = dto.password();
        this.phone = dto.phone();
    }

    public User(String name, String decrypt, String decrypt3) {
        this.name = name;
        this.email = decrypt;
        this.phone = decrypt3;
    }

    public User(String name, String decrypt, String decrypt1, String decrypt2, String decrypt3) {
        this.name = name;
        this.email = decrypt;
        this.phone = decrypt3;
        this.addresses.add(new Address(decrypt1, decrypt2));
    }

    public void atualizar(@Valid UpdatUserDTO dto, PasswordEncoder encoder, EncryptionService encrypt) {
        if(dto.password() != null) {
            this.password = encoder.encode(dto.password());
        }
        if (dto.name() != null) {
            this.name = dto.name();
        }
        if (dto.email() != null) {
            try {
                this.email = encrypt.encrypt(dto.email());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (dto.username() != null) {
            this.username = dto.username();
        }
        if (dto.phone() != null) {
            try {
                this.phone = encrypt.encrypt(dto.phone());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void desativar() {
        this.ativo = false;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(perfil);
    }
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ativo;
    }

  public String getAdress() {
        return addresses.stream().map(Address::toString).reduce("", (a, b) -> a + b);
    }


}
