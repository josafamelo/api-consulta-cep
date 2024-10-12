package org.josafamelo.usecase;

import org.josafamelo.domain.CEP;
import org.josafamelo.gateway.apis.APICEPGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsultaCEPUseCaseTest {

    @Mock
    private APICEPGateway gateway;

    @InjectMocks
    private ConsultaCEPUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void consultaCEP_DeveRetornarCEPQuandoSucesso() {
        // Arrange
        String cepInput = "12345-678";
        CEP cepEsperado = new CEP(cepInput, "Rua Exemplo", "Bairro Exemplo", "Cidade Exemplo", "Estado Exemplo");

        when(gateway.consultarCep(cepInput)).thenReturn(cepEsperado);

        // Act
        CEP resultado = useCase.consultaCEP(cepInput);

        // Assert
        assertNotNull(resultado);
        assertEquals(cepEsperado, resultado);
        verify(gateway, times(1)).consultarCep(cepInput);
    }

    @Test
    void consultaCEP_DeveLancarExcecaoQuandoGatewayFalhar() {
        // Arrange
        String cepInput = "12345-678";
        when(gateway.consultarCep(cepInput)).thenThrow(new RuntimeException("Erro na consulta"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            useCase.consultaCEP(cepInput);
        });

        assertEquals("Erro na consulta", exception.getMessage());
        verify(gateway, times(1)).consultarCep(cepInput);
    }
}