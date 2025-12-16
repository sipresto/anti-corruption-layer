package be.kdg.ipj3.rabbitMQ.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopology {
    public static final String GAME_EVENTS_EXCHANGE = "gameExchange";
    public static final String TURN_NOTIFICATIONS_EXCHANGE = "turn-notifications-exchange";

    public static final String ACL_REGISTER_QUEUE = "acl.register.game.queue";

    @Bean
    public TopicExchange gameEventsExchange() {
        return new TopicExchange(GAME_EVENTS_EXCHANGE);
    }

    @Bean
    public TopicExchange notificationsExchange() {
        return new TopicExchange(TURN_NOTIFICATIONS_EXCHANGE);
    }


    @Bean
    Queue registerGameQueue() {
        return QueueBuilder.durable(ACL_REGISTER_QUEUE).build();
    }

    @Bean
    Binding binding(Queue registerGameQueue, TopicExchange gameEventsExchange) {
        return BindingBuilder.bind(registerGameQueue)
                .to(gameEventsExchange)
                .with("game.registered");
    }
}