package br.com.qualitatec.votacao_pauta.mapper;

import br.com.qualitatec.votacao_pauta.domain.Pauta;
import br.com.qualitatec.votacao_pauta.domain.Sessao;
import br.com.qualitatec.votacao_pauta.model.SessaoResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SessaoMapperTest {

    @Test
    void testToEntity() {
        Pauta pauta = Pauta.builder()
                .id(1L)
                .titulo("Pauta Teste")
                .descricao("Descricao Teste")
                .build();

        Sessao sessao = SessaoMapper.toEntity(pauta);

        assertNotNull(sessao);
        assertEquals(pauta, sessao.getPauta());
    }

    @Test
    void testToResponse() {
        Pauta pauta = Pauta.builder()
                .id(2L)
                .titulo("Pauta Response")
                .descricao("Descricao Response")
                .build();

        Sessao sessao = Sessao.builder()
                .id(10L)
                .dataHoraInicio(LocalDateTime.now())
                .dataHoraFim(LocalDateTime.now().plusMinutes(5))
                .pauta(pauta)
                .build();

        SessaoResponse response = SessaoMapper.toResponse(sessao, pauta);

        assertNotNull(response);
        assertEquals(sessao.getId(), response.getId());
        assertEquals(sessao.getDataHoraInicio(), response.getDataHoraInicio());
        assertEquals(sessao.getDataHoraFim(), response.getDataHoraFim());
        assertEquals(pauta, response.getPauta());
    }
}