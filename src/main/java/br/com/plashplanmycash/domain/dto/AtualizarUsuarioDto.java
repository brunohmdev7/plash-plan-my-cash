package br.com.plashplanmycash.domain.dto;

import jakarta.validation.constraints.Email;

public record AtualizarUsuarioDto(String nome, @Email String email, String senha) {}