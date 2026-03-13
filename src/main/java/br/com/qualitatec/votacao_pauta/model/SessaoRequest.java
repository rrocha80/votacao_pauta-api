package br.com.qualitatec.votacao_pauta.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessaoRequest {
    @Min(value = 1, message = "A duração deve ser maior que zero")
    @NotNull(message = "O campo DURAÇÃO é obrigatório (Simou Não)")
    private Integer duracaoMinutos;

    @NotNull(message = "O campo PAUTA é obrigatório (Simou Não)")
    private Long pautaId;
}