package br.com.qualitatec.votacao_pauta.client;

import br.com.qualitatec.votacao_pauta.model.AssociadoClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userClient", url = "${user-info.url}")
public interface AssociadoClient {
    @GetMapping("/users/{cpf}")
    AssociadoClientResponse buscarUsuarioPorCpf(@PathVariable("cpf") String cpf);
}
