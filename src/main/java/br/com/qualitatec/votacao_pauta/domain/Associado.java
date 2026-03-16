package br.com.qualitatec.votacao_pauta.domain;

import br.com.qualitatec.votacao_pauta.model.Enum.StatusAssociadoEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Associado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String cpf;

    @Enumerated(EnumType.STRING)
    private StatusAssociadoEnum status;
}
