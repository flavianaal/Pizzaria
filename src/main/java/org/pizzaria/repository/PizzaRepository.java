package org.pizzaria.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.pizzaria.model.Ingrediente;
import org.pizzaria.model.Pizza;

import java.util.List;

@ApplicationScoped
public class PizzaRepository implements PanacheRepository<Pizza>{

    public List<Pizza> listarPizza(){
        return find("SELECT p FROM Pizza p WHERE p.ativo= true").list();
    }

    public List<Pizza> pesquisarNome(String nome){
        return find("Nome", nome).list();
    }

    public Pizza pesquisarID(Long id){
        return findById(id);
    }

    public void inativar(Pizza pizza){
        pizza.setAtivo(Boolean.FALSE);
    }

}
