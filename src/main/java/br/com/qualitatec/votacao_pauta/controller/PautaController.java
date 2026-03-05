package br.com.qualitatec.votacao_pauta.controller;

import br.com.qualitatec.votacao_pauta.domain.Pauta;
import br.com.qualitatec.votacao_pauta.mapper.PautaMapper;
import br.com.qualitatec.votacao_pauta.model.PautaRequest;
import br.com.qualitatec.votacao_pauta.model.PautaResponse;
import br.com.qualitatec.votacao_pauta.service.PautaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/pauta")
public class PautaController {
    private final PautaService pautaService;
    private final PautaMapper mapper;

    public PautaController(PautaService pautaService, PautaMapper mapper) {
        this.pautaService = pautaService;
        this.mapper = mapper;
    }

    @PostMapping("/salvar")
    public PautaResponse save(@Valid @RequestBody PautaRequest request) {
        Pauta pauta = mapper.toEntity(request);
        Pauta salva = pautaService.save(pauta);
        return mapper.toResponseDTO(salva);
    }

    @GetMapping("/{id}")
    public PautaResponse getById(@PathVariable Long id) {
        Pauta pauta = pautaService.findById(id);
        return mapper.toResponseDTO(pauta);
    }

    @GetMapping("/buscar-todos")
    public List<PautaResponse> getAll() {
        return pautaService.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}/atualizar")
    public PautaResponse update(@PathVariable Long id, @Valid @RequestBody PautaRequest request) {
        Pauta pauta = mapper.toEntity(request);
        Pauta atualizada = pautaService.update(id, pauta);
        return mapper.toResponseDTO(atualizada);
    }

    @DeleteMapping("/{id}/deletar")
    public void delete(@PathVariable Long id) {
        pautaService.delete(id);
    }
}