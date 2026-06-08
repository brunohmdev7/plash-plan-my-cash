package br.com.plashplanmycash.controller;

import br.com.plashplanmycash.domain.dto.planejamento.AtualizarPlanejamentoDto;
import br.com.plashplanmycash.domain.dto.planejamento.CadastroPlanejamentoDto;
import br.com.plashplanmycash.domain.dto.planejamento.LeituraPlanejamentoDto;
import br.com.plashplanmycash.domain.entity.Planejamento;
import br.com.plashplanmycash.service.PlanejamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/planejamentos")
@RequiredArgsConstructor
public class PlanejamentoController {

    private final PlanejamentoService planejamentoService;

    @PostMapping
    public ResponseEntity<LeituraPlanejamentoDto> salvar(@RequestBody @Valid CadastroPlanejamentoDto dto) {
        Planejamento salvo = planejamentoService.salvar(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.getId())
                .toUri();
        return ResponseEntity.created(location).body(toDto(salvo));
    }

    @GetMapping
    public ResponseEntity<List<LeituraPlanejamentoDto>> listar() {
        List<LeituraPlanejamentoDto> planejamentos = planejamentoService.listar().stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(planejamentos);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LeituraPlanejamentoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizarPlanejamentoDto dto) {
        return ResponseEntity.ok(toDto(planejamentoService.atualizar(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        planejamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private LeituraPlanejamentoDto toDto(Planejamento p) {
        LocalDate hoje = LocalDate.now();
        BigDecimal valorRestante = p.getValorMeta().subtract(p.getValorAtual());
        long mesesTotais = ChronoUnit.MONTHS.between(p.getPrazoInicio(), p.getPrazoFim());
        long mesesRestantes = ChronoUnit.MONTHS.between(hoje, p.getPrazoFim());
        BigDecimal percentualConcluido = p.getValorAtual()
                .divide(p.getValorMeta(), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        BigDecimal valorMensalNecessario = mesesRestantes > 0
                ? valorRestante.divide(BigDecimal.valueOf(mesesRestantes), 2, RoundingMode.HALF_UP)
                : valorRestante;

        return new LeituraPlanejamentoDto(
                p.getId(),
                p.getUsuario().getId(),
                p.getTipo(),
                p.getNome(),
                p.getMoeda(),
                p.getPrazoInicio(),
                p.getPrazoFim(),
                p.getValorMeta(),
                p.getValorAtual(),
                valorRestante,
                percentualConcluido,
                valorMensalNecessario,
                mesesTotais,
                mesesRestantes,
                p.getCriadoEm(),
                p.getAtualizadoEm()
        );
    }
}