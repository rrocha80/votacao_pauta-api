package br.com.qualitatec.votacao_pauta.service;

import br.com.qualitatec.votacao_pauta.config.exception.BusinessException;
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

    private final SessaoRepository repository;
    private final SessaoMapper mapper;
    private final PautaService pautaService;

    @Override
    public SessaoResponse criarSessao(SessaoRequest request) {
        var pleitoEntity = pautaService.findById(request.getPautaId());
        var sessaoEntity = mapper.toEntity(pleitoEntity);

        if (request.getDuracaoMinutos() == null) {
            request.setDuracaoMinutos(1); // Define duração padrão de 1 minuto
        }

        sessaoEntity.setDataHoraInicio(LocalDateTime.now());
        sessaoEntity.setDataHoraFim(sessaoEntity.getDataHoraInicio().plusMinutes(request.getDuracaoMinutos()));

        var existeSessaoAberta = repository.existsSessaoAtiva(request.getPautaId(), LocalDateTime.now());
        if (existeSessaoAberta) {
            throw new BusinessException("Já existe uma sessão aberta para esta pauta");
        }

        repository.save(sessaoEntity);

        var response = SessaoMapper.toResponse(sessaoEntity, pleitoEntity);

        return response;
    }

    @Override
    public List<SessaoResponse> listarTodas() {
        var sessoes = repository.findAll();
        return sessoes.stream()
                .map(sessao -> {
                    var pauta = pautaService.findById(sessao.getPauta().getId());
                    return SessaoMapper.toResponse(sessao, pauta);
                })
                .toList();
    }

    @Override
    public SessaoResponse buscarPorId(Long id) {
        var sessao = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Sessão não encontrada com id: " + id));

        var pauta = pautaService.findById(sessao.getPauta().getId());
        return SessaoMapper.toResponse(sessao, pauta);
     }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}