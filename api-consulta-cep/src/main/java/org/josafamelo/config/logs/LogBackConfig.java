package org.josafamelo.config.logs;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.josafamelo.config.dynamo.DynamoDBAppender;
import org.josafamelo.gateway.repository.CepLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogBackConfig {

    @Autowired
    private CepLogRepository repository;

    @Bean
    public Appender<ILoggingEvent> dynamoDBAppender(CepLogRepository repository) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        DynamoDBAppender appender = new DynamoDBAppender(repository);
        appender.setContext(loggerContext);
        appender.start();
        return appender;
    }
}
