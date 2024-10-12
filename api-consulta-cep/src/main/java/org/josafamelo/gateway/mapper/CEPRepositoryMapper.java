package org.josafamelo.gateway.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.josafamelo.domain.CEP;
import org.josafamelo.gateway.repository.entity.CepLogTableEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CEPRepositoryMapper {

    @Mapping(target = "response", source = "domain", qualifiedByName = "domainToJson")
    CepLogTableEntity toEntity(CEP domain);

    @Named("domainToJson")
    default String domainToJson(CEP domain) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(domain);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
