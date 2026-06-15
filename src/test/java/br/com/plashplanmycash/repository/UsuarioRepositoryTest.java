package br.com.plashplanmycash.repository;

import br.com.plashplanmycash.domain.entity.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario criarUsuario(String nome, String email) {
        return usuarioRepository.save(new Usuario(nome, email, "senha_hash"));
    }

    @Test
    void findByEmail_deveRetornarUsuario_quandoEmailExiste() {
        criarUsuario("Bruno", "bruno@email.com");

        UserDetails resultado = usuarioRepository.findByEmail("bruno@email.com");

        assertThat(resultado).isNotNull();
        assertThat(resultado.getUsername()).isEqualTo("bruno@email.com");
    }

    @Test
    void findByEmail_deveRetornarNull_quandoEmailNaoExiste() {
        UserDetails resultado = usuarioRepository.findByEmail("naoexiste@email.com");

        assertThat(resultado).isNull();
    }

    @Test
    void save_devePersistirUsuario() {
        Usuario usuario = criarUsuario("Ana", "ana@email.com");

        assertThat(usuario.getId()).isNotNull();
        assertThat(usuario.getCriadoEm()).isNotNull();
        assertThat(usuario.getAtualizadoEm()).isNotNull();
    }

    @Test
    void delete_deveRemoverUsuario() {
        Usuario usuario = criarUsuario("Carlos", "carlos@email.com");
        Long id = usuario.getId();

        usuarioRepository.delete(usuario);

        assertThat(usuarioRepository.findById(id)).isEmpty();
    }
}