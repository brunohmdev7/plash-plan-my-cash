package br.com.plashplanmycash.domain.dto.planejamento;

import br.com.plashplanmycash.domain.enums.TipoMoeda;
import br.com.plashplanmycash.domain.enums.TipoPlanejamento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AtualizarPlanejamentoDto(
        TipoPlanejamento tipo,
        String nome,
        @DecimalMin(value = "0.01", message = "Valor da meta deve ser maior que zero") BigDecimal valorMeta,
        @DecimalMin(value = "0.0", message = "Valor atual não pode ser negativo") BigDecimal valorAtual,
        LocalDate prazoInicio,
        @Future(message = "Prazo fim deve ser uma data futura") LocalDate prazoFim,
        TipoMoeda moeda
) {}