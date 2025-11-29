package br.fujideia.controller;

import br.fujideia.model.Filme;
import br.fujideia.service.FilmeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmeControllerTest {

    @Mock
    private FilmeService filmeService;

    @InjectMocks
    private FilmeController filmeController;

    private Filme filme;

    @BeforeEach
    void setUp() {
        // Configura o contexto da requisição para o teste de criação
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        filme = Filme.builder()
                .id(1L)
                .titulo("O Poderoso Chefão")
                .genero("Drama")
                .dataLancamento(LocalDate.of(1972, 3, 24))
                .duracaoMinutos(175)
                .classificacaoIndicativa("16")
                .sinopse("A história da família mafiosa Corleone...")
                .build();
    }

    @Test
    void listar_DeveRetornarListaDeFilmes() {
        // Arrange
        when(filmeService.listar()).thenReturn(Arrays.asList(filme));

        // Act
        List<Filme> resultado = filmeController.listar();

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("O Poderoso Chefão", resultado.get(0).getTitulo());
    }

    @Test
    void buscarPorId_DeveRetornarFilme_QuandoIdExistir() {
        // Arrange
        when(filmeService.buscarPorId(1L)).thenReturn(filme);

        // Act
        ResponseEntity<Filme> response = filmeController.buscarPorId(1L);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("O Poderoso Chefão", response.getBody().getTitulo());
    }

    @Test
    void criar_DeveRetornarFilmeCriado() {
        // Arrange
        when(filmeService.salvar(any(Filme.class))).thenReturn(filme);

        // Act
        ResponseEntity<Filme> response = filmeController.criar(filme);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertNotNull(response.getHeaders().getLocation());
        assertTrue(Objects.requireNonNull(response.getHeaders().getLocation()).getPath().contains("/1"));
        assertEquals("O Poderoso Chefão", Objects.requireNonNull(response.getBody()).getTitulo());
    }

    @Test
    void atualizar_DeveRetornarFilmeAtualizado() {
        // Arrange
        when(filmeService.atualizar(eq(1L), any(Filme.class))).thenReturn(filme);

        // Act
        ResponseEntity<Filme> response = filmeController.atualizar(1L, filme);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("O Poderoso Chefão", response.getBody().getTitulo());
    }

    @Test
    void deletar_DeveRetornarNoContent() {
        // Arrange
        doNothing().when(filmeService).excluir(1L);

        // Act
        ResponseEntity<Void> response = filmeController.deletar(1L);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(filmeService, times(1)).excluir(1L);
    }

    @Test
    void buscarPorTitulo_DeveRetornarFilmesComTitulo() {
        // Arrange
        when(filmeService.buscarPorTitulo("Chefão")).thenReturn(Arrays.asList(filme));

        // Act
        ResponseEntity<List<Filme>> response = filmeController.buscarPorTitulo("Chefão");

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals("O Poderoso Chefão", response.getBody().get(0).getTitulo());
    }
}
