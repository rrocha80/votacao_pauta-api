package br.com.qualitatec.votacao_pauta.service;

import br.com.qualitatec.votacao_pauta.domain.Voto;
import br.com.qualitatec.votacao_pauta.model.VotoRequest;
import br.com.qualitatec.votacao_pauta.model.VotoResponse;

import java.util.List;

public interface VotoService {
    VotoResponse registrarVoto(VotoRequest voto);
    Voto buscarPorId(Long id);
    List<Voto> listarTodos();
    void deletar(Long id);

}