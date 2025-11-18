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

@Configuration
public class RabbitMQConfig {

    // Injeta os valores do properties para facilitar
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    // --- CONFIGURAÇÃO DA INFRAESTRUTURA ---

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Queue queue() {
        // durable=true (a fila sobrevive a restarts do RabbitMQ)
        return new Queue(queueName, true); 
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    // --- CORREÇÃO DO PROBLEMA DE SERIALIZAÇÃO ---

    /**
     * Define o Jackson2JsonMessageConverter. 
     * Isso garante que a serialização do Java para JSON seja pura, sem metadados do Spring.
     */
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Sobrescreve o RabbitTemplate para usar o nosso conversor JSON.
     * Isso garante que todas as mensagens enviadas pelo RabbitTemplate serão JSON puro.
     */
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        
        // Aplica o conversor JSON puro ao template
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        
        return rabbitTemplate;
    }
}