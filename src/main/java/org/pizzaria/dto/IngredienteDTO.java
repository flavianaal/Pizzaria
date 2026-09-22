package org.pizzaria.dto;

public class IngredienteDTO {

    private String nome;

    private String imagemBase64;

    public IngredienteDTO(String nome, String imagemBase64) {
        this.nome = nome;
        this.imagemBase64 = imagemBase64;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagemBase64() {
        return imagemBase64;
    }

    public void setImagemBase64(String imagemBase64) {
        this.imagemBase64 = imagemBase64;
    }
}
