package org.josafamelo.gateway.apis;

import org.josafamelo.domain.CEP;
import org.josafamelo.gateway.mapper.CEPGatewayMapper;
import org.josafamelo.gateway.apis.responses.ResponseCEP;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class APICEPGatewayTest {

    @Mock
    private CEPClient cepClient;

    private final CEPGatewayMapper mapper = Mappers.getMapper(CEPGatewayMapper.class);

    @InjectMocks
    private APICEPGateway apiGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void consultarCep_DeveRetornarCEPQuandoSucesso() {
        // Arrange
        String cepInput = "12345-678";
        ResponseCEP responseDTO = new ResponseCEP(cepInput, "Rua Exemplo", "Bairro","Cidade Exemplo", "Estado Exemplo");
        CEP cepEsperado = mapper.toDomain(responseDTO);

        when(cepClient.consultaCep(cepInput)).thenReturn(responseDTO);

        // Act
        CEP resultado = apiGateway.consultarCep(cepInput);

        // Assert
        assertNotNull(resultado);
        assertEquals(cepEsperado, resultado);
        verify(cepClient, times(1)).consultaCep(cepInput);
    }

    @Test
    void consultarCep_DeveLancarExcecaoQuandoClienteFalhar() {
        // Arrange
        String cepInput = "12345-678";
        when(cepClient.consultaCep(cepInput)).thenThrow(new RuntimeException("Erro no serviço externo"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            apiGateway.consultarCep(cepInput);
        });

        assertEquals("Erro no serviço externo", exception.getMessage());
        verify(cepClient, times(1)).consultaCep(cepInput);
    }
}
