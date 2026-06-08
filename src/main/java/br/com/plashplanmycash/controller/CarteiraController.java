package br.com.plashplanmycash.controller;

import br.com.plashplanmycash.domain.dto.carteira.AtualizarCarteiraDto;
import br.com.plashplanmycash.domain.dto.carteira.CadastroCarteiraDto;
import br.com.plashplanmycash.domain.dto.carteira.LeituraCarteiraDto;
import br.com.plashplanmycash.domain.entity.Carteira;
import br.com.plashplanmycash.service.CarteiraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/carteiras")
@RequiredArgsConstructor
public class CarteiraController {

    private final CarteiraService carteiraService;

    @PostMapping
    public ResponseEntity<LeituraCarteiraDto> salvar(@RequestBody @Valid CadastroCarteiraDto dto) {
        Carteira carteiraSalva = carteiraService.salvar(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(carteiraSalva.getId())
                .toUri();
        return ResponseEntity.created(location).body(toDto(carteiraSalva));
    }

    @GetMapping
    public ResponseEntity<List<LeituraCarteiraDto>> listar() {
        List<LeituraCarteiraDto> carteiras = carteiraService.listar().stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(carteiras);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LeituraCarteiraDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizarCarteiraDto dto) {
        return ResponseEntity.ok(toDto(carteiraService.atualizar(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        carteiraService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private LeituraCarteiraDto toDto(Carteira c) {
        return new LeituraCarteiraDto(
                c.getId(),
                c.getUsuario().getId(),
                c.getApelido(),
                c.getTipoCarteira(),
                c.getTipoConta(),
                c.getTipoMoeda(),
                c.getSaldo(),
                c.getCriadoEm(),
                c.getAtualizadoEm()
        );
    }
}