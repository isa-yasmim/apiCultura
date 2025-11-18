package br.edu.utfpr.apicultura.app.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Adiciona proxyBeanMethods = false para garantir que os beans sejam injetados diretamente, 
// o que é vital para quebrar ciclos de inicialização complexos.
@Configuration(proxyBeanMethods = false) 
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * FIX DEFINITIVO: Define o RabbitTemplate explicitamente.
     * Isso substitui a Autoconfiguração do Spring Boot, que estava gerando o ciclo.
     */
    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter jsonMessageConverter) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        
        // Aplica o conversor JSON.
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        
        return rabbitTemplate;
    }

}