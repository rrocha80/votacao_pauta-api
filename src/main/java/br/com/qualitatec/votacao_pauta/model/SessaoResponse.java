package br.com.qualitatec.votacao_pauta.model;

import br.com.qualitatec.votacao_pauta.domain.Pauta;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessaoResponse {

    private Long id;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private Pauta pauta;

}
