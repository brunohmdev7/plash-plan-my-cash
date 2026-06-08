package br.com.plashplanmycash.domain.dto.planejamento;

import br.com.plashplanmycash.domain.enums.TipoMoeda;
import br.com.plashplanmycash.domain.enums.TipoPlanejamento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record LeituraPlanejamentoDto(
        Long id,
        Long usuarioId,
        TipoPlanejamento tipo,
        String nome,
        TipoMoeda moeda,
        LocalDate prazoInicio,
        LocalDate prazoFim,
        BigDecimal valorMeta,
        BigDecimal valorAtual,
        BigDecimal valorRestante,
        BigDecimal percentualConcluido,
        BigDecimal valorMensalNecessario,
        long mesesTotais,
        long mesesRestantes,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {}