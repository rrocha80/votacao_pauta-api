package br.com.qualitatec.votacao_pauta.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaResponse {
    private Long id;
    private String titulo;
    private String descricao;
}
