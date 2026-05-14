package br.com.plashplanmycash.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

public record ErroResponse(
        LocalDateTime timestamp,
        int status,
        String erro,
        String mensagem,
        String caminho,
        @JsonInclude(JsonInclude.Include.NON_NULL) Map<String, String> campos
) {}