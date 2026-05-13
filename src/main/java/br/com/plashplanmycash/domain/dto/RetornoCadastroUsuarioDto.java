package br.com.plashplanmycash.domain.dto;

import java.time.LocalDateTime;

public record RetornoCadastroUsuarioDto(Long id, String
nome, String email, LocalDateTime criadoEm) {}
