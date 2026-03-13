package br.com.qualitatec.votacao_pauta.repository;

import br.com.qualitatec.votacao_pauta.domain.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END " +
            "FROM Sessao s " +
            "WHERE s.pauta.id = :pautaId AND s.dataHoraFim > :now")
    Boolean existsSessaoAtiva(Long pautaId, LocalDateTime now);
}