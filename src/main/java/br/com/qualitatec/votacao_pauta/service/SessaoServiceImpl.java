package br.com.qualitatec.votacao_pauta.service;

import br.com.qualitatec.votacao_pauta.exception.BusinessException;
import br.com.qualitatec.votacao_pauta.mapper.SessaoMapper;
import br.com.qualitatec.votacao_pauta.model.SessaoRequest;
import br.com.qualitatec.votacao_pauta.model.SessaoResponse;
import br.com.qualitatec.votacao_pauta.repository.SessaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessaoServiceImpl implements SessaoService {

    private final SessaoRepository sessaoRepository;
    private final SessaoMapper mapper;
    private final PautaService pautaService;

    private final String SESSAO_ABERTA_PARA_PAUTA = "Já existe uma sessão aberta para esta pauta";
    private final String SESSAO_NAO_ENCONTRADA = "Sessão não encontrada com id: ";

    @Override
    public SessaoResponse criarSessao(SessaoRequest request) {
        var pleitoEntity = pautaService.findById(request.getPautaId());
        var sessaoEntity = mapper.toEntity(pleitoEntity);

        if (request.getDuracaoMinutos() == null) {
            request.setDuracaoMinutos(1); // Define duração padrão de 1 minuto
        }

        sessaoEntity.setDataHoraInicio(LocalDateTime.now());
        sessaoEntity.setDataHoraFim(sessaoEntity.getDataHoraInicio().plusMinutes(request.getDuracaoMinutos()));

        var existeSessaoAberta = sessaoRepository.existsSessaoAtivaByPauta(request.getPautaId(), LocalDateTime.now());
        if (existeSessaoAberta != null && existeSessaoAberta > 0L) {
            throw new BusinessException(SESSAO_ABERTA_PARA_PAUTA);
        }

        sessaoRepository.save(sessaoEntity);

        var response = SessaoMapper.toResponse(sessaoEntity, pleitoEntity);

        return response;
    }

    @Override
    public List<SessaoResponse> listarTodas() {
        var sessoes = sessaoRepository.findAll();
        return sessoes.stream()
                .map(sessao -> {
                    var pauta = pautaService.findById(sessao.getPauta().getId());
                    return SessaoMapper.toResponse(sessao, pauta);
                })
                .toList();
    }

    @Override
    public SessaoResponse buscarPorId(Long id) {
        var sessao = sessaoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(SESSAO_NAO_ENCONTRADA + id));

        var pauta = pautaService.findById(sessao.getPauta().getId());
        return SessaoMapper.toResponse(sessao, pauta);
     }

    @Override
    public void deletar(Long id) {
        sessaoRepository.deleteById(id);
    }

    @Override
    public List<SessaoResponse> listarSessoesAtivas() {
        return sessaoRepository.findSessoesAtivas(LocalDateTime.now()).stream()
                .map(sessao -> {
                    var pauta = pautaService.findById(sessao.getPauta().getId());
                    return SessaoMapper.toResponse(sessao, pauta);
                })
                .toList();
    }
}