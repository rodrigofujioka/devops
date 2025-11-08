package br.fujideia.controller;

import br.fujideia.model.Materia;
import br.fujideia.service.MateriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materias")
@RequiredArgsConstructor
public class MateriaController {

    private final MateriaService materiaService;

    @GetMapping
    public ResponseEntity<List<Materia>> listarTodas() {
        return ResponseEntity.ok(materiaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Materia> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(materiaService.buscarPorId(id));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Materia> buscarPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(materiaService.buscarPorCodigo(codigo));
    }

    @GetMapping("/dificuldade/{nivel}")
    public ResponseEntity<List<Materia>> buscarPorDificuldade(
            @PathVariable("nivel") Materia.NivelDificuldade nivel) {
        return ResponseEntity.ok(materiaService.buscarMateriasPorDificuldade(nivel));
    }

    @PostMapping
    public ResponseEntity<Materia> criar(@Valid @RequestBody Materia materia) {
        return new ResponseEntity<>(materiaService.salvar(materia), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Materia> atualizar(
            @PathVariable Long id, 
            @Valid @RequestBody Materia materia) {
        return ResponseEntity.ok(materiaService.atualizar(id, materia));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        materiaService.deletar(id);
    }
}
