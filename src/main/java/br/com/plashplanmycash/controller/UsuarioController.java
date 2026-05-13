package br.com.plashplanmycash.controller;

import br.com.plashplanmycash.domain.dto.CadastroUsuarioDto;
import br.com.plashplanmycash.domain.dto.RetornoCadastroUsuarioDto;
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
}