package br.com.qualitatec.votacao_pauta.repository;

import br.com.qualitatec.votacao_pauta.domain.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VoroRepository extends JpaRepository<Voto, Long> {
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END " +
            "FROM Voto v " +
            "WHERE v.cpf = :cpf AND v.pauta.id = :pautaId")
    Boolean votoRealizado(String cpf, Long pautaId);
}
