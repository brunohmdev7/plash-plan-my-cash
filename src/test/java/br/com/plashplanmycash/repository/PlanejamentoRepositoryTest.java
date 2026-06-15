package br.com.plashplanmycash.repository;

import br.com.plashplanmycash.domain.entity.Planejamento;
import br.com.plashplanmycash.domain.entity.Usuario;
import br.com.plashplanmycash.domain.enums.TipoMoeda;
import br.com.plashplanmycash.domain.enums.TipoPlanejamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
class PlanejamentoRepositoryTest {

    @Autowired
    private PlanejamentoRepository planejamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = usuarioRepository.save(new Usuario("Bruno", "bruno.plan@email.com", "senha_hash"));
    }

    private Planejamento criarPlanejamento(String nome, BigDecimal meta, BigDecimal atual) {
        return planejamentoRepository.save(Planejamento.builder()
                .usuario(usuario)
                .tipo(TipoPlanejamento.VIAGEM)
                .nome(nome)
                .valorMeta(meta)
                .valorAtual(atual)
                .prazoInicio(LocalDate.now())
                .prazoFim(LocalDate.now().plusMonths(6))
                .moeda(TipoMoeda.BRL)
                .build());
    }

    @Test
    void save_devePersistirPlanejamento() {
        Planejamento planejamento = criarPlanejamento("Viagem ao Japão", BigDecimal.valueOf(10000), BigDecimal.ZERO);

        assertThat(planejamento.getId()).isNotNull();
        assertThat(planejamento.getCriadoEm()).isNotNull();
        assertThat(planejamento.getValorMeta()).isEqualByComparingTo(BigDecimal.valueOf(10000));
    }

    @Test
    void findById_deveRetornarPlanejamento_quandoExiste() {
        Planejamento planejamento = criarPlanejamento("Reserva de Emergência", BigDecimal.valueOf(5000), BigDecimal.valueOf(1000));

        var resultado = planejamentoRepository.findById(planejamento.getId());

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNome()).isEqualTo("Reserva de Emergência");
        assertThat(resultado.get().getValorAtual()).isEqualByComparingTo(BigDecimal.valueOf(1000));
    }

    @Test
    void findById_deveRetornarVazio_quandoNaoExiste() {
        var resultado = planejamentoRepository.findById(999L);

        assertThat(resultado).isEmpty();
    }

    @Test
    void findAll_deveRetornarTodosOsPlanejamentosDoContexto() {
        criarPlanejamento("Viagem ao Japão", BigDecimal.valueOf(10000), BigDecimal.ZERO);
        criarPlanejamento("Compra de Notebook", BigDecimal.valueOf(4000), BigDecimal.valueOf(500));

        List<Planejamento> todos = planejamentoRepository.findAll();

        assertThat(todos).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void delete_deveRemoverPlanejamento() {
        Planejamento planejamento = criarPlanejamento("Investimento", BigDecimal.valueOf(2000), BigDecimal.ZERO);
        Long id = planejamento.getId();

        planejamentoRepository.delete(planejamento);

        assertThat(planejamentoRepository.findById(id)).isEmpty();
    }

    @Test
    void save_deveAtualizarPlanejamento() {
        Planejamento planejamento = criarPlanejamento("Compra de Carro", BigDecimal.valueOf(30000), BigDecimal.ZERO);

        planejamento.setValorAtual(BigDecimal.valueOf(5000));
        planejamentoRepository.save(planejamento);

        Planejamento atualizado = planejamentoRepository.findById(planejamento.getId()).orElseThrow();
        assertThat(atualizado.getValorAtual()).isEqualByComparingTo(BigDecimal.valueOf(5000));
    }
}