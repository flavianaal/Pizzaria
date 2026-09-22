package org.pizzaria.dto;

import java.util.List;

public class PizzaDTO {

    private String nome;
    private String descricao;
    private String imagemBase64;
    private List<Long> ingredientesId;
    private List<ItemPizzaDTO> itens;


    public PizzaDTO() {
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

    public String getImagemBase64() {
        return imagemBase64;
    }

    public void setImagemBase64(String imagemBase64) {
        this.imagemBase64 = imagemBase64;
    }

    public List<Long> getIngredientesId() {
        return ingredientesId;
    }

    public void setIngredientesId(List<Long> ingredientesId) {
        this.ingredientesId = ingredientesId;
    }

    public List<ItemPizzaDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPizzaDTO> itens) {
        this.itens = itens;
    }
}
