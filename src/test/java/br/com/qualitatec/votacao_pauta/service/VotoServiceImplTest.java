package br.com.qualitatec.votacao_pauta.service;

import br.com.qualitatec.votacao_pauta.config.exception.BusinessException;
import br.com.qualitatec.votacao_pauta.config.exception.CpfInvalidoException;
import br.com.qualitatec.votacao_pauta.domain.Pauta;
import br.com.qualitatec.votacao_pauta.domain.Sessao;
import br.com.qualitatec.votacao_pauta.domain.Voto;
import br.com.qualitatec.votacao_pauta.mapper.VotoMapper;
import br.com.qualitatec.votacao_pauta.model.Enum.VotoEnum;
import br.com.qualitatec.votacao_pauta.model.VotoRequest;
import br.com.qualitatec.votacao_pauta.model.VotoResponse;
import br.com.qualitatec.votacao_pauta.repository.SessaoRepository;
import br.com.qualitatec.votacao_pauta.repository.VoroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VotoServiceImplTest {

    @Mock
    private VoroRepository votoRepository;
    @Mock
    private SessaoRepository sessaoRepository;
    @Mock
    private VotoMapper votoMapper;

    @InjectMocks
    private VotoServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarVoto_cpfInvalido_deveLancarCpfInvalidoException() {
        VotoRequest request = VotoRequest.builder()
                .cpf("123")
                .pautaId(1L)
                .voto("SIM")
                .build();

        assertThrows(CpfInvalidoException.class, () -> service.registrarVoto(request));
        verifyNoInteractions(sessaoRepository, votoRepository, votoMapper);
    }

    @Test
    void registrarVoto_sessaoInativa_deveLancarBusinessException() {
        VotoRequest request = VotoRequest.builder()
                .cpf("12345678909")
                .pautaId(1L)
                .voto("SIM")
                .build();

        when(sessaoRepository.existsSessaoAtiva(eq(1L), any(LocalDateTime.class))).thenReturn(0L);

        assertThrows(BusinessException.class, () -> service.registrarVoto(request));
        verify(sessaoRepository).existsSessaoAtiva(eq(1L), any(LocalDateTime.class));
        verifyNoMoreInteractions(sessaoRepository, votoRepository, votoMapper);
    }

    @Test
    void registrarVoto_votoJaRealizado_deveLancarBusinessException() {
        VotoRequest request = VotoRequest.builder()
                .cpf("12345678909")
                .pautaId(1L)
                .voto("SIM")
                .build();

        when(sessaoRepository.existsSessaoAtiva(eq(1L), any(LocalDateTime.class))).thenReturn(2L);
        when(votoRepository.votoRealizado("12345678909", 1L)).thenReturn(true);

        assertThrows(BusinessException.class, () -> service.registrarVoto(request));
        verify(votoRepository).votoRealizado("12345678909", 1L);
        verifyNoMoreInteractions(votoRepository, votoMapper);
    }

    @Test
    void registrarVoto_sessaoNaoEncontrada_deveLancarBusinessException() {
        VotoRequest request = VotoRequest.builder()
                .cpf("12345678909")
                .pautaId(1L)
                .voto("SIM")
                .build();

        when(sessaoRepository.existsSessaoAtiva(eq(1L), any(LocalDateTime.class))).thenReturn(2L);
        when(votoRepository.votoRealizado("12345678909", 1L)).thenReturn(false);
        when(sessaoRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> service.registrarVoto(request));
        verify(sessaoRepository).findById(2L);
    }

    @Test
    void registrarVoto_fluxoFeliz_deveSalvarEVotar() {
        VotoRequest request = VotoRequest.builder()
                .cpf("12345678909")
                .pautaId(1L)
                .voto("SIM")
                .build();

        Sessao sessao = Sessao.builder().id(2L).pauta(Pauta.builder().id(1L).build()).build();
        Voto votoEntity = Voto.builder().id(10L).cpf("12345678909").sessao(sessao).pauta(sessao.getPauta()).voto(VotoEnum.SIM).build();
        VotoResponse response = VotoResponse.builder().id(10L).cpf("12345678909").voto(VotoEnum.SIM).build();

        when(sessaoRepository.existsSessaoAtiva(eq(1L), any(LocalDateTime.class))).thenReturn(2L);
        when(votoRepository.votoRealizado("12345678909", 1L)).thenReturn(false);
        when(sessaoRepository.findById(2L)).thenReturn(Optional.of(sessao));
        when(votoRepository.save(votoEntity)).thenReturn(votoEntity);

        VotoResponse result = service.registrarVoto(request);

        assertNotNull(result);
        assertEquals("12345678909", result.getCpf());
        assertEquals(2L, result.getSessao().getId());
        assertEquals(VotoEnum.SIM, result.getVoto());
        verify(votoRepository).save(votoEntity);
    }

    @Test
    void buscarPorId_existente_deveRetornarVoto() {
        Voto voto = Voto.builder().id(1L).build();
        when(votoRepository.findById(1L)).thenReturn(Optional.of(voto));
        Voto result = service.buscarPorId(1L);
        assertEquals(voto, result);
    }

    @Test
    void buscarPorId_inexistente_deveLancarException() {
        when(votoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.buscarPorId(1L));
    }

    @Test
    void listarTodos_deveRetornarLista() {
        Voto voto = Voto.builder().id(1L).build();
        when(votoRepository.findAll()).thenReturn(List.of(voto));
        List<Voto> lista = service.listarTodos();
        assertEquals(1, lista.size());
        assertEquals(voto, lista.get(0));
    }

    @Test
    void deletar_deveChamarRepository() {
        doNothing().when(votoRepository).deleteById(1L);
        service.deletar(1L);
        verify(votoRepository).deleteById(1L);
    }
}