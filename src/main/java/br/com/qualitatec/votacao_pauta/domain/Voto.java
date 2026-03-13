package br.com.qualitatec.votacao_pauta.domain;

import br.com.qualitatec.votacao_pauta.model.Enum.VotoEnum;
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
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cpf;

    @ManyToOne
    @JoinColumn(name = "pautaId", nullable = false)
    private Pauta pauta;

    @ManyToOne
    @JoinColumn(name = "sessaoId", nullable = false)
    private Sessao sessao;

    private LocalDateTime dataHoraVoto;

    @Enumerated(EnumType.STRING)
    private VotoEnum voto;
}
