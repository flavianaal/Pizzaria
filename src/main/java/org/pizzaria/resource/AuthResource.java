package org.pizzaria.resource;

import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.PermitAll;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pizzaria.dto.LoginRequest;
import org.pizzaria.model.Usuario;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Path("/auth")
public class AuthResource {

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    @Transactional
    public Response login(LoginRequest request) {
        Usuario usuario = Usuario.find("email", request.getEmail()).firstResult();

        if (usuario == null || !usuario.verificarSenha(request.getSenha())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Usuário ou senha inválidos").build();
        }

        // Gera o token
        Set<String> roles = new HashSet<>();
        roles.add("admin"); // papel fixo para nosso único usuário

        String token = Jwt.issuer("https://pizzaria-api")
                .upn(usuario.getEmail())
                .groups(roles)
                .expiresIn(Duration.ofDays(30))
                .sign();

        return Response.ok().entity(token).build();
    }
}
