package br.com.plashplanmycash.controller;

import br.com.plashplanmycash.domain.dto.usuario.AtualizarUsuarioDto;
import br.com.plashplanmycash.domain.dto.usuario.CadastroUsuarioDto;
import br.com.plashplanmycash.domain.dto.usuario.LeituraUsuarioDto;
import br.com.plashplanmycash.domain.dto.usuario.RetornoCadastroUsuarioDto;
import br.com.plashplanmycash.domain.entity.Usuario;
import br.com.plashplanmycash.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<RetornoCadastroUsuarioDto> salvar(@RequestBody @Valid CadastroUsuarioDto usuario) {
        Usuario usuarioSalvo = usuarioService.salvar(new Usuario(usuario.nome(), usuario.email(), usuario.senha()));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuarioSalvo.getId())
                .toUri();

        RetornoCadastroUsuarioDto bodyRetorno = new RetornoCadastroUsuarioDto(
                usuarioSalvo.getId(),
                usuarioSalvo.getNome(),
                usuarioSalvo.getEmail(),
                usuarioSalvo.getCriadoEm()
        );

        return ResponseEntity.created(location).body(bodyRetorno);
    }

    @GetMapping
    public ResponseEntity<List<LeituraUsuarioDto>> listar() {
        List<LeituraUsuarioDto> bodyRetorno = usuarioService.listar().stream()
                .map(u -> new LeituraUsuarioDto(u.getNome(), u.getEmail(), u.getCarteiras(), u.getPlanejamentos()))
                .toList();
        return ResponseEntity.ok(bodyRetorno);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RetornoCadastroUsuarioDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizarUsuarioDto usuario) {
        Usuario usuarioAtualizado = usuarioService.atualizar(id, usuario);
        RetornoCadastroUsuarioDto bodyRetorno = new RetornoCadastroUsuarioDto(
                usuarioAtualizado.getId(),
                usuarioAtualizado.getNome(),
                usuarioAtualizado.getEmail(),
                usuarioAtualizado.getCriadoEm()
        );
        return ResponseEntity.ok(bodyRetorno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}