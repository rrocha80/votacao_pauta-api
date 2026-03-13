package br.com.qualitatec.votacao_pauta.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    @ManyToOne
    @JoinColumn(name = "pautaId", nullable = false)
    private Pauta pauta;

}