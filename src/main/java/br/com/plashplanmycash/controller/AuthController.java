package br.com.plashplanmycash.controller;

import br.com.plashplanmycash.config.security.TokenConfig;
import br.com.plashplanmycash.domain.dto.usuario.CadastroUsuarioDto;
import br.com.plashplanmycash.domain.dto.usuario.LoginUsuarioDto;
import br.com.plashplanmycash.domain.dto.usuario.RetornoCadastroUsuarioDto;
import br.com.plashplanmycash.domain.dto.usuario.RetornoLoginDto;
import br.com.plashplanmycash.domain.entity.Usuario;
import br.com.plashplanmycash.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Autenticação")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;

    @PostMapping("/login")
    public ResponseEntity<RetornoLoginDto> login(@RequestBody @Valid LoginUsuarioDto request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.email(), request.senha());
        Authentication authentication = authenticationManager.authenticate(authToken);

        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = tokenConfig.gerarToken(usuario);

        return ResponseEntity.ok(new RetornoLoginDto(token));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<RetornoCadastroUsuarioDto> cadastrar(@RequestBody @Valid CadastroUsuarioDto request) {
        String senhaComHashing = passwordEncoder.encode(request.senha());
        Usuario usuario = usuarioService.salvar(new Usuario(request.nome(), request.email(), senhaComHashing));

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/usuarios/{id}")
                .buildAndExpand(usuario.getId())
                .toUri();

        RetornoCadastroUsuarioDto usuarioCadastrado = new RetornoCadastroUsuarioDto(
                usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getCriadoEm()
        );

        return ResponseEntity.created(location).body(usuarioCadastrado);
    }
}
