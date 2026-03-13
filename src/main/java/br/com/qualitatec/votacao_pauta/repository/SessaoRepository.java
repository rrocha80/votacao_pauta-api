package br.com.qualitatec.votacao_pauta.repository;

import br.com.qualitatec.votacao_pauta.domain.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    @Query("SELECT s.id " +
            "FROM Sessao s " +
            "WHERE s.pauta.id = :pautaId AND s.dataHoraFim > :now")
    Long existsSessaoAtiva(Long pautaId, LocalDateTime now);

    @Query("SELECT s " +
            "FROM Sessao s " +
            "WHERE s.dataHoraInicio <= :now AND s.dataHoraFim >= :now")
    List<Sessao> findSessoesAtivas(LocalDateTime now);
}