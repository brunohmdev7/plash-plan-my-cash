package br.com.plashplanmycash.config;

import lombok.Builder;

@Builder
public record DadosUsuarioJWT(Long id, String email) {
}
