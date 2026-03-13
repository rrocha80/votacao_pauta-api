package br.com.qualitatec.votacao_pauta.mapper;

import br.com.qualitatec.votacao_pauta.domain.Sessao;
import br.com.qualitatec.votacao_pauta.domain.Voto;
import br.com.qualitatec.votacao_pauta.model.Enum.VotoEnum;
import br.com.qualitatec.votacao_pauta.model.VotoRequest;
import br.com.qualitatec.votacao_pauta.model.VotoResponse;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public class VotoMapper {

    public static Voto toEntity(VotoRequest request, Sessao sessao) {
        var votoEnum = VotoEnum.valueOf(request.getVoto().toUpperCase());
        return Voto.builder()
                .sessao(sessao)
                .pauta(sessao.getPauta())
                .cpf(request.getCpf())
                .voto(votoEnum)
                .dataHoraVoto(LocalDateTime.now())
                .build();
    }

    public static VotoResponse toResponse(Voto voto) {
        return VotoResponse.builder()
                .id(voto.getId())
                .sessao(voto.getSessao())
                .cpf(voto.getCpf())
                .voto(voto.getVoto())
                .dataHoraVoto(voto.getDataHoraVoto())
                .build();
    }
}