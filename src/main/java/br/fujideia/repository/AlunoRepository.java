package br.fujideia.repository;

import br.fujideia.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    boolean existsByEmail(String email);
    boolean existsByMatricula(String matricula);
    Optional<Aluno> findByMatricula(String matricula);
}
