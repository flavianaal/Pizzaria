package org.pizzaria.dto;

import jakarta.validation.constraints.Email;

public class UsuarioDTO {

    @Email(message = "Email Inválido")
    private String email;


    private String senha;

    public UsuarioDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
