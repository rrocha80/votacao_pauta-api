package br.com.qualitatec.votacao_pauta.controller;

import br.com.qualitatec.votacao_pauta.domain.Voto;
import br.com.qualitatec.votacao_pauta.mapper.SessaoMapper;
import br.com.qualitatec.votacao_pauta.model.SessaoRequest;
import br.com.qualitatec.votacao_pauta.model.SessaoResponse;
import br.com.qualitatec.votacao_pauta.service.SessaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/sessoes")
@RequiredArgsConstructor
public class SessaoController {

    private final SessaoService service;
    private final SessaoMapper mapper;

    @PostMapping("/criar")
    public SessaoResponse criar(@Valid @RequestBody SessaoRequest request) {
        return service.criarSessao(request);
    }

    @GetMapping("/buscar-todos")
    public List<SessaoResponse> listar() {
        return service.listarTodas();
    }

    @GetMapping("/{id}/buscar")
    public SessaoResponse buscar(@PathVariable Long id) {
        service.buscarPorId(id);
        return service.buscarPorId(id);
    }

    @GetMapping("/buscar-sessoes-ativas")
    public List<SessaoResponse> listarSessoesAtivas() {
        return service.listarSessoesAtivas();
    }

    @DeleteMapping("/{id}/deletar")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }

}