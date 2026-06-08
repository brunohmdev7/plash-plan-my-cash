package br.com.plashplanmycash.domain.dto.usuario;

import br.com.plashplanmycash.domain.entity.Carteira;
import br.com.plashplanmycash.domain.entity.Planejamento;

import java.util.List;

public record LeituraUsuarioDto(String nome, String email, List<Carteira> carteiras, List<Planejamento> planejamentos) {
}
