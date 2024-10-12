package org.josafamelo.gateway.apis;

import org.josafamelo.gateway.apis.responses.ResponseCEP;
import org.josafamelo.gateway.repository.CepLogRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CEPClient.class)
class CEPClientTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CepLogRepository repository;

    @MockBean
    private CEPClient cepClient;

    @Test
    void testConsultaCep() throws Exception {
        String cep = "01001000";
        ResponseCEP responseMock = new ResponseCEP();
        responseMock.setCep(cep);
        responseMock.setLogradouro("Praça da Sé");
        responseMock.setBairro("Sé");
        responseMock.setCidade("São Paulo");
        responseMock.setEstado("SP");

        doNothing().when(repository).save(any());
        when(cepClient.consultaCep(cep)).thenReturn(responseMock);

        mockMvc.perform(get("/cep/{cep}", cep).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
