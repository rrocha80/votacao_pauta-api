package br.com.qualitatec.votacao_pauta.mapper;

import br.com.qualitatec.votacao_pauta.domain.Pauta;
import br.com.qualitatec.votacao_pauta.domain.Sessao;
import br.com.qualitatec.votacao_pauta.domain.Voto;
import br.com.qualitatec.votacao_pauta.model.Enum.VotoEnum;
import br.com.qualitatec.votacao_pauta.model.VotoRequest;
import br.com.qualitatec.votacao_pauta.model.VotoResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class VotoMapperTest {

    @Test
    void toEntity_deveMapearCamposCorretamente_SIM() {
        Pauta pauta = Pauta.builder().id(1L).build();
        Sessao sessao = Sessao.builder().id(2L).pauta(pauta).build();

        VotoRequest request = VotoRequest.builder()
                .cpf("12345678901")
                .voto("SIM")
                .build();

        Voto voto = VotoMapper.toEntity(request, sessao);

        assertEquals(sessao, voto.getSessao());
        assertEquals(pauta, voto.getPauta());
        assertEquals("12345678901", voto.getCpf());
        assertEquals(VotoEnum.SIM, voto.getVoto());
        assertNotNull(voto.getDataHoraVoto());
    }

    @Test
    void toEntity_deveAceitarMinusculo() {
        Pauta pauta = Pauta.builder().id(1L).build();
        Sessao sessao = Sessao.builder().id(2L).pauta(pauta).build();

        VotoRequest request = VotoRequest.builder()
                .cpf("12345678901")
                .voto("nao")
                .build();

        Voto voto = VotoMapper.toEntity(request, sessao);

        assertEquals(VotoEnum.NAO, voto.getVoto());
    }

    @Test
    void toEntity_valorInvalidoDeveLancarExcecao() {
        Pauta pauta = Pauta.builder().id(1L).build();
        Sessao sessao = Sessao.builder().id(2L).pauta(pauta).build();

        VotoRequest request = VotoRequest.builder()
                .cpf("12345678901")
                .voto("TALVEZ")
                .build();

        assertThrows(IllegalArgumentException.class, () -> VotoMapper.toEntity(request, sessao));
    }

    @Test
    void toResponse_deveMapearCamposCorretamente() {
        Pauta pauta = Pauta.builder().id(1L).build();
        Sessao sessao = Sessao.builder().id(2L).pauta(pauta).build();
        LocalDateTime dataHora = LocalDateTime.now();

        Voto voto = Voto.builder()
                .id(10L)
                .sessao(sessao)
                .pauta(pauta)
                .cpf("98765432100")
                .voto(VotoEnum.SIM)
                .dataHoraVoto(dataHora)
                .build();

        VotoResponse response = VotoMapper.toResponse(voto);

        assertEquals(10L, response.getId());
        assertEquals(2L, response.getSessao().getId());
        assertEquals("98765432100", response.getCpf());
        assertEquals(VotoEnum.SIM, response.getVoto());
        assertEquals(dataHora, response.getDataHoraVoto());
    }
}