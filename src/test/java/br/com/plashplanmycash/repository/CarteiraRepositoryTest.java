package br.com.plashplanmycash.repository;

import br.com.plashplanmycash.domain.entity.Carteira;
import br.com.plashplanmycash.domain.entity.Usuario;
import br.com.plashplanmycash.domain.enums.TipoCarteira;
import br.com.plashplanmycash.domain.enums.TipoConta;
import br.com.plashplanmycash.domain.enums.TipoMoeda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
class CarteiraRepositoryTest {

    @Autowired
    private CarteiraRepository carteiraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = usuarioRepository.save(new Usuario("Bruno", "bruno.carteira@email.com", "senha_hash"));
    }

    private Carteira criarCarteira(String apelido) {
        return carteiraRepository.save(Carteira.builder()
                .usuario(usuario)
                .apelido(apelido)
                .tipoCarteira(TipoCarteira.BANCO)
                .tipoConta(TipoConta.NACIONAL)
                .tipoMoeda(TipoMoeda.BRL)
                .saldo(BigDecimal.valueOf(1000))
                .build());
    }

    @Test
    void existsByUsuarioIdAndApelido_deveRetornarTrue_quandoApelidoExiste() {
        criarCarteira("Nubank");

        boolean existe = carteiraRepository.existsByUsuarioIdAndApelido(usuario.getId(), "Nubank");

        assertThat(existe).isTrue();
    }

    @Test
    void existsByUsuarioIdAndApelido_deveRetornarFalse_quandoApelidoNaoExiste() {
        boolean existe = carteiraRepository.existsByUsuarioIdAndApelido(usuario.getId(), "Nubank");

        assertThat(existe).isFalse();
    }

    @Test
    void existsByUsuarioIdAndApelido_deveRetornarFalse_quandoUsuarioDiferente() {
        criarCarteira("Nubank");
        Usuario outroUsuario = usuarioRepository.save(new Usuario("Ana", "ana.carteira@email.com", "senha_hash"));

        boolean existe = carteiraRepository.existsByUsuarioIdAndApelido(outroUsuario.getId(), "Nubank");

        assertThat(existe).isFalse();
    }

    @Test
    void existsByUsuarioIdAndApelidoAndIdNot_deveRetornarFalse_paraMesmaCarteira() {
        Carteira carteira = criarCarteira("Inter");

        boolean existe = carteiraRepository.existsByUsuarioIdAndApelidoAndIdNot(
                usuario.getId(), "Inter", carteira.getId());

        assertThat(existe).isFalse();
    }

    @Test
    void existsByUsuarioIdAndApelidoAndIdNot_deveRetornarTrue_quandoOutraCarteiraTemMesmoApelido() {
        criarCarteira("Inter");
        Carteira outraCarteira = criarCarteira("XP");

        boolean existe = carteiraRepository.existsByUsuarioIdAndApelidoAndIdNot(
                usuario.getId(), "Inter", outraCarteira.getId());

        assertThat(existe).isTrue();
    }

    @Test
    void save_devePersistirCarteira() {
        Carteira carteira = criarCarteira("Binance");

        assertThat(carteira.getId()).isNotNull();
        assertThat(carteira.getCriadoEm()).isNotNull();
        assertThat(carteira.getSaldo()).isEqualByComparingTo(BigDecimal.valueOf(1000));
    }

    @Test
    void delete_deveRemoverCarteira() {
        Carteira carteira = criarCarteira("Itaú");
        Long id = carteira.getId();

        carteiraRepository.delete(carteira);

        assertThat(carteiraRepository.findById(id)).isEmpty();
    }
}