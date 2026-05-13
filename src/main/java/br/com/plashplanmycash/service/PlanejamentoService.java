package br.com.plashplanmycash.service;

import br.com.plashplanmycash.domain.entity.Planejamento;
import br.com.plashplanmycash.repository.PlanejamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanejamentoService {

    private final PlanejamentoRepository planejamentoRepository;
}