package org.josafamelo.config.logs;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class RequestIdFilter implements Filter {

    private static final String REQUEST_ID_KEY = "executionId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            // Gera um UUID para cada requisição e adiciona ao MDC
            String executionId = UUID.randomUUID().toString();
            MDC.put(REQUEST_ID_KEY, executionId);

            // Continua a cadeia de execução da requisição
            chain.doFilter(request, response);
        } finally {
            // Remove o UUID do MDC para evitar vazamento de contexto
            MDC.remove(REQUEST_ID_KEY);
        }
    }
}
