package org.josafamelo.config.erros;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.josafamelo.gateway.exception.GatewayException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String errorMessage;
        try {
            errorMessage = response.body() != null
                    ? new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8)
                    : "Erro desconhecido.";
        } catch (IOException e) {
            errorMessage = "Erro ao ler a resposta da API externa.";
        }

        return new GatewayException(errorMessage, response.status());
    }
}
