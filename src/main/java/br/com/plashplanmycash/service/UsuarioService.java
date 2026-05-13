package br.com.plashplanmycash.service;

import br.com.plashplanmycash.domain.entity.Usuario;
import br.com.plashplanmycash.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario salvar(Usuario usuario) { return usuarioRepository.save(usuario); }

    public List<Usuario> listar() { return usuarioRepository.findAll(); }
}
