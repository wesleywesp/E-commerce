package com.wesploja.lojaweb.domain.user;

import org.springframework.security.core.GrantedAuthority;

public enum Perfil implements GrantedAuthority {
    ADMIN(1, "ROLE_ADMIN"),
    CLIENT(2, "ROLE_CLIENT");

    private final int cod;
    private final String descricao;

    Perfil(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Perfil toEnum(Integer cod){
        if (cod == null) {
            return null;
        }

        for (Perfil cliente: Perfil.values()){
            if (cod.equals(cliente.getCod())){
                return cliente;
            }
        }

        throw new IllegalArgumentException("Id is not invalid!"+ cod);
    }

    @Override
    public String getAuthority() {
        return descricao;
    }
}
