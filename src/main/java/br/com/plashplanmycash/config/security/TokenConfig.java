package br.com.plashplanmycash.config.security;

import br.com.plashplanmycash.config.dto.DadosUsuarioJWT;
import br.com.plashplanmycash.domain.entity.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class TokenConfig {

    @Value("${api.plash-plan-my-cash.token-config.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim("id", usuario.getId())
                .withSubject(usuario.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 7200000)) // Token válido por 2 horas
                .sign(algorithm);
    }

    public Optional<DadosUsuarioJWT> validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return Optional.of(DadosUsuarioJWT.builder()
                    .id(decodedJWT.getClaim("id").asLong())
                    .email(decodedJWT.getSubject())
                    .build());

        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }
}
