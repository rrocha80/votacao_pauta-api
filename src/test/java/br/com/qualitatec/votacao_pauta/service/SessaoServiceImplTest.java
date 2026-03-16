package br.com.qualitatec.votacao_pauta.service;

import br.com.qualitatec.votacao_pauta.exception.BusinessException;
import br.com.qualitatec.votacao_pauta.domain.Pauta;
import br.com.qualitatec.votacao_pauta.domain.Sessao;
import br.com.qualitatec.votacao_pauta.mapper.SessaoMapper;
import br.com.qualitatec.votacao_pauta.model.SessaoRequest;
import br.com.qualitatec.votacao_pauta.model.SessaoResponse;
import br.com.qualitatec.votacao_pauta.repository.SessaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessaoServiceImplTest {

    @Mock
    private SessaoRepository repository;
    @Mock
    private SessaoMapper mapper;
    @Mock
    private PautaService pautaService;

    @InjectMocks
    private SessaoServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarSessao_comDuracaoValida_sucesso() {
        SessaoRequest request = new SessaoRequest();
        request.setDuracaoMinutos(10);
        request.setPautaId(1L);

        Pauta pauta = new Pauta();
        pauta.setId(1L);

        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);

        when(pautaService.findById(1L)).thenReturn(pauta);
        when(repository.existsSessaoAtivaByPauta(eq(1L), any(LocalDateTime.class))).thenReturn(0L);
        when(repository.save(any(Sessao.class))).thenReturn(sessao);

        SessaoResponse response = service.criarSessao(request);

        assertNotNull(response);
        verify(repository).save(any(Sessao.class));
    }

    @Test
    void criarSessao_comDuracaoNula_usaPadrao1() {
        SessaoRequest request = new SessaoRequest();
        request.setDuracaoMinutos(null);
        request.setPautaId(1L);

        Pauta pauta = new Pauta();
        pauta.setId(1L);

        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);

        when(pautaService.findById(1L)).thenReturn(pauta);
        when(repository.existsSessaoAtivaByPauta(eq(1L), any(LocalDateTime.class))).thenReturn(0L);
        when(repository.save(any(Sessao.class))).thenReturn(sessao);

        SessaoResponse response = service.criarSessao(request);

        assertNotNull(response);
        verify(repository).save(any(Sessao.class));
        assertEquals(1, request.getDuracaoMinutos());
    }

    @Test
    void criarSessao_comSessaoAberta_lancaExcecao() {
        SessaoRequest request = new SessaoRequest();
        request.setDuracaoMinutos(5);
        request.setPautaId(1L);

        Pauta pauta = new Pauta();
        pauta.setId(1L);

        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);

        when(pautaService.findById(1L)).thenReturn(pauta);
        when(repository.existsSessaoAtivaByPauta(eq(1L), any(LocalDateTime.class))).thenReturn(1L);

        assertThrows(BusinessException.class, () -> service.criarSessao(request));
        verify(repository, never()).save(any());
    }

    @Test
    void buscarPorId_existente_sucesso() {
        Sessao sessao = new Sessao();
        sessao.setId(1L);
        Pauta pauta = new Pauta();
        pauta.setId(2L);
        sessao.setPauta(pauta);

        when(repository.findById(1L)).thenReturn(Optional.of(sessao));
        when(pautaService.findById(2L)).thenReturn(pauta);

        SessaoResponse response = service.buscarPorId(1L);

        assertNotNull(response);
    }

    @Test
    void buscarPorId_inexistente_lancaExcecao() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> service.buscarPorId(1L));
    }

    @Test
    void listarTodas_sucesso() {
        Sessao sessao = new Sessao();
        sessao.setId(1L);
        Pauta pauta = new Pauta();
        pauta.setId(2L);
        sessao.setPauta(pauta);

        when(repository.findAll()).thenReturn(List.of(sessao));
        when(pautaService.findById(2L)).thenReturn(pauta);

        List<SessaoResponse> lista = service.listarTodas();

        assertEquals(1, lista.size());
    }

    @Test
    void deletar_sucesso() {
        doNothing().when(repository).deleteById(1L);
        service.deletar(1L);
        verify(repository).deleteById(1L);
    }
}