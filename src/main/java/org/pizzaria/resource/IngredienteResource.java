package org.pizzaria.resource;


import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.pizzaria.dto.IngredienteDTO;
import org.pizzaria.model.Ingrediente;
import org.pizzaria.repository.IngredienteRepository;
import org.pizzaria.utilitario.UtilitarioImage;

import java.util.List;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@Path("/ingredientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IngredienteResource {

    @Inject
    IngredienteRepository ingredienteRepository;

    @Inject
    @ConfigProperty(name = "pizzaria.upload.dir")
    String diretorio;

    @POST
    @Transactional
    @RolesAllowed("admin")
    public Response criar(IngredienteDTO ingredienteDTO){

        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNome(ingredienteDTO.getNome());

        if (ingredienteDTO.getImagemBase64()!= null && !ingredienteDTO.getImagemBase64().isEmpty()){
            String caminhoImagem = UtilitarioImage.salvarImagem(diretorio, ingredienteDTO.getImagemBase64());
            ingrediente.setCaminhoImagem(caminhoImagem);
        }
        ingredienteRepository.persist(ingrediente);

        return Response.status(Response.Status.CREATED)
                .entity(ingrediente).build();

    }

    @GET
    public List<Ingrediente> listarIngrediente(){

        return ingredienteRepository.listarTodos();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin")
    public Response atualizar(@PathParam("id") Long id, IngredienteDTO dados){

        Ingrediente ingrediente = Ingrediente.findById(id);

        if (ingrediente == null){

            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Ingrediente não encontrado").build();
        }
        ingrediente.setNome(dados.getNome());

        if (dados.getImagemBase64()!= null && !dados.getImagemBase64().isEmpty()){
            String caminhoImagem = "Caminho da Imagem";
            ingrediente.setCaminhoImagem(caminhoImagem);
        }

        return Response.ok(ingrediente).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin")
    public Response deletar(@PathParam("id") Long id){

        Ingrediente ingrediente = Ingrediente.findById(id);

        if (ingrediente == null){

            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Ingrediente não encontrado").build();
        }
        ingredienteRepository.inativar(ingrediente);

        return Response.noContent().build();
    }

    @GET
    @Path("/{caminho: .+}")
    @Produces({"image/jpeg", "image/png", "image/gif", "image/webp", "image/bmp", "image/svg+xml"})
    public Response visualizarImagem(@PathParam("caminho") String caminho) {
        try {


            StreamingOutput saidaStream = saida -> {
                try (InputStream fluxoEntrada = Files.newInputStream(new File("/"+caminho).toPath())) {
                    byte[] buffer = new byte[8192];
                    int bytesLidos;
                    while ((bytesLidos = fluxoEntrada.read(buffer)) != -1) {
                        saida.write(buffer, 0, bytesLidos);
                    }
                }
            };


            return Response.ok(saidaStream)
                    .type("image/jpeg")
                    .header("Cache-Control", "public, max-age=3600")
                    .header("Content-Disposition", "inline; filename=\"" + caminho + "\"")
                    .build();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
