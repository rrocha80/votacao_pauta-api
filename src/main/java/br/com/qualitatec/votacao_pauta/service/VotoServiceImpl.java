package br.com.qualitatec.votacao_pauta.service;

import br.com.qualitatec.votacao_pauta.config.exception.BusinessException;
import br.com.qualitatec.votacao_pauta.config.exception.CpfInvalidoException;
import br.com.qualitatec.votacao_pauta.domain.Voto;
import br.com.qualitatec.votacao_pauta.mapper.VotoMapper;
import br.com.qualitatec.votacao_pauta.model.VotoRequest;
import br.com.qualitatec.votacao_pauta.model.VotoResponse;
import br.com.qualitatec.votacao_pauta.repository.SessaoRepository;
import br.com.qualitatec.votacao_pauta.repository.VoroRepository;
import br.com.qualitatec.votacao_pauta.util.CpfValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VotoServiceImpl implements VotoService {

    private final VoroRepository votoRepository;

    private final SessaoRepository sessaoRepository;

    private final VotoMapper votoMapper;

    @Override
    public VotoResponse registrarVoto(VotoRequest voto) {
        if (!CpfValidator.isValid(voto.getCpf())) {
            throw new CpfInvalidoException("CPF inválido: " + voto.getCpf());
        };

        var sessaoAtiva = sessaoRepository.existsSessaoAtiva(voto.getPautaId(), LocalDateTime.now());
        if (sessaoAtiva == null || sessaoAtiva == 0L) {
            throw new BusinessException("Sessão não está ativa para a pauta: " + voto.getPautaId());
        }

        var votoRealizado = votoRepository.votoRealizado(voto.getCpf(), voto.getPautaId());

        if (votoRealizado) {
            throw new BusinessException("Voto já computado para este CPF");
        }

        var sessao = sessaoRepository.findById(sessaoAtiva)
                .orElseThrow(() -> new BusinessException("Sessão não encontrada com id: " + voto.getPautaId()));

        var votoEntity = votoMapper.toEntity(voto, sessao);

        votoRepository.save(votoEntity);

        return votoMapper.toResponse(votoEntity);
    }

    @Override
    public Voto buscarPorId(Long id) {
        return votoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voto não encontrado"));
    }

    @Override
    public List<Voto> listarTodos() {
        return votoRepository.findAll();
    }

    @Override
    public void deletar(Long id) {
        votoRepository.deleteById(id);
    }
}