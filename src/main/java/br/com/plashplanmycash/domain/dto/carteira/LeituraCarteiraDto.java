package br.com.plashplanmycash.domain.dto.carteira;

import br.com.plashplanmycash.domain.enums.TipoCarteira;
import br.com.plashplanmycash.domain.enums.TipoConta;
import br.com.plashplanmycash.domain.enums.TipoMoeda;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LeituraCarteiraDto(
        Long id,
        Long usuarioId,
        String apelido,
        TipoCarteira tipoCarteira,
        TipoConta tipoConta,
        TipoMoeda tipoMoeda,
        BigDecimal saldo,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {}