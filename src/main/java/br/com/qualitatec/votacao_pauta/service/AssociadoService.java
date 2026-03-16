package br.com.qualitatec.votacao_pauta.service;

import br.com.qualitatec.votacao_pauta.model.AssociadoResponse;

import java.util.List;

public interface AssociadoService {
    List<AssociadoResponse> findAll();
}
