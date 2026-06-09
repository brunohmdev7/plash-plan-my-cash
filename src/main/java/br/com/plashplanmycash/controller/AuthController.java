package br.com.plashplanmycash.controller;

import br.com.plashplanmycash.domain.dto.usuario.CadastroUsuarioDto;
import br.com.plashplanmycash.domain.dto.usuario.LoginUsuarioDto;
import br.com.plashplanmycash.domain.dto.usuario.RetornoCadastroUsuarioDto;
import br.com.plashplanmycash.domain.entity.Usuario;
import br.com.plashplanmycash.exception.PlashException;
import br.com.plashplanmycash.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity login(LoginUsuarioDto usuario) {
        return null;
    }

    @PostMapping("/cadastro")
    public ResponseEntity cadastrar(@RequestBody @Valid CadastroUsuarioDto dadosUsuario) {
            Usuario usuario =  new Usuario(dadosUsuario.nome(), dadosUsuario.email(), dadosUsuario.senha());
            usuarioService.salvar(usuario);

            RetornoCadastroUsuarioDto usuarioCadastrado = new RetornoCadastroUsuarioDto(
                    usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getCriadoEm()
            );

            return ResponseEntity.ok(usuarioCadastrado);
    }
}
