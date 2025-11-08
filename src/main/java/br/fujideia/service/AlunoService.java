package br.fujideia.service;

import br.fujideia.exception.ResourceNotFoundException;
import br.fujideia.model.Aluno;
import br.fujideia.repository.AlunoRepository;
import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final Faker faker = new Faker(new Locale("pt-BR"));
    private final Random random = new Random();

    @PostConstruct
    public void init() {
        if (alunoRepository.count() == 0) {
            log.info("Populando banco com dados iniciais de alunos...");
            for (int i = 0; i < 100; i++) {
                Aluno aluno = Aluno.builder()
                        .nome(faker.name().fullName())
                        .email(faker.internet().emailAddress())
                        .dataNascimento(Aluno.toLocalDate(faker.date().birthday(18, 60)))
                        .matricula("A" + String.format("%06d", i + 1))
                        .curso(faker.educator().course())
                        .ativo(faker.bool().bool())
                        .build();
                alunoRepository.save(aluno);
            }
            log.info("Dados iniciais de alunos populados com sucesso!");
        }
    }

    public List<Aluno> listarTodos() {
        // Performance issue: Simulating a slow query
        try {
            Thread.sleep(random.nextInt(500) + 100); // Random delay between 100-600ms
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com o ID: " + id));
    }

    public Aluno buscarPorMatricula(String matricula) {
        return alunoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com a matrícula: " + matricula));
    }

    public Aluno salvar(Aluno aluno) {
        if (alunoRepository.existsByEmail(aluno.getEmail())) {
            throw new IllegalArgumentException("Já existe um aluno cadastrado com este e-mail.");
        }
        if (aluno.getMatricula() != null && alunoRepository.existsByMatricula(aluno.getMatricula())) {
            throw new IllegalArgumentException("Já existe um aluno cadastrado com esta matrícula.");
        }
        return alunoRepository.save(aluno);
    }

    public Aluno atualizar(Long id, Aluno alunoAtualizado) {
        Aluno alunoExistente = buscarPorId(id);
        
        if (!alunoExistente.getEmail().equals(alunoAtualizado.getEmail()) && 
            alunoRepository.existsByEmail(alunoAtualizado.getEmail())) {
            throw new IllegalArgumentException("Já existe um aluno cadastrado com este e-mail.");
        }
        
        alunoAtualizado.setId(id);
        return alunoRepository.save(alunoAtualizado);
    }

    public void deletar(Long id) {
        Aluno aluno = buscarPorId(id);
        alunoRepository.delete(aluno);
    }

    // Método com problema de performance para o New Relic
    public List<Aluno> buscarAlunosPorCurso(String curso) {
        // Performance issue: Inefficient search with full table scan
        return alunoRepository.findAll().stream()
                .filter(aluno -> aluno.getCurso() != null && 
                               aluno.getCurso().toLowerCase().contains(curso.toLowerCase()))
                .toList();
    }
}
