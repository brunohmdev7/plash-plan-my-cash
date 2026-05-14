package br.com.plashplanmycash.service;

import br.com.plashplanmycash.domain.dto.AtualizarPlanejamentoDto;
import br.com.plashplanmycash.domain.dto.CadastroPlanejamentoDto;
import br.com.plashplanmycash.domain.entity.Planejamento;
import br.com.plashplanmycash.domain.entity.Usuario;
import br.com.plashplanmycash.exception.RegraDeNegocioException;
import br.com.plashplanmycash.exception.RecursoNaoEncontradoException;
import br.com.plashplanmycash.repository.PlanejamentoRepository;
import br.com.plashplanmycash.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanejamentoService {

    private final PlanejamentoRepository planejamentoRepository;
    private final UsuarioRepository usuarioRepository;

    public Planejamento salvar(CadastroPlanejamentoDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        validarPrazos(dto.prazoInicio(), dto.prazoFim());
        validarValores(dto.valorAtual(), dto.valorMeta());

        Planejamento planejamento = new Planejamento();
        planejamento.setUsuario(usuario);
        planejamento.setTipo(dto.tipo());
        planejamento.setNome(dto.nome());
        planejamento.setValorMeta(dto.valorMeta());
        planejamento.setValorAtual(dto.valorAtual());
        planejamento.setPrazoInicio(dto.prazoInicio());
        planejamento.setPrazoFim(dto.prazoFim());
        planejamento.setMoeda(dto.moeda());

        return planejamentoRepository.save(planejamento);
    }

    public List<Planejamento> listar() { return planejamentoRepository.findAll(); }

    public Planejamento atualizar(Long id, AtualizarPlanejamentoDto dto) {
        Planejamento planejamento = planejamentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Planejamento não encontrado"));

        if (dto.tipo() != null) planejamento.setTipo(dto.tipo());
        if (dto.nome() != null && !dto.nome().isBlank()) planejamento.setNome(dto.nome());
        if (dto.moeda() != null) planejamento.setMoeda(dto.moeda());
        if (dto.prazoInicio() != null) planejamento.setPrazoInicio(dto.prazoInicio());
        if (dto.prazoFim() != null) planejamento.setPrazoFim(dto.prazoFim());
        if (dto.valorMeta() != null) planejamento.setValorMeta(dto.valorMeta());
        if (dto.valorAtual() != null) planejamento.setValorAtual(dto.valorAtual());

        validarPrazos(planejamento.getPrazoInicio(), planejamento.getPrazoFim());
        validarValores(planejamento.getValorAtual(), planejamento.getValorMeta());

        return planejamentoRepository.save(planejamento);
    }

    public void deletar(Long id) {
        if (!planejamentoRepository.existsById(id))
            throw new RecursoNaoEncontradoException("Planejamento não encontrado");
        planejamentoRepository.deleteById(id);
    }

    private void validarPrazos(LocalDate inicio, LocalDate fim) {
        if (!fim.isAfter(inicio))
            throw new RegraDeNegocioException("Prazo fim deve ser posterior ao prazo início");
    }

    private void validarValores(BigDecimal valorAtual, BigDecimal valorMeta) {
        if (valorAtual.compareTo(valorMeta) > 0)
            throw new RegraDeNegocioException("Valor atual não pode ser maior que o valor da meta");
    }
}