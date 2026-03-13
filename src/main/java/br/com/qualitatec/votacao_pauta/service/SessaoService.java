package br.com.qualitatec.votacao_pauta.service;

import br.com.qualitatec.votacao_pauta.model.SessaoRequest;
import br.com.qualitatec.votacao_pauta.model.SessaoResponse;

import java.util.List;

public interface SessaoService {

    SessaoResponse criarSessao(SessaoRequest request);

    List<SessaoResponse> listarTodas();

    SessaoResponse buscarPorId(Long id);

    void deletar(Long id);

    List<SessaoResponse> listarSessoesAtivas();
}