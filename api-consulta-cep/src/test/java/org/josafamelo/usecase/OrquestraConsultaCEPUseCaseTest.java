package org.josafamelo.usecase;

import org.josafamelo.domain.CEP;
import org.josafamelo.gateway.apis.APICEPGateway;
import org.josafamelo.gateway.mapper.CEPRepositoryMapper;
import org.josafamelo.gateway.repository.CepLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrquestraConsultaCEPUseCaseTest {

    @Mock
    private APICEPGateway gateway;

    @Mock
    private CepLogRepository repository;

    private final CEPRepositoryMapper mapper = Mappers.getMapper(CEPRepositoryMapper.class);

    @InjectMocks
    private OrquestraConsultaCEPUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void orquestraConsultaCep_DeveRetornarCEPESalvarNoRepository() {
        // Arrange
        String cepInput = "12345-678";
        CEP cepEsperado = new CEP(cepInput, "Rua Exemplo", "Bairro", "Cidade Exemplo", "Estado Exemplo");

        when(gateway.consultarCep(cepInput)).thenReturn(cepEsperado);

        // Act
        CEP resultado = useCase.orquestraConsultaCep(cepInput);

        // Assert
        assertNotNull(resultado);
        assertEquals(cepEsperado, resultado);
        verify(gateway, times(1)).consultarCep(cepInput);
        verify(repository, times(1)).save(mapper.toEntity(cepEsperado));
    }

    @Test
    void orquestraConsultaCep_DeveLancarExcecaoQuandoGatewayFalhar() {
        // Arrange
        String cepInput = "12345-678";
        when(gateway.consultarCep(cepInput)).thenThrow(new RuntimeException("Erro na consulta"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            useCase.orquestraConsultaCep(cepInput);
        });

        assertEquals("Erro na consulta", exception.getMessage());
        verify(gateway, times(1)).consultarCep(cepInput);
        verify(repository, never()).save(any());
    }
}
