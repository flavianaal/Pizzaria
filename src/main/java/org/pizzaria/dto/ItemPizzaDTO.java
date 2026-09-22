package org.pizzaria.dto;

import org.pizzaria.enums.Tamanho;

import java.math.BigDecimal;

public class ItemPizzaDTO {

    private Tamanho tamanho;

    private BigDecimal valor;


    public ItemPizzaDTO() {
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
