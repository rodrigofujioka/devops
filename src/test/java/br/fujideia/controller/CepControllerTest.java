package br.fujideia.controller;

import br.fujideia.dto.ViaCepResponse;
import br.fujideia.service.ViaCepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CepControllerTest {

    @Mock
    private ViaCepService viaCepService;

    @InjectMocks
    private CepController cepController;

    private ViaCepResponse viaCepResponse;

    @BeforeEach
    void setUp() {
        viaCepResponse = new ViaCepResponse();
        viaCepResponse.setCep("01001-000");
        viaCepResponse.setLogradouro("Praça da Sé");
        viaCepResponse.setLocalidade("São Paulo");
        viaCepResponse.setUf("SP");
    }

    @Test
    void consultarCep_DeveRetornarEndereco_QuandoCepValido() {
        // Arrange
        String cep = "01001000";
        when(viaCepService.buscarEnderecoPorCep(anyString())).thenReturn(viaCepResponse);

        // Act
        ResponseEntity<ViaCepResponse> response = cepController.consultarCep(cep);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("01001-000", response.getBody().getCep());
        assertEquals("Praça da Sé", response.getBody().getLogradouro());
        assertEquals("São Paulo", response.getBody().getLocalidade());
        assertEquals("SP", response.getBody().getUf());
    }

    @Test
    void testMudancaCep_DeveRetornarStringTeste() {
        // Act
        String result = cepController.mudancaCep();

        // Assert
        assertEquals("Teste", result);
    }
}
