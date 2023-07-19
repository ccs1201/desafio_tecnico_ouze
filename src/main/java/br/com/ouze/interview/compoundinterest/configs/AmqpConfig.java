package br.com.ouze.interview.compoundinterest.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public abstract class AmqpConfig {

    @Value("${spring.rabbitmq.listener.simple.default-requeue-rejected:false}")
    protected boolean defaultRequeueRejected;
    @Value("${spring.rabbitmq.listener.simple.concurrency:5}")
    protected int concurrentConsumers;
    @Value("${spring.rabbitmq.listener.simple.prefetch:1}")
    protected int prefetchCount;

    @Bean
    ObjectMapper objectMapper() {
        final var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDefaultPropertyInclusion(NON_NULL);
        return mapper;
    }

    @Bean("jacksonConverter")
    public Jackson2JsonMessageConverter jacksonConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
