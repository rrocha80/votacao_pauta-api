package br.com.qualitatec.votacao_pauta.model;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class SessaoRequest {
    @Min(value = 1, message = "A duração deve ser maior que zero")
    private Integer duracaoMinutos;
    private Long pautaId;
}