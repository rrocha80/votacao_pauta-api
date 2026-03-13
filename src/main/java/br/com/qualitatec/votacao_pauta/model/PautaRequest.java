package br.com.qualitatec.votacao_pauta.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaRequest {
    @NotBlank(message = "O nome do título é obrigatório")
    @Size(min = 4, max = 150, message = "O Título deve conter entre 4 a 150 caracteres.")
    private String titulo;

    @NotBlank(message = "O nome da descricao é obrigatório")
    @Size(min = 10, max = 255, message = "O Título deve conter entre 10 a 255 caracteres.")
    private String descricao;
}
