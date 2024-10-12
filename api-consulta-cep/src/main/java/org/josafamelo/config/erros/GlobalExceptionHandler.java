package org.josafamelo.config.erros;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignStatusException(FeignException ex) {
        // Extrai o status HTTP da exceção Feign
        HttpStatus status = HttpStatus.resolve(ex.status());

        // Extrai a mensagem do corpo da resposta externa (se disponível)
        String message = ex.contentUTF8();

        // Se não houver status mapeado, retorna INTERNAL_SERVER_ERROR
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        // Cria uma resposta personalizada para o cliente da API
        ErrorResponse errorResponse = new ErrorResponse(status.value(), message);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
