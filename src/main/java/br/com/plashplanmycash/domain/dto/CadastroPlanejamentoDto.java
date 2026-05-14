package br.com.plashplanmycash.domain.dto;

import br.com.plashplanmycash.domain.enums.TipoMoeda;
import br.com.plashplanmycash.domain.enums.TipoPlanejamento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CadastroPlanejamentoDto(
        @NotNull Long usuarioId,
        @NotNull TipoPlanejamento tipo,
        @NotBlank String nome,
        @NotNull @DecimalMin(value = "0.01", message = "Valor da meta deve ser maior que zero") BigDecimal valorMeta,
        @NotNull @DecimalMin(value = "0.0", message = "Valor atual não pode ser negativo") BigDecimal valorAtual,
        @NotNull LocalDate prazoInicio,
        @NotNull @Future(message = "Prazo fim deve ser uma data futura") LocalDate prazoFim,
        @NotNull TipoMoeda moeda
) {}