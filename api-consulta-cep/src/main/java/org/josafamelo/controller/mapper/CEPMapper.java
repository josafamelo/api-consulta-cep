package org.josafamelo.controller.mapper;

import org.josafamelo.controller.response.ResponseCep;
import org.josafamelo.domain.CEP;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CEPMapper {

    ResponseCep domainToResponse(CEP cep);

}
