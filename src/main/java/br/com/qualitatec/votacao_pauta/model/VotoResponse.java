package br.com.qualitatec.votacao_pauta.model;

import br.com.qualitatec.votacao_pauta.domain.Sessao;
import br.com.qualitatec.votacao_pauta.model.Enum.VotoEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotoResponse {
    private Long id;
    private Sessao sessao;
    private String cpf;
    private VotoEnum voto;
    private LocalDateTime dataHoraVoto;
}