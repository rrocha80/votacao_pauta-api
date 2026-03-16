package br.com.qualitatec.votacao_pauta.controller;

import br.com.qualitatec.votacao_pauta.model.AssociadoResponse;
import br.com.qualitatec.votacao_pauta.service.AssociadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/associados")
public class AssociadoController {

    private final AssociadoService associadoService;

    @GetMapping
    public ResponseEntity<List<AssociadoResponse>> list() {
        return ResponseEntity.ok(associadoService.findAll());
    }

}