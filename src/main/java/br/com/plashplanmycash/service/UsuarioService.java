package br.com.plashplanmycash.service;

import br.com.plashplanmycash.domain.entity.Usuario;
import br.com.plashplanmycash.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
}
