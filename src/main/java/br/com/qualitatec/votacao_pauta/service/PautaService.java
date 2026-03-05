package br.com.qualitatec.votacao_pauta.service;

import br.com.qualitatec.votacao_pauta.domain.Pauta;

import java.util.List;

public interface PautaService {
    Pauta save(Pauta pauta);
    Pauta findById(Long id);
    List<Pauta> findAll();
    Pauta update(Long id, Pauta pauta);
    void delete(Long id);
}