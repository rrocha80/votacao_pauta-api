package br.com.qualitatec.votacao_pauta.repository;

import br.com.qualitatec.votacao_pauta.domain.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PautaRepository extends JpaRepository<Pauta, Long> {
    Optional<Pauta> findByTituloIgnoreCase(String titulo);
}
