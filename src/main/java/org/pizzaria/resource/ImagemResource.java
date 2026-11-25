
package org.pizzaria.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/uploads/pizzas")
public class ImagemResource {

    @Inject
    @ConfigProperty(name = "pizzaria.upload.dir")
    String diretorio;

    @GET
    @Path("/{nomeArquivo}")
    public Response buscarImagem(@PathParam("nomeArquivo") String nomeArquivo) {

        java.nio.file.Path caminho = Paths.get(diretorio, nomeArquivo).normalize();

        if (!caminho.startsWith(Paths.get(diretorio).normalize())) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        File arquivo = caminho.toFile();

        if (!arquivo.exists() || !arquivo.isFile()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            String tipoImagem = Files.probeContentType(caminho);

            return Response.ok(arquivo)
                    .type(tipoImagem != null ? tipoImagem : "application/octet-stream")
                    .build();

        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}
