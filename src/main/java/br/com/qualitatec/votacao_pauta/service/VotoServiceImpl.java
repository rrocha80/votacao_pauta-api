package br.com.qualitatec.votacao_pauta.service;

import br.com.qualitatec.votacao_pauta.client.AssociadoClient;
import br.com.qualitatec.votacao_pauta.config.exception.BusinessException;
import br.com.qualitatec.votacao_pauta.config.exception.CpfInvalidoException;
import br.com.qualitatec.votacao_pauta.domain.Voto;
import br.com.qualitatec.votacao_pauta.mapper.VotoMapper;
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

    @Override
    public VotoResponse registrarVoto(VotoRequest voto) {
        if (!CpfValidator.isValid(voto.getCpf())) {
            throw new CpfInvalidoException(CPF_INVALIDO + ": " + voto.getCpf());
        };

        validarAssociado(voto.getCpf());

        var sessaoAtiva = sessaoRepository.existsSessaoAtiva(voto.getPautaId(), LocalDateTime.now());
        if (sessaoAtiva == null || sessaoAtiva == 0L) {
            throw new BusinessException(SESSAO_NAO_ATIVA + ": " + voto.getPautaId());
        }

        var votoRealizado = votoRepository.votoRealizado(voto.getCpf(), voto.getPautaId());

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
        if (usarAssociadoRemoto) {
            String cpfLimpo = CpfValidator.cpfSoNumeros(cpf);
            var associadoResponse = associadoClient.buscarUsuarioPorCpf(cpfLimpo);
            if (associadoResponse == null || !HABILITADO_PRA_VOTO.equals(associadoResponse.toString())) {
                throw new BusinessException(ASSOCIADO_NAO_ATIVO + ": " + cpf);
            }
        } else {
            var associadoAtivo = associadosRepository.findByAssociadoAtivo(cpf);
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
}