package org.josafamelo.gateway.repository;

import org.josafamelo.gateway.repository.entity.CepLogTableEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.internal.client.DefaultDynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import java.util.Date;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CepLogRepositoryTest {

    @Mock
    private DynamoDbClient dynamoDbClient;

    private DynamoDbEnhancedClient enhancedClient;

    @Spy
    DynamoDbTable<CepLogTableEntity> table;

    @InjectMocks
    private CepLogRepository repository;

    @BeforeEach
    void setUp() {
        repository.table = table;
        enhancedClient = mock(DynamoDbEnhancedClient.class);
    }

    @Test
    void testSave() {
        CepLogTableEntity entity = new CepLogTableEntity();
        entity.setCep("01001000");
        entity.setDataHoraRegistro(new Date().toString());
        entity.setResponse("ok");

        doNothing().when(table).putItem(entity);

        repository.save(entity);
        verify(table).putItem(entity);
    }
}
