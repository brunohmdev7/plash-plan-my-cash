package br.com.plashplanmycash.service;

import br.com.plashplanmycash.domain.dto.AtualizarUsuarioDto;
import br.com.plashplanmycash.domain.entity.Usuario;
import br.com.plashplanmycash.exception.PlashException;
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

    public Usuario atualizar(Long id, AtualizarUsuarioDto dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> PlashException.naoEncontrado("Usuário não encontrado"));

        if (dto.nome() != null && !dto.nome().isBlank()) usuario.setNome(dto.nome());
        if (dto.email() != null && !dto.email().isBlank()) usuario.setEmail(dto.email());
        if (dto.senha() != null && !dto.senha().isBlank()) usuario.setSenha(dto.senha());

        return usuarioRepository.save(usuario);
    }

    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id))
            throw PlashException.naoEncontrado("Usuário não encontrado");
        usuarioRepository.deleteById(id);
    }
}