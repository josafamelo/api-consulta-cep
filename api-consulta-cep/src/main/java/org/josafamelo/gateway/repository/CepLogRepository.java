package org.josafamelo.gateway.repository;

import org.josafamelo.gateway.repository.entity.CepLogTableEntity;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Repository
public class CepLogRepository {

    protected DynamoDbTable<CepLogTableEntity> table;

    public CepLogRepository(DynamoDbClient client){
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(client)
                .build();

        table = enhancedClient.table("cep_log", TableSchema.fromBean(CepLogTableEntity.class));
    }
    public void save(CepLogTableEntity cepLogTable) {
        table.putItem(cepLogTable);
    }
}
