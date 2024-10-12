package org.josafamelo.controller;

import org.josafamelo.controller.response.ResponseCep;
import org.josafamelo.domain.CEP;
import org.josafamelo.gateway.apis.CEPClient;
import org.josafamelo.usecase.OrquestraConsultaCEPUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CEPController.class)
@EnableFeignClients
class CEPControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrquestraConsultaCEPUseCase useCase;

    @MockBean
    private CEPClient cepClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void consultarCep_DeveRetornarResponseCepComSucesso() throws Exception {
        // Arrange
        String cep = "12345-678";
        CEP cepDomain = new CEP(cep, "Rua Exemplo", "Bairro", "Cidade Exemplo", "Estado Exemplo");
        ResponseCep responseCep = new ResponseCep(cep, "Rua Exemplo", "Bairro","Cidade Exemplo", "Estado Exemplo");

        when(useCase.orquestraConsultaCep(cep)).thenReturn(cepDomain);

        // Act & Assert
        mockMvc.perform(get("/cep/{cep}", cep)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cep").value(cep))
                .andExpect(jsonPath("$.logradouro").value("Rua Exemplo"))
                .andExpect(jsonPath("$.bairro").value("Bairro"))
                .andExpect(jsonPath("$.cidade").value("Cidade Exemplo"))
                .andExpect(jsonPath("$.estado").value("Estado Exemplo"));

        verify(useCase, times(1)).orquestraConsultaCep(cep);
    }

    @Test
    void consultarCep_DeveRetornarStatus500() throws Exception {
        // Arrange
        String cep = "99999-999";
        when(useCase.orquestraConsultaCep(cep)).thenThrow(new RuntimeException("Erro na comuncação com a API"));

        // Act & Assert
        mockMvc.perform(get("/cep/{cep}", cep)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(useCase, times(1)).orquestraConsultaCep(cep);
    }
}
