package org.pizzaria.resource;


import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pizzaria.dto.UsuarioDTO;
import org.pizzaria.model.Usuario;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @POST
    @Transactional
    public Response Criar(@Valid UsuarioDTO usuarioDTO){

        if (Usuario.find("email", usuarioDTO.getEmail())
                .firstResult() != null){
            return Response.status(Response.Status.CONFLICT)
                    .entity("Email já cadastrado").build();
        }

        String hash = BCrypt.withDefaults()
                .hashToString(12, usuarioDTO.getSenha()
                        .toCharArray());

        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenhaHash(hash);
        usuario.persist();

        return Response.status(Response.Status.CREATED)
                .entity("Usuário Cadastrado com Sucesso").build();

    }
}
