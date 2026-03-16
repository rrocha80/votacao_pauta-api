package br.com.qualitatec.votacao_pauta.model;

import br.com.qualitatec.votacao_pauta.model.Enum.StatusAssociadoEnum;
import lombok.Data;

@Data
public class AssociadoResponse {
    private String cpf;
    private StatusAssociadoEnum status;
}
