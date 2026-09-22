package org.pizzaria.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.pizzaria.model.Ingrediente;

import java.util.List;

@ApplicationScoped
public class IngredienteRepository implements PanacheRepository<Ingrediente> {


    public List<Ingrediente> listarTodos() {
        return find("SELECT i FROM Ingrediente i WHERE i.ativo = true").list();
    }

    public List<Ingrediente> pesquisarNome(String nome){
        return find("nome", nome).list();
    }

    public void inativar(Ingrediente ingrediente){
        ingrediente.setAtivo(Boolean.FALSE);
    }

    public Ingrediente pesquisarID(Long id){
        return findById(id);
    }

}
