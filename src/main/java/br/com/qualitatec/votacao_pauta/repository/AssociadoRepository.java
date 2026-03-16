package br.com.qualitatec.votacao_pauta.repository;

import br.com.qualitatec.votacao_pauta.domain.Associado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END "+
            "FROM Associado a WHERE a.cpf = :cpf AND a.status = 'ABLE_TO_VOTE'")
    Boolean findByAssociadoAtivo(String cpf);
}