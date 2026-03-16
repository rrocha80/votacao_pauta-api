package br.com.qualitatec.votacao_pauta.config;

import br.com.qualitatec.votacao_pauta.domain.Associado;
import br.com.qualitatec.votacao_pauta.repository.AssociadoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final AssociadoRepository repository;

    public DataLoader(AssociadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        // verifica se já existe para não duplicar
        if (repository.count() == 0) {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Associado>> typeReference = new TypeReference<>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/data/associados.json");

            List<Associado> associados = mapper.readValue(inputStream, typeReference);
            repository.saveAll(associados);

            System.out.println("Associados carregados: " + associados.size());
        }
    }
}