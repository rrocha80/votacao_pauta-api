package br.com.qualitatec.votacao_pauta.mapper;

import br.com.qualitatec.votacao_pauta.domain.Pauta;
import br.com.qualitatec.votacao_pauta.model.PautaRequest;
import br.com.qualitatec.votacao_pauta.model.PautaResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class PautaMapperTest {

    private final PautaMapper mapper = Mappers.getMapper(PautaMapper.class);

    @Test
    void testToEntity() {
        PautaRequest request = PautaRequest.builder()
                .titulo("Título Teste")
                .descricao("Descrição de teste para pauta")
                .build();

        Pauta pauta = mapper.toEntity(request);

        assertNull(pauta.getId()); // id deve ser ignorado
        assertEquals(request.getTitulo(), pauta.getTitulo());
        assertEquals(request.getDescricao(), pauta.getDescricao());
    }

    @Test
    void testToResponseDTO() {
        Pauta pauta = Pauta.builder()
                .id(1L)
                .titulo("Título Teste")
                .descricao("Descrição de teste para pauta")
                .build();

        PautaResponse response = mapper.toResponseDTO(pauta);

        assertEquals(pauta.getId(), response.getId());
        assertEquals(pauta.getTitulo(), response.getTitulo());
        assertEquals(pauta.getDescricao(), response.getDescricao());
    }
}