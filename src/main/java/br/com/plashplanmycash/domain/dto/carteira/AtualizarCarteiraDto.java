package br.com.plashplanmycash.domain.dto.carteira;

import br.com.plashplanmycash.domain.enums.TipoCarteira;
import br.com.plashplanmycash.domain.enums.TipoConta;
import br.com.plashplanmycash.domain.enums.TipoMoeda;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record AtualizarCarteiraDto(
        String apelido,
        TipoCarteira tipoCarteira,
        TipoConta tipoConta,
        TipoMoeda tipoMoeda,
        @DecimalMin(value = "0.0", message = "Saldo não pode ser negativo") BigDecimal saldo
) {}