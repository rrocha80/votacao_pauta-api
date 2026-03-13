package br.com.qualitatec.votacao_pauta.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SessaoTest {

    @Test
    void deveConstruirSessaoComBuilderECamposCorretos() {
        Pauta pauta = Pauta.builder()
                .id(1L)
                .titulo("Pauta Teste")
                .descricao("Descricao Teste")
                .build();

        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fim = inicio.plusMinutes(5);

        Sessao sessao = Sessao.builder()
                .id(10L)
                .dataHoraInicio(inicio)
                .dataHoraFim(fim)
                .pauta(pauta)
                .build();

        assertEquals(10L, sessao.getId());
        assertEquals(inicio, sessao.getDataHoraInicio());
        assertEquals(fim, sessao.getDataHoraFim());
        assertEquals(pauta, sessao.getPauta());
    }

    @Test
    void devePermitirAlterarCamposComSetters() {
        Sessao sessao = new Sessao();
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fim = inicio.plusMinutes(10);
        Pauta pauta = new Pauta();
        pauta.setId(2L);

        sessao.setId(20L);
        sessao.setDataHoraInicio(inicio);
        sessao.setDataHoraFim(fim);
        sessao.setPauta(pauta);

        assertEquals(20L, sessao.getId());
        assertEquals(inicio, sessao.getDataHoraInicio());
        assertEquals(fim, sessao.getDataHoraFim());
        assertEquals(pauta, sessao.getPauta());
    }

    @Test
    void deveCompararSessoesPorIdComEqualsEHashCode() {
        Pauta pauta = Pauta.builder().id(1L).build();
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fim = inicio.plusMinutes(5);

        Sessao sessao1 = Sessao.builder()
                .id(100L)
                .dataHoraInicio(inicio)
                .dataHoraFim(fim)
                .pauta(pauta)
                .build();

        Sessao sessao2 = Sessao.builder()
                .id(100L)
                .dataHoraInicio(inicio)
                .dataHoraFim(fim)
                .pauta(pauta)
                .build();

        Sessao sessao3 = Sessao.builder()
                .id(101L)
                .dataHoraInicio(inicio)
                .dataHoraFim(fim)
                .pauta(pauta)
                .build();

        assertEquals(sessao1, sessao2);
        assertEquals(sessao1.hashCode(), sessao2.hashCode());
    }

    @Test
    void devePermitirCamposNulos() {
        Sessao sessao = new Sessao();
        sessao.setId(null);
        sessao.setDataHoraInicio(null);
        sessao.setDataHoraFim(null);
        sessao.setPauta(null);

        assertNull(sessao.getId());
        assertNull(sessao.getDataHoraInicio());
        assertNull(sessao.getDataHoraFim());
        assertNull(sessao.getPauta());
    }
}