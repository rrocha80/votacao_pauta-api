package br.com.qualitatec.votacao_pauta.service;

import br.com.qualitatec.votacao_pauta.client.AssociadoClient;
import br.com.qualitatec.votacao_pauta.exception.BusinessException;
import br.com.qualitatec.votacao_pauta.exception.CpfInvalidoException;
import br.com.qualitatec.votacao_pauta.domain.Pauta;
import br.com.qualitatec.votacao_pauta.domain.Voto;
import br.com.qualitatec.votacao_pauta.mapper.VotoMapper;
import br.com.qualitatec.votacao_pauta.model.Enum.VotoEnum;
import br.com.qualitatec.votacao_pauta.model.PautaResultadoResponse;
import br.com.qualitatec.votacao_pauta.model.VotoRequest;
import br.com.qualitatec.votacao_pauta.model.VotoResponse;
import br.com.qualitatec.votacao_pauta.repository.AssociadosRepository;
import br.com.qualitatec.votacao_pauta.repository.SessaoRepository;
import br.com.qualitatec.votacao_pauta.repository.VoroRepository;
import br.com.qualitatec.votacao_pauta.util.CpfValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VotoServiceImpl implements VotoService {

    private final VoroRepository votoRepository;

    private final SessaoRepository sessaoRepository;

    private final PautaService pautaService;

    private final AssociadosRepository associadosRepository;

    private final VotoMapper votoMapper;

    private final AssociadoClient  associadoClient;

    @Value("${usar-associado-remoto}")
    private boolean usarAssociadoRemoto;

    private final String CPF_INVALIDO = "CPF inválido: ";
    private final String HABILITADO_PRA_VOTO = "ABLE_TO_VOTE";
    private final String ASSOCIADO_NAO_ATIVO = "Associado não ativo/encontrado com o CPF";
    private final String SESSAO_NAO_ATIVA = "Sessão não está ativa para a pauta: ";
    private final String VOTO_JA_COMPUTADO = "Voto já computado para este CPF";
    private final String VOTO_NAO_ENCONTRADO = "Voto não encontrado com id: ";
    private final String SESSAO_ATIVA = "Não é possível obter o resultado da votação, pois a sessão está ativa";

    @Override
    public VotoResponse registrarVoto(VotoRequest voto) {
        if (!CpfValidator.isValid(voto.getCpf())) {
            throw new CpfInvalidoException(CPF_INVALIDO + ": " + voto.getCpf());
        };

        validarAssociado(voto.getCpf());

        var sessaoAtiva = sessaoRepository.existsSessaoAtivaByPauta(voto.getPautaId(), LocalDateTime.now());
        if (sessaoAtiva == null || sessaoAtiva == 0L) {
            throw new BusinessException(SESSAO_NAO_ATIVA + ": " + voto.getPautaId());
        }

        var votoRealizado = votoRepository.votoRealizado(CpfValidator.cpfSoNumeros(voto.getCpf()), voto.getPautaId());

        if (votoRealizado) {
            throw new BusinessException(VOTO_JA_COMPUTADO);
        }

        var sessao = sessaoRepository.findById(sessaoAtiva);

        var votoEntity = votoMapper.toEntity(voto, sessao.get());

        votoEntity.setCpf(CpfValidator.cpfSoNumeros(voto.getCpf()));
        votoRepository.save(votoEntity);

        votoEntity.setCpf(CpfValidator.formatCpf(voto.getCpf()));

        return votoMapper.toResponse(votoEntity);
    }

    private void validarAssociado(String cpf) {
        String cpfLimpo = CpfValidator.cpfSoNumeros(cpf);
        if (usarAssociadoRemoto) {
            var associadoResponse = associadoClient.buscarUsuarioPorCpf(cpfLimpo);
            if (associadoResponse == null || !HABILITADO_PRA_VOTO.equals(associadoResponse.toString())) {
                throw new BusinessException(ASSOCIADO_NAO_ATIVO + ": " + cpf);
            }
        } else {
            var associadoAtivo = associadosRepository.findByAssociadoAtivo(cpfLimpo);
            if (associadoAtivo == null || !associadoAtivo.booleanValue()) {
                throw new BusinessException(ASSOCIADO_NAO_ATIVO + ": " + cpf);
            }
        }
    }

    @Override
    public Voto buscarPorId(Long id) {
        return votoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(VOTO_NAO_ENCONTRADO));
    }

    @Override
    public List<Voto> listarTodos() {
        return votoRepository.findAll();
    }

    @Override
    public void deletar(Long id) {
        votoRepository.deleteById(id);
    }

    @Override
    public PautaResultadoResponse obterResultadoVotacao(Long pautaId) {
        var sessaoativa = sessaoRepository.existsSessaoAtivaByPauta(pautaId, LocalDateTime.now()); // Verifica se a sessão está ativa para a pauta
        if (sessaoativa != null && sessaoativa > 0L) {
            throw new BusinessException(SESSAO_ATIVA);
        }
        Pauta pauta = pautaService.findById(pautaId);
        var votosRealizados = votoRepository.findByPautaId(pautaId);

        long votosSim = votosRealizados.stream()
                .filter(e -> e.getVoto() == VotoEnum.SIM)
                .count();

        long votosNao = votosRealizados.stream()
                .filter(e -> e.getVoto() == VotoEnum.NAO)
                .count();
        return PautaResultadoResponse.builder()
                .id(pauta.getId())
                .titulo(pauta.getTitulo())
                .descricao(pauta.getDescricao())
                .totalVotosSim(votosSim)
                .totalVotosNao(votosNao)
                .build();
    }
}