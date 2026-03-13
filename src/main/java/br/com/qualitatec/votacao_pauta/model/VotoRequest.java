package br.com.qualitatec.votacao_pauta.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotoRequest {

    @NotNull(message = "O campo PAUTA é obrigatório")
    private Long pautaId;

    @NotNull(message = "O campo CPF é obrigatório")
    private String cpf;

    @NotNull(message = "O campo VOTO é obrigatório (Simou Não)")
    private String voto; // Exemplo: "SIM" ou "NAO"
}