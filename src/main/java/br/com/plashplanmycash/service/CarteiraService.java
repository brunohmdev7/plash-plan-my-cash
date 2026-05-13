package br.com.plashplanmycash.service;

import br.com.plashplanmycash.domain.entity.Carteira;
import br.com.plashplanmycash.repository.CarteiraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarteiraService {

    private final CarteiraRepository carteiraRepository;
}
