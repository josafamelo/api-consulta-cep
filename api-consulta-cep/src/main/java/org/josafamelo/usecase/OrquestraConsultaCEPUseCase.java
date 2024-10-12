package org.josafamelo.usecase;

import lombok.extern.slf4j.Slf4j;
import org.josafamelo.domain.CEP;
import org.josafamelo.gateway.apis.APICEPGateway;
import org.josafamelo.gateway.mapper.CEPRepositoryMapper;
import org.josafamelo.gateway.repository.CepLogRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrquestraConsultaCEPUseCase {

    @Autowired
    private APICEPGateway gateway;

    @Autowired
    private CepLogRepository repository;

    private final CEPRepositoryMapper mapper = Mappers.getMapper(CEPRepositoryMapper.class);

    public CEP orquestraConsultaCep(String cep){
        try {
            log.info("OrquestraConsultaCEPUseCase::orquestraConsultaCep, iniciando orquestração de consulta cep: {}", cep);

            CEP responseGateway = gateway.consultarCep(cep);
            log.info("OrquestraConsultaCEPUseCase::orquestraConsultaCep, consulta no gateway efetuada: {}", responseGateway);

            repository.save(mapper.toEntity(responseGateway));
            log.info("OrquestraConsultaCEPUseCase::orquestraConsultaCep, sucesso na consulta");

            return responseGateway;

        } catch (Exception e) {
            log.error("OrquestraConsultaCEPUseCase::orquestraConsultaCep: erro na consulta - {}", e.getMessage());
            throw e;
        }
    }
}
