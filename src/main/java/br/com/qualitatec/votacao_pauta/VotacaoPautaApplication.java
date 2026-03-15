package br.com.qualitatec.votacao_pauta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "br.com.qualitatec.votacao_pauta.client")
public class VotacaoPautaApplication {
	public static void main(String[] args) {
		SpringApplication.run(VotacaoPautaApplication.class, args);
	}

}
