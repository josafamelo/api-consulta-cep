package org.josafamelo.config.dynamo;

import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.classic.spi.ILoggingEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.josafamelo.config.logs.LogEvent;
import org.josafamelo.gateway.repository.CepLogRepository;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;
import java.time.Instant;
@AllArgsConstructor
@NoArgsConstructor
public class DynamoDBAppender extends AppenderBase<ILoggingEvent> {

    private String region = "us-east-1";

    private DynamoDbEnhancedClient enhancedClient;
    private DynamoDbTable<LogEvent> logTable;

    public DynamoDBAppender(CepLogRepository repository) {
    }

    @Override
    public void start() {
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .region(Region.of(region))
                .endpointOverride(URI.create("http://localhost:4566"))
                .build();

        enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        logTable = enhancedClient.table("application_logs", TableSchema.fromBean(LogEvent.class));
        super.start();
    }

    @Override
    protected void append(ILoggingEvent event) {
        try {
            String executionId = MDC.get("executionId");

            LogEvent logEvent = new LogEvent();
            logEvent.setExecutionId(executionId);
            logEvent.setLogId(String.valueOf(event.getTimeStamp()));
            logEvent.setLevel(event.getLevel().toString());
            logEvent.setLogger(event.getLoggerName());
            logEvent.setMessage(event.getFormattedMessage());
            logEvent.setThread(event.getThreadName());
            logEvent.setTimestamp(Instant.ofEpochMilli(event.getTimeStamp()).toString());

            PutItemEnhancedRequest<LogEvent> request = PutItemEnhancedRequest.builder(LogEvent.class)
                    .item(logEvent)
                    .build();

            logTable.putItem(request);
        } catch (Exception e) {
            addError("Failed to log event to DynamoDB", e);
        }
    }
}
