package org.pizzaria.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.pizzaria.enums.Tamanho;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class ItemPizza  extends PanacheEntity {

    @Enumerated(EnumType.STRING)
    private Tamanho tamanho;
    @Column(precision = 10, scale = 2)
    private BigDecimal valor;


    public ItemPizza() {
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
