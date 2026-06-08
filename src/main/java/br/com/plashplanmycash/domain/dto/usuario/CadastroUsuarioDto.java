package br.com.plashplanmycash.domain.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CadastroUsuarioDto(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Email é obrigatório")
        @Email
        String email,

        @NotBlank(message = "Senha é obrigatório")
        String senha) {}
