package br.com.plashplanmycash.domain.dto;

import br.com.plashplanmycash.domain.enums.TipoMoeda;
import br.com.plashplanmycash.domain.enums.TipoPlanejamento;
import jakarta.validation.constraints.AssertTrue;
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
) {
    @AssertTrue(message = "Prazo fim deve ser posterior ao prazo início")
    public boolean isPrazoValido() {
        return prazoInicio == null || prazoFim == null || prazoFim.isAfter(prazoInicio);
    }

    @AssertTrue(message = "Valor atual não pode ser maior que o valor da meta")
    public boolean isValorAtualValido() {
        return valorAtual == null || valorMeta == null || valorAtual.compareTo(valorMeta) <= 0;
    }
}