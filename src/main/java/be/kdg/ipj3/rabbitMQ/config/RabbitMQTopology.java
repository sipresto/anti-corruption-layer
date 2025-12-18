package be.kdg.ipj3.rabbitMQ.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopology {
    public static final String GAME_EVENTS_EXCHANGE = "gameExchange";
    public static final String TURN_NOTIFICATIONS_EXCHANGE = "turn-notifications-exchange";

    public static final String ACL_REGISTER_QUEUE = "acl-register-game-queue";
    public static final String ACHIEVEMENT_MADE_QUEUE = "achievement-made-queue";

    public static final String GAME_REGISTRATION_QUEUE = "game-registration-queue";
    public static final String ACHIEVEMENT_COMPLETED_QUEUE = "achievement-completed-queue";

    @Bean
    public TopicExchange gameEventsExchange() {
        return new TopicExchange(GAME_EVENTS_EXCHANGE);
    }

    @Bean
    public TopicExchange notificationsExchange() {
        return new TopicExchange(TURN_NOTIFICATIONS_EXCHANGE);
    }

    //Receive
    @Bean
    Queue registerGameQueue() {
        return QueueBuilder.durable(ACL_REGISTER_QUEUE).build();
    }

    @Bean
    Binding registerBinding(Queue registerGameQueue, TopicExchange gameEventsExchange) {
        return BindingBuilder.bind(registerGameQueue)
                .to(gameEventsExchange)
                .with("game.registered");
    }

    @Bean
    Queue achievementMadeQueue() {
        return QueueBuilder.durable(ACHIEVEMENT_MADE_QUEUE).build();
    }

    @Bean
    Binding achievementMadeBinding(Queue achievementMadeQueue) {
        return BindingBuilder.bind(achievementMadeQueue)
                .to(gameEventsExchange())
                .with("achievement.acquired");
    }


    //Send
    @Bean
    Queue gameRegistrationQueue() {
        return QueueBuilder.durable(GAME_REGISTRATION_QUEUE).build();
    }

    @Bean
    Binding registrationsBinding(Queue gameRegistrationQueue, TopicExchange gameEventsExchange) {
        return BindingBuilder.bind(gameRegistrationQueue)
                .to(gameEventsExchange)
                .with("game.register.*");
    }

    @Bean
    Queue achievementCompletedQueue() {
        return QueueBuilder.durable(ACHIEVEMENT_COMPLETED_QUEUE).build();
    }

    @Bean
    Binding achievementCompletedBinding(Queue achievementCompletedQueue, TopicExchange gameEventsExchange) {
        return BindingBuilder.bind(achievementCompletedQueue)
                .to(gameEventsExchange)
                .with("achievement.completed.*");
    }

}