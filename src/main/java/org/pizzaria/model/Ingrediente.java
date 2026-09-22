package org.pizzaria.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;


import java.util.Objects;

@Entity
public class Ingrediente extends PanacheEntity {


    private String nome;
    private Boolean ativo;
    private String caminhoImagem;


    public Ingrediente() {
        ativo = Boolean.TRUE;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }


}
