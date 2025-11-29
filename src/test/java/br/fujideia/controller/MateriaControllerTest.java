package br.fujideia.controller;

import br.fujideia.model.Materia;
import br.fujideia.service.MateriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MateriaControllerTest {

    @Mock
    private MateriaService materiaService;

    @InjectMocks
    private MateriaController materiaController;

    private Materia materia;

    @BeforeEach
    void setUp() {
        materia = new Materia();
        materia.setId(1L);
        materia.setNome("Matemática Avançada");
        materia.setCodigo("MAT101");
        materia.setCargaHoraria(60);
    }

    @Test
    void listarTodas_DeveRetornarListaDeMaterias() {
        // Arrange
        when(materiaService.listarTodas()).thenReturn(Arrays.asList(materia));

        // Act
        ResponseEntity<List<Materia>> response = materiaController.listarTodas();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals("Matemática Avançada", response.getBody().get(0).getNome());
    }

    @Test
    void buscarPorId_DeveRetornarMateria_QuandoIdExistir() {
        // Arrange
        when(materiaService.buscarPorId(1L)).thenReturn(materia);

        // Act
        ResponseEntity<Materia> response = materiaController.buscarPorId(1L);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Matemática Avançada", response.getBody().getNome());
    }

    @Test
    void buscarPorCodigo_DeveRetornarMateria_QuandoCodigoExistir() {
        // Arrange
        when(materiaService.buscarPorCodigo("MAT101")).thenReturn(materia);

        // Act
        ResponseEntity<Materia> response = materiaController.buscarPorCodigo("MAT101");

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("MAT101", response.getBody().getCodigo());
    }

    @Test
    void buscarPorDificuldade_DeveRetornarListaDeMaterias() {
        // Arrange
        materia.setNivelDificuldade(Materia.NivelDificuldade.MEDIO);
        when(materiaService.buscarMateriasPorDificuldade(any())).thenReturn(Arrays.asList(materia));

        // Act
        ResponseEntity<List<Materia>> response = materiaController.buscarPorDificuldade(Materia.NivelDificuldade.MEDIO);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(Materia.NivelDificuldade.MEDIO, response.getBody().get(0).getNivelDificuldade());
    }

    @Test
    void criar_DeveRetornarMateriaCriada() {
        // Arrange
        when(materiaService.salvar(any(Materia.class))).thenReturn(materia);

        // Act
        ResponseEntity<Materia> response = materiaController.criar(materia);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Matemática Avançada", response.getBody().getNome());
    }

    @Test
    void atualizar_DeveRetornarMateriaAtualizada() {
        // Arrange
        when(materiaService.atualizar(eq(1L), any(Materia.class))).thenReturn(materia);

        // Act
        ResponseEntity<Materia> response = materiaController.atualizar(1L, materia);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Matemática Avançada", response.getBody().getNome());
    }

    @Test
    void deletar_DeveChamarMetodoDeletarDoService() {
        // Arrange
        doNothing().when(materiaService).deletar(1L);

        // Act
        materiaController.deletar(1L);

        // Assert
        verify(materiaService, times(1)).deletar(1L);
    }
}
