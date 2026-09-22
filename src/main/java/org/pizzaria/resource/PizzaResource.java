package org.pizzaria.resource;


import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.pizzaria.dto.IngredienteDTO;
import org.pizzaria.dto.ItemPizzaDTO;
import org.pizzaria.dto.PizzaDTO;
import org.pizzaria.model.Ingrediente;
import org.pizzaria.model.ItemPizza;
import org.pizzaria.model.Pizza;
import org.pizzaria.repository.IngredienteRepository;
import org.pizzaria.repository.PizzaRepository;
import org.pizzaria.utilitario.UtilitarioImage;

import java.util.ArrayList;
import java.util.List;

@Path("/pizzas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PizzaResource {

    @Inject
    PizzaRepository pizzaRepository;

    @Inject
    IngredienteRepository ingredienteRepository;

    @Inject
    @ConfigProperty(name = "pizzaria.upload.dir")
    String diretorio;

    @POST
    @Transactional
    @RolesAllowed("admin")
    public Response criar(PizzaDTO pizzaDTO){

        System.out.println("Entrou no método criar");



        List<Ingrediente> ingredientes = new ArrayList<>();
        for (Long id: pizzaDTO.getIngredientesId()){
            Ingrediente ingrediente = ingredienteRepository.pesquisarID(id);
            if (ingrediente != null){
                ingredientes.add(ingrediente);
            }
        }

        List<ItemPizza> itensPizza = new ArrayList<>();
        // ✅ Correto
        if (!pizzaDTO.getItens().isEmpty()){
            for (ItemPizzaDTO itemPizzaDTO: pizzaDTO.getItens()) {
                ItemPizza itemPizza = new ItemPizza();
                itemPizza.setTamanho(itemPizzaDTO.getTamanho());
                itemPizza.setValor(itemPizzaDTO.getValor());
                itensPizza.add(itemPizza);
            }
        }

        String caminhoImagem = null;
        if (pizzaDTO.getImagemBase64()!= null && !pizzaDTO.getImagemBase64().isEmpty()){
            caminhoImagem = UtilitarioImage.salvarImagem(diretorio, pizzaDTO.getImagemBase64());

        }


        Pizza pizza = new Pizza();
        pizza.setNome(pizzaDTO.getNome());
        pizza.setDescricao(pizzaDTO.getDescricao());
        pizza.setIngredientes(ingredientes);
        pizza.setItens(itensPizza);
        pizza.setCaminhoImagem(caminhoImagem);

        pizzaRepository.persist(pizza);

        return Response.status(Response.Status.CREATED).entity(pizza).build();


    }

    @GET
    public List<Pizza> listarPizzas(){
        return pizzaRepository.listarPizza();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id){

        Pizza pizza = pizzaRepository.pesquisarID(id);
        if (pizza == null || !pizza.getAtivo()){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Pizza não encontrada").build();
        }
        return Response.ok(pizza).build();

    }


    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin")
    public Response atualizar(@PathParam("id") Long id, PizzaDTO pizzaDTO){

        Pizza pizza = pizzaRepository.pesquisarID(id);

        if (pizza == null){

            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Pizza não encontrada").build();
        }


        List<Ingrediente> ingredientes = new ArrayList<>();
        for (Long ids: pizzaDTO.getIngredientesId()){
            Ingrediente ingrediente = ingredienteRepository.pesquisarID(ids);
            if (ingrediente != null){
                ingredientes.add(ingrediente);
            }
        }

        List<ItemPizza> itensAtual = pizza.getItens();
        itensAtual.clear();
        if (!pizzaDTO.getItens().isEmpty()){
            for (ItemPizzaDTO itemPizzaDTO: pizzaDTO.getItens()) {
                ItemPizza itemPizza = new ItemPizza();
                itemPizza.setTamanho(itemPizzaDTO.getTamanho());
                itemPizza.setValor(itemPizzaDTO.getValor());
                itensAtual.add(itemPizza);
            }
        }

        String caminhoImagem = null;
        if (pizzaDTO.getImagemBase64()!= null && !pizzaDTO.getImagemBase64().isEmpty()){
            caminhoImagem = UtilitarioImage.salvarImagem(diretorio, pizzaDTO.getImagemBase64());

        }

        pizza.setNome(pizzaDTO.getNome());
        pizza.setDescricao(pizzaDTO.getDescricao());
        pizza.setIngredientes(ingredientes);
        pizza.setItens(itensAtual);
        pizza.setCaminhoImagem(caminhoImagem);


        return Response.ok(pizza).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin")
    public Response deletar(@PathParam("id") Long id){

        Pizza pizza = pizzaRepository.pesquisarID(id);

        if (pizza == null){

            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Pizza não encontrada").build();
        }
        pizzaRepository.inativar(pizza);

        return Response.noContent().build();
    }

}
