package br.com.qualitatec.votacao_pauta.repository;

import br.com.qualitatec.votacao_pauta.domain.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PautaRepository extends JpaRepository<Pauta, Long> {
    @Query("SELECT p FROM Pauta p WHERE LOWER(p.titulo) = LOWER(:titulo)")
    Optional<Pauta> findByTituloExistente(String titulo);
}
