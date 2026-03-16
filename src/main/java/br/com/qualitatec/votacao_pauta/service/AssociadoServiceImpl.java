package br.com.qualitatec.votacao_pauta.service;

import br.com.qualitatec.votacao_pauta.mapper.AssociadoMapper;
import br.com.qualitatec.votacao_pauta.model.AssociadoResponse;
import br.com.qualitatec.votacao_pauta.repository.AssociadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssociadoServiceImpl implements AssociadoService {

    private final AssociadoRepository associadoRepository;
    private final AssociadoMapper associadoMapper;

    @Override
    public List<AssociadoResponse> findAll() {
        return associadoMapper.toResponseDTO(associadoRepository.findAll());
    }


}
