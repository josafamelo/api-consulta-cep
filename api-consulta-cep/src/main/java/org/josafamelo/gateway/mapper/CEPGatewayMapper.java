package org.josafamelo.gateway.mapper;

import org.josafamelo.domain.CEP;
import org.josafamelo.gateway.apis.responses.ResponseCEP;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CEPGatewayMapper {

    CEP toDomain(ResponseCEP response);
}
