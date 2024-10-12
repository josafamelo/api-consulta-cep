package org.josafamelo.gateway.apis;

import org.josafamelo.gateway.apis.responses.ResponseCEP;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-cep", url = "${feign-client.api-consulta-cep-endpoint}")
public interface CEPClient {

    @GetMapping("/cep/{cep}")
    ResponseCEP consultaCep(@PathVariable("cep") String cep);

}
