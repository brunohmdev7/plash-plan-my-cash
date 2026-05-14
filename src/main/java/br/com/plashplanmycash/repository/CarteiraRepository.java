package br.com.plashplanmycash.repository;

import br.com.plashplanmycash.domain.entity.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

    boolean existsByUsuarioIdAndApelido(Long usuarioId, String apelido);

    boolean existsByUsuarioIdAndApelidoAndIdNot(Long usuarioId, String apelido, Long id);
}