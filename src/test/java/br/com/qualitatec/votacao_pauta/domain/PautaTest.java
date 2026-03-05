package br.com.qualitatec.votacao_pauta.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PautaTest {

    // Testa o builder e os getters
    @Test
    void deveConstruirPautaComBuilderECamposCorretos() {
        Pauta pauta = Pauta.builder()
                .id(1L)
                .titulo("Titulo Teste")
                .descricao("Descricao Teste")
                .build();

        assertEquals(1L, pauta.getId());
        assertEquals("Titulo Teste", pauta.getTitulo());
        assertEquals("Descricao Teste", pauta.getDescricao());
    }

    // Testa os setters
    @Test
    void devePermitirAlterarCamposComSetters() {
        Pauta pauta = new Pauta();
        pauta.setId(2L);
        pauta.setTitulo("Outro Titulo");
        pauta.setDescricao("Outra Descricao");

        assertEquals(2L, pauta.getId());
        assertEquals("Outro Titulo", pauta.getTitulo());
        assertEquals("Outra Descricao", pauta.getDescricao());
    }

    // Testa equals e hashCode
    @Test
    void deveCompararPautasPorIdComEqualsEHashCode() {
        Pauta pauta1 = Pauta.builder()
                .id(1L)
                .titulo("Titulo")
                .descricao("Descricao")
                .build();

        Pauta pauta2 = Pauta.builder()
                .id(1L)
                .titulo("Titulo")
                .descricao("Descricao")
                .build();

        Pauta pauta3 = Pauta.builder()
                .id(2L)
                .titulo("Outro")
                .descricao("Outra")
                .build();

        assertEquals(pauta1, pauta2);
        assertEquals(pauta1.hashCode(), pauta2.hashCode());
        assertNotEquals(pauta1, pauta3);
        assertNotEquals(pauta1.hashCode(), pauta3.hashCode());
    }


}