package org.josafamelo.usecase;

import lombok.extern.slf4j.Slf4j;
import org.josafamelo.domain.CEP;
import org.josafamelo.gateway.apis.APICEPGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsultaCEPUseCase {

    @Autowired
    private APICEPGateway gateway;

    public CEP consultaCEP(String cep){
        try {
            log.info("ConsultaCEPUseCase::consultaCEP: chamando gateway para consulta, cep: {}", cep);
            return gateway.consultarCep(cep);
        } catch (Exception e) {
            log.error("ConsultaCEPUseCase::consultaCEP: erro ao chamar gateway - {}", e.getMessage());
            throw e;
        }
    }

}
