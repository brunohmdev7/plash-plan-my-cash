package br.com.plashplanmycash.domain.dto.carteira;

import br.com.plashplanmycash.domain.enums.TipoCarteira;
import br.com.plashplanmycash.domain.enums.TipoConta;
import br.com.plashplanmycash.domain.enums.TipoMoeda;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CadastroCarteiraDto(
        @NotNull Long usuarioId,
        @NotBlank String apelido,
        @NotNull TipoCarteira tipoCarteira,
        @NotNull TipoConta tipoConta,
        @NotNull TipoMoeda tipoMoeda,
        @NotNull @DecimalMin(value = "0.0", message = "Saldo não pode ser negativo") BigDecimal saldo
) {}