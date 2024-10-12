package org.josafamelo.config.logs;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Data
@DynamoDbBean
public class LogEvent {

    private String executionId;
    private String logId;
    private String level;
    private String logger;
    private String message;
    private String thread;
    private String timestamp;

    @DynamoDbPartitionKey
    public String getExecutionId() {
        return executionId;
    }

    @DynamoDbSortKey
    public String getTimestamp() {
        return timestamp;
    }

}
