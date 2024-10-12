package org.josafamelo.gateway.apis;

import lombok.extern.slf4j.Slf4j;
import org.josafamelo.domain.CEP;
import org.josafamelo.gateway.mapper.CEPGatewayMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class APICEPGateway {

    @Autowired
    private CEPClient cepClient;

    private final CEPGatewayMapper mapper = Mappers.getMapper(CEPGatewayMapper.class);

    public CEP consultarCep(String cep) {
        try {
            log.info("APICEPGateway::consultarCep: iniciando chamada no feignClient");
            return mapper.toDomain(cepClient.consultaCep(cep));
        } catch (Exception ex) {
            log.error("APICEPGateway::consultarCep:Erro ao consultar CEP: {}", ex.getMessage());
            throw ex;
        }
    }

}
