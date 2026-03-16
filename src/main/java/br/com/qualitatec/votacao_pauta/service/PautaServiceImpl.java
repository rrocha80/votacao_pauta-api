package br.com.qualitatec.votacao_pauta.service;

import br.com.qualitatec.votacao_pauta.config.exception.BusinessException;
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

    @Override
    public Pauta save(Pauta pauta) {
        Optional<Pauta> existe = pautaRepository.findByTituloIgnoreCase(pauta.getTitulo());

        if (existe.isPresent()) {
            throw new BusinessException("Pauta com título já existe");
        }
        return pautaRepository.save(pauta);
    }

    @Override
    public Pauta findById(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada"));
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