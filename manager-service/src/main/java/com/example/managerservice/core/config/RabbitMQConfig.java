package com.example.managerservice.core.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MarshallingMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.task.queue}")
    private String taskQueue;

    @Value("${rabbitmq.result.queue}")
    private String resultQueue;

    @Value("${rabbitmq.tasks.exchange}")
    private String taskExchange;

    @Value("${rabbitmq.result.exchange}")
    private String resultExchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.result.key}")
    private String resultKey;

    @Bean
    public Queue taskQueue() {
        return QueueBuilder
                .durable(taskQueue)
                .build();
    }

    @Bean
    public Queue resultQueue() {
        return QueueBuilder
                .durable(resultQueue)
                .build();
    }

    @Bean
    public DirectExchange taskExchange() {
        return new DirectExchange(taskExchange, true, false);
    }

    @Bean
    public Binding taskBinding() {
        return BindingBuilder
                .bind(taskQueue())
                .to(taskExchange())
                .with(routingKey);
    }

    @Bean
    public DirectExchange resultExchange() {
        return new DirectExchange(resultExchange, true, false);
    }

    @Bean
    public Binding resultBinding() {
        return BindingBuilder
                .bind(resultQueue())
                .to(resultExchange())
                .with(resultKey);
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com.example.managerservice.core.model");
        return marshaller;
    }

    @Bean
    public MarshallingMessageConverter marshallingMessageConverter(final Jaxb2Marshaller marshaller) {
        return new MarshallingMessageConverter(marshaller);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory, final MarshallingMessageConverter messageConverter) {
        final RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
