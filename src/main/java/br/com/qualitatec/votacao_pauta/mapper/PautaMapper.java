package br.com.qualitatec.votacao_pauta.mapper;

import br.com.qualitatec.votacao_pauta.domain.Pauta;
import br.com.qualitatec.votacao_pauta.model.PautaRequest;
import br.com.qualitatec.votacao_pauta.model.PautaResponse;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PautaMapper {
    Pauta toEntity(PautaRequest request);

    PautaResponse toResponseDTO(Pauta entity);
}
