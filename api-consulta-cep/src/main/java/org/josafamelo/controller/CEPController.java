package org.josafamelo.controller;

import lombok.extern.slf4j.Slf4j;
import org.josafamelo.controller.mapper.CEPMapper;
import org.josafamelo.controller.response.ResponseCep;
import org.josafamelo.usecase.OrquestraConsultaCEPUseCase;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cep")
public class CEPController {

    @Autowired
    private OrquestraConsultaCEPUseCase useCase;
    private final CEPMapper mapper = Mappers.getMapper(CEPMapper.class);

    @GetMapping("/{cep}")
    public ResponseEntity<ResponseCep> consultarCep(@PathVariable String cep){
        log.info("CEPController::consultarCep: Iniciando consulta, cep: {}", cep);
        ResponseCep response = mapper.domainToResponse(useCase.orquestraConsultaCep(cep));
        log.info("CEPController::consultarCep: consulta efetuada, response: {}", response.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
