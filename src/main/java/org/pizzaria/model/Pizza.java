package org.pizzaria.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.Objects;
@Entity
public class Pizza extends PanacheEntity {


    private String nome;
    private String descricao;
    @ManyToMany
    private List<Ingrediente> ingredientes;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPizza> itens;
    private Boolean ativo;
    private String caminhoImagem;


    public Pizza() {
        ativo = Boolean.TRUE;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<ItemPizza> getItens() {
        return itens;
    }

    public void setItens(List<ItemPizza> itens) {
        this.itens = itens;
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
