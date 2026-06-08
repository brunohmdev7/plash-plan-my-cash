package br.com.plashplanmycash.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginUsuarioDto(
        @NotEmpty(message = "Email é obrigatório")
        @Email
        String email,

        @NotEmpty(message = "Senha é obrigatório")
        String senha) {
}
