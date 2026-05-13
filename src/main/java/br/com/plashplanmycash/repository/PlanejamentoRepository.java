package br.com.plashplanmycash.repository;

import br.com.plashplanmycash.domain.entity.Planejamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanejamentoRepository extends JpaRepository<Planejamento, Long> {
}