package org.pizzaria.model;


import at.favre.lib.crypto.bcrypt.BCrypt;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Usuario extends PanacheEntity {

    @Column(unique = true)
    private String email;
    private String senhaHash;

    public Usuario() {
    }


    public boolean verificarSenha(String senhaDigitada){
        return BCrypt.verifyer()
                .verify(senhaDigitada.toCharArray(), this.senhaHash)
                .verified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }
}
