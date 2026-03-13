package br.com.qualitatec.votacao_pauta.domain;

import br.com.qualitatec.votacao_pauta.model.Enum.VotoEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class VotoTest {

    @Test
    void deveConstruirVotoComBuilderECamposCorretos() {
        Pauta pauta = Pauta.builder().id(1L).titulo("Pauta Teste").build();
        Sessao sessao = Sessao.builder().id(2L).pauta(pauta).build();
        LocalDateTime dataHora = LocalDateTime.now();

        Voto voto = Voto.builder()
                .id(10L)
                .cpf("12345678901")
                .pauta(pauta)
                .sessao(sessao)
                .dataHoraVoto(dataHora)
                .voto(VotoEnum.SIM)
                .build();

        assertEquals(10L, voto.getId());
        assertEquals("12345678901", voto.getCpf());
        assertEquals(pauta, voto.getPauta());
        assertEquals(sessao, voto.getSessao());
        assertEquals(dataHora, voto.getDataHoraVoto());
        assertEquals(VotoEnum.SIM, voto.getVoto());
    }

    @Test
    void devePermitirAlterarCamposComSetters() {
        Voto voto = new Voto();
        Pauta pauta = new Pauta();
        pauta.setId(3L);
        Sessao sessao = new Sessao();
        sessao.setId(4L);
        LocalDateTime dataHora = LocalDateTime.now();

        voto.setId(20L);
        voto.setCpf("98765432100");
        voto.setPauta(pauta);
        voto.setSessao(sessao);
        voto.setDataHoraVoto(dataHora);
        voto.setVoto(VotoEnum.NAO);

        assertEquals(20L, voto.getId());
        assertEquals("98765432100", voto.getCpf());
        assertEquals(pauta, voto.getPauta());
        assertEquals(sessao, voto.getSessao());
        assertEquals(dataHora, voto.getDataHoraVoto());
        assertEquals(VotoEnum.NAO, voto.getVoto());
    }

    @Test
    void deveCompararVotosPorIdComEqualsEHashCode() {
        Pauta pauta = Pauta.builder().id(1L).build();
        Sessao sessao = Sessao.builder().id(2L).pauta(pauta).build();
        LocalDateTime dataHora = LocalDateTime.now();

        Voto voto1 = Voto.builder()
                .id(100L)
                .cpf("11111111111")
                .pauta(pauta)
                .sessao(sessao)
                .dataHoraVoto(dataHora)
                .voto(VotoEnum.SIM)
                .build();

        Voto voto2 = Voto.builder()
                .id(100L)
                .cpf("22222222222")
                .pauta(pauta)
                .sessao(sessao)
                .dataHoraVoto(dataHora)
                .voto(VotoEnum.NAO)
                .build();

        Voto voto3 = Voto.builder()
                .id(101L)
                .cpf("33333333333")
                .pauta(pauta)
                .sessao(sessao)
                .dataHoraVoto(dataHora)
                .voto(VotoEnum.SIM)
                .build();

        assertEquals(voto1, voto2);
        assertEquals(voto1.hashCode(), voto2.hashCode());
    }

    @Test
    void devePermitirCamposNulos() {
        Voto voto = new Voto();
        voto.setId(null);
        voto.setCpf(null);
        voto.setPauta(null);
        voto.setSessao(null);
        voto.setDataHoraVoto(null);
        voto.setVoto(null);

        assertNull(voto.getId());
        assertNull(voto.getCpf());
        assertNull(voto.getPauta());
        assertNull(voto.getSessao());
        assertNull(voto.getDataHoraVoto());
        assertNull(voto.getVoto());
    }
}