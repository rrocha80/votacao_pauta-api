package br.com.qualitatec.votacao_pauta.mapper;

import br.com.qualitatec.votacao_pauta.domain.Associado;
import br.com.qualitatec.votacao_pauta.model.AssociadoResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssociadoMapper {
    List<AssociadoResponse> toResponseDTO(List<Associado> entities);
}
