package br.com.ouze.interview.compoundinterest.config.queue;

import br.com.ouze.interview.compoundinterest.config.AmqpConfig;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfirmationAmqpConfig extends AmqpConfig {

    @Value("${api.event.exchange}")
    private String exchange;

    @Value("${api.event.payment.confirmation.routing-key}")
    private String routingKey;

    @Value("${api.event.payment.confirmation.queue}")
    private String queue;

    @Value("${api.event.payment.confirmation.error.ttl}")
    private Integer ttl;

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
                .directExchange(exchange)
                .durable(true)
                .autoDelete()
                .build();
    }


    @Bean
    Queue queue() {
        return QueueBuilder.durable(queue)
                .autoDelete()
                .deadLetterExchange("")
                .deadLetterRoutingKey(getDlq())
                .build();
    }

    private String getDlq() {
        return this.queue.concat(".dlq");
    }

    @Bean
    Queue parkingLot() {
        return QueueBuilder.durable(getParkingLot())
                .lazy()
                .build();
    }

    private String getParkingLot() {
        return this.queue.concat(".parking-lot");
    }

    @Bean
    Queue dlq() {
        return QueueBuilder.durable(getDlq())
                .autoDelete()
                .deadLetterExchange("")
                .deadLetterRoutingKey(queue)
                .ttl(ttl)
                .build();
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue())
                .to(directExchange())
                .with(routingKey);
    }
}
