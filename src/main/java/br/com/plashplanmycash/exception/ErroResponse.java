package br.com.plashplanmycash.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

public record ErroResponse(
        int status,
        String erro,
        String mensagem
) {}