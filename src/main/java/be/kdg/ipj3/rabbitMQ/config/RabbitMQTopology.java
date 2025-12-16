package be.kdg.ipj3.rabbitMQ.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopology {
    public static final String GAME_EVENTS_EXCHANGE = "game-events-exchange";
    public static final String TURN_NOTIFICATIONS_EXCHANGE = "turn-notifications-exchange";
    public static final String REGISTER_GAME_EXCHANGE = "register-game-exchange";

    @Bean
    public TopicExchange gameEventsExchange() {
        return new TopicExchange(GAME_EVENTS_EXCHANGE);
    }

    @Bean
    public TopicExchange notificationsExchange() {
        return new TopicExchange(TURN_NOTIFICATIONS_EXCHANGE);
    }

    @Bean
    public TopicExchange registerGameExchange() {
        return new TopicExchange(REGISTER_GAME_EXCHANGE);
    }

    @Bean
    Queue registerGameQueue() {
        return QueueBuilder.durable(REGISTER_GAME_EXCHANGE).build();
    }

    @Bean
    Binding binding(Queue aclQueue, TopicExchange registerGameExchange) {
        return BindingBuilder.bind(aclQueue)
                .to(registerGameExchange)
                .with("game.registered");
    }
}