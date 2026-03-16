package br.com.qualitatec.votacao_pauta.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaResultadoResponse {
    private Long id;
    private String titulo;
    private String descricao;
    private Long totalVotosSim;
    private Long totalVotosNao;
}
