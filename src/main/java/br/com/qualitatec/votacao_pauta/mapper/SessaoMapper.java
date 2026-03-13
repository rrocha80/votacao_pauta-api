package br.com.qualitatec.votacao_pauta.mapper;

import br.com.qualitatec.votacao_pauta.domain.Pauta;
import br.com.qualitatec.votacao_pauta.domain.Sessao;
import br.com.qualitatec.votacao_pauta.model.SessaoResponse;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public class SessaoMapper {

    public static Sessao toEntity(Pauta pauta) {
        return Sessao.builder()
                .pauta(pauta)
                .build();
    }

    public static SessaoResponse toResponse(Sessao sessao, Pauta pauta) {
        return SessaoResponse.builder()
                .id(sessao.getId())
                .dataHoraInicio(sessao.getDataHoraInicio())
                .dataHoraFim(sessao.getDataHoraFim())
                .pauta(pauta)
                .build();
    }
}
