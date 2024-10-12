package org.josafamelo.gateway.repository.entity;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class CepLogTableEntity {

        public String cep;
        public String dataHoraRegistro;
        public String response;

        @DynamoDbPartitionKey
        public String getCep() {
                return cep;
        }

        @DynamoDbSortKey
        public String getDataHoraRegistro() {
                return new Date().toString();
        }
}
