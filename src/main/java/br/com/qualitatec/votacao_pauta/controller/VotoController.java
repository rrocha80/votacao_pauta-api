package br.com.qualitatec.votacao_pauta.controller;

import br.com.qualitatec.votacao_pauta.domain.Voto;
import br.com.qualitatec.votacao_pauta.model.VotoRequest;
import br.com.qualitatec.votacao_pauta.model.VotoResponse;
import br.com.qualitatec.votacao_pauta.service.SessaoService;
import br.com.qualitatec.votacao_pauta.service.VotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/votos")
@RequiredArgsConstructor
public class VotoController {

    private final VotoService votoService;

    @PostMapping("/registrar-voto")
    public VotoResponse registrarVoto(@Valid @RequestBody VotoRequest request) {
        return votoService.registrarVoto(request);
    }

    @GetMapping("/{id}/buscar")
    public Voto buscarPorId(@PathVariable Long id) {
        return votoService.buscarPorId(id);
    }

    @GetMapping("/buscar-todos")
    public List<Voto> listarTodos() {
        return votoService.listarTodos();
    }

    @DeleteMapping("/{id}/deletar")
    public void deletar(@PathVariable Long id) {
        votoService.deletar(id);
    }
}