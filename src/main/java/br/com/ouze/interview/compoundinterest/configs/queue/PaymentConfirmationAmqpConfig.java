package br.com.ouze.interview.compoundinterest.configs.queue;

import br.com.ouze.interview.compoundinterest.configs.AmqpConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PaymentConfirmationAmqpConfig extends AmqpConfig {

    private final PaymentConfirmationConstant constant;

    @Bean
    public RabbitAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        var admin = new RabbitAdmin(connectionFactory);
        admin.declareExchange(directExchange());
        admin.declareQueue(queue());
        admin.declareBinding(binding());
        admin.declareQueue(dlq());
        admin.declareQueue(parkingLot());
        return admin;
    }

    @Bean
    SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        final var factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(concurrentConsumers);
        factory.setDefaultRequeueRejected(defaultRequeueRejected);
        factory.setPrefetchCount(prefetchCount);
        return factory;
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    DirectExchange directExchange() {
        return ExchangeBuilder
                .directExchange(constant.getExchange())
                .durable(true)
                .autoDelete()
                .build();
    }


    @Bean
    Queue queue() {
        return QueueBuilder.durable(constant.getQueue())
                .autoDelete()
                .deadLetterExchange(PaymentConfirmationConstant.EMPTY)
                .deadLetterRoutingKey(constant.getDql())
                .build();
    }

    @Bean
    Queue parkingLot() {
        return QueueBuilder.durable(constant.getParkingLot())
                .lazy()
                .build();
    }

    @Bean
    Queue dlq() {
        return QueueBuilder.durable(constant.getDql())
                .autoDelete()
                .deadLetterExchange(PaymentConfirmationConstant.EMPTY)
                .deadLetterRoutingKey(constant.getQueue())
                .ttl(constant.getTtl())
                .build();
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue())
                .to(directExchange())
                .with(constant.getRoutingKey());
    }
}
