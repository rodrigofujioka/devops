package br.fujideia.repository;

import br.fujideia.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
    Optional<Materia> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    List<Materia> findByNivelDificuldade(Materia.NivelDificuldade nivel);
}
