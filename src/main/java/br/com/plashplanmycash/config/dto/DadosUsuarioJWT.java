package br.com.plashplanmycash.config.dto;

import lombok.Builder;

@Builder
public record DadosUsuarioJWT(Long id, String email) {
}
