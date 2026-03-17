package br.com.qualitatec.votacao_pauta.service;

import br.com.qualitatec.votacao_pauta.exception.BusinessException;
import br.com.qualitatec.votacao_pauta.domain.Pauta;
import br.com.qualitatec.votacao_pauta.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PautaServiceImpl implements PautaService {

    private final PautaRepository pautaRepository;

    private final String TITULO_EXISTENTE = "Pauta com título já existe";

    private final String PAUTA_NAO_ENCONTRADA = "Pauta não encontrada";

    @Override
    public Pauta save(Pauta pauta) {
        Optional<Pauta> existe = pautaRepository.findByTituloExistente(pauta.getTitulo());

        if (existe.isPresent()) {
            throw new BusinessException(TITULO_EXISTENTE);
        }
        return pautaRepository.save(pauta);
    }

    @Override
    public Pauta findById(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(PAUTA_NAO_ENCONTRADA));
    }

    @Override
    public List<Pauta> findAll() {
        return pautaRepository.findAll();
    }

    @Override
    public Pauta update(Long id, Pauta pauta) {
        Pauta existente = findById(id);
        existente.setTitulo(pauta.getTitulo());
        existente.setDescricao(pauta.getDescricao());
        // Atualize outros campos conforme necessário
        return pautaRepository.save(existente);
    }

    @Override
    public void delete(Long id) {
        pautaRepository.deleteById(id);
    }

}