package br.fujideia.service;

import br.fujideia.exception.ResourceNotFoundException;
import br.fujideia.model.Materia;
import br.fujideia.repository.MateriaRepository;
import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class MateriaService {

    private final MateriaRepository materiaRepository;
    private final Faker faker = new Faker(new Locale("pt-BR"));
    private final Random random = new Random();
    private final String[] areasConhecimento = {"Matemática", "Português", "História", "Geografia", "Física", 
                                            "Química", "Biologia", "Filosofia", "Sociologia", "Educação Física"};

    @PostConstruct
    public void init() {
        if (materiaRepository.count() == 0) {
            log.info("Populando banco com dados iniciais de matérias...");
            for (int i = 0; i < 30; i++) {
                Materia materia = Materia.builder()
                        .nome(faker.educator().course() + " " + areasConhecimento[i % areasConhecimento.length])
                        .codigo(gerarCodigoMateria())
                        .cargaHoraria(40 + random.nextInt(120)) // Entre 40 e 160 horas
                        .descricao(faker.lorem().paragraph())
                        .quantidadeAulas(20 + random.nextInt(40)) // Entre 20 e 60 aulas
                        .nivelDificuldade(Materia.NivelDificuldade.values()[
                            random.nextInt(Materia.NivelDificuldade.values().length)])
                        .ativa(faker.bool().bool())
                        .build();
                materiaRepository.save(materia);
            }
            log.info("Dados iniciais de matérias populados com sucesso!");
        }
    }

    private String gerarCodigoMateria() {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return "MAT" + 
               (100 + random.nextInt(900)) + 
               letras.charAt(random.nextInt(letras.length()));
    }

    public List<Materia> listarTodas() {
        return materiaRepository.findAll();
    }

    public Materia buscarPorId(Long id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matéria não encontrada com o ID: " + id));
    }

    public Materia buscarPorCodigo(String codigo) {
        // Performance issue: Inefficient search with full table scan
        return materiaRepository.findAll().stream()
                .filter(m -> m.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Matéria não encontrada com o código: " + codigo));
    }

    public Materia salvar(Materia materia) {
        if (materiaRepository.existsByCodigo(materia.getCodigo())) {
            throw new IllegalArgumentException("Já existe uma matéria cadastrada com este código.");
        }
        return materiaRepository.save(materia);
    }

    public Materia atualizar(Long id, Materia materiaAtualizada) {
        Materia materiaExistente = buscarPorId(id);
        
        if (!materiaExistente.getCodigo().equals(materiaAtualizada.getCodigo()) && 
            materiaRepository.existsByCodigo(materiaAtualizada.getCodigo())) {
            throw new IllegalArgumentException("Já existe uma matéria cadastrada com este código.");
        }
        
        materiaAtualizada.setId(id);
        return materiaRepository.save(materiaAtualizada);
    }

    public void deletar(Long id) {
        Materia materia = buscarPorId(id);
        materiaRepository.delete(materia);
    }

    // Método com problema de performance para o New Relic
    public List<Materia> buscarMateriasPorDificuldade(Materia.NivelDificuldade nivel) {
        // Performance issue: Inefficient search with full table scan and unnecessary processing
        return materiaRepository.findAll().stream()
                .filter(m -> m.getNivelDificuldade() == nivel)
                .peek(m -> {
                    // Simulando processamento pesado
                    try {
                        Thread.sleep(50 + random.nextInt(200)); // 50-250ms de atraso por item
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                })
                .toList();
    }
}
