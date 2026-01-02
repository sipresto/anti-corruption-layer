package be.kdg.ipj3.rabbitMQ.messageSender;

import be.kdg.ipj3.rabbitMQ.config.RabbitMQTopology;
import be.kdg.ipj3.rabbitMQ.messageSender.dto.AchievementOutputDto;
import be.kdg.ipj3.rabbitMQ.messageSender.dto.GameResultMessageDto;
import be.kdg.ipj3.rabbitMQ.messageSender.dto.TurnNotificationDto;
import be.kdg.ipj3.rabbitMQ.messageSender.dto.registration.RegisterGameOutputDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSendHandler {

    private final RabbitTemplate rabbitTemplate;

    public MessageSendHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendGameResult(GameResultMessageDto game) {
        this.rabbitTemplate.convertAndSend(RabbitMQTopology.GAME_EVENTS_EXCHANGE,
                "game.result.acl",
                game);
    }

    public void registerGame(RegisterGameOutputDto game) {
        this.rabbitTemplate.convertAndSend(RabbitMQTopology.GAME_EVENTS_EXCHANGE,
                "game.register.acl", game);
    }

    public void acquireAchievement(AchievementOutputDto achievement) {
        this.rabbitTemplate.convertAndSend(RabbitMQTopology.GAME_EVENTS_EXCHANGE,
                "achievement.completed.chess", achievement);
    }

    public void sendTurnNotification(TurnNotificationDto notifDto) {
        this.rabbitTemplate.convertAndSend(
                RabbitMQTopology.TURN_NOTIFICATIONS_EXCHANGE,
                "turn.notification.chess",
                notifDto
        );
    }
}
