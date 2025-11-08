package br.fujideia.config;

import br.fujideia.model.Aluno;
import br.fujideia.model.Materia;
import br.fujideia.repository.AlunoRepository;
import br.fujideia.repository.MateriaRepository;
import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.IntStream;

@Slf4j
@Configuration
@RequiredArgsConstructor
@Profile("!test") // Não executa em ambiente de teste
public class DataInitializer {

    private final AlunoRepository alunoRepository;
    private final MateriaRepository materiaRepository;
    private final Faker faker = new Faker(new Locale("pt-BR"));
    private final Random random = new Random();

    @PostConstruct
    public void init() {
        if (alunoRepository.count() == 0) {
            popularAlunos();
        }
        if (materiaRepository.count() == 0) {
            popularMaterias();
        }
    }

    private void popularAlunos() {
        log.info("Iniciando carga de dados de alunos...");
        
        List<String> cursos = Arrays.asList(
            "Engenharia de Software", "Ciência da Computação", "Sistemas de Informação",
            "Engenharia da Computação", "Análise e Desenvolvimento de Sistemas"
        );

        IntStream.range(0, 100).forEach(i -> {
            Aluno aluno = Aluno.builder()
                    .nome(faker.name().fullName())
                    .email(faker.internet().emailAddress())
                    .dataNascimento(faker.date().birthday(18, 30).toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDate())
                    .matricula(String.format("A%06d", i + 1))
                    .curso(cursos.get(random.nextInt(cursos.size())))
                    .ativo(faker.bool().bool())
                    .build();
            alunoRepository.save(aluno);
        });
        
        log.info("Carga de {} alunos concluída com sucesso!", alunoRepository.count());
    }

    private void popularMaterias() {
        log.info("Iniciando carga de dados de matérias...");
        
        List<String> areas = Arrays.asList(
            "Matemática", "Português", "História", "Geografia", "Física",
            "Química", "Biologia", "Filosofia", "Sociologia", "Educação Física"
        );
        
        List<String> prefixos = Arrays.asList(
            "Introdução a ", "Avançado em ", "Fundamentos de ", "Tópicos Especiais em ",
            "Laboratório de ", "Seminários em ", "Metodologia de ", "Prática em "
        );

        IntStream.range(0, 30).forEach(i -> {
            String area = areas.get(i % areas.size());
            String prefixo = prefixos.get(random.nextInt(prefixos.size()));
            
            Materia materia = Materia.builder()
                    .nome(prefixo + area)
                    .codigo(String.format("MAT%03d%c", i + 1, (char)('A' + random.nextInt(26))))
                    .cargaHoraria(40 + random.nextInt(121)) // 40 a 160 horas
                    .descricao(faker.lorem().paragraph())
                    .quantidadeAulas(20 + random.nextInt(41)) // 20 a 60 aulas
                    .nivelDificuldade(Materia.NivelDificuldade.values()[
                        random.nextInt(Materia.NivelDificuldade.values().length)])
                    .ativa(faker.bool().bool())
                    .build();
            materiaRepository.save(materia);
        });
        
        log.info("Carga de {} matérias concluída com sucesso!", materiaRepository.count());
    }
}
