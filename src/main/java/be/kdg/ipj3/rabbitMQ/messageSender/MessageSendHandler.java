package be.kdg.ipj3.rabbitMQ.messageSender;

import be.kdg.ipj3.rabbitMQ.config.RabbitMQTopology;
import be.kdg.ipj3.rabbitMQ.messageReciever.dto.RegisterGameMessageDto;
import be.kdg.ipj3.rabbitMQ.messageSender.dto.GameResultMessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSendHandler {

    private final RabbitTemplate rabbitTemplate;

    public MessageSendHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendGameResult(GameResultMessageDto game){
        this.rabbitTemplate.convertAndSend(RabbitMQTopology.GAME_EVENTS_EXCHANGE,
                "game.result.acl",
                game);
    }

    public void registerGame(RegisterGameMessageDto game){
        this.rabbitTemplate.convertAndSend(RabbitMQTopology.GAME_EVENTS_EXCHANGE,
                "game.register.acl", game);
    }


//    public void sendAchievementCompleted(Game game) {
//        Player winner = game.getWinner();
//
//        if (winner != null && winner.isHuman()) {
//            String achievementCode;
//
//            if (winner.equals(game.getPlayer1())) {
//                achievementCode = "BLUE";
//            } else {
//                achievementCode = "RED";
//            }
//
//            AchievementCompletedDto dto = new AchievementCompletedDto(
//                    winner.id().id(),
//                    game.getId().id(),
//                    achievementCode,
//                    Instant.now()
//            );
//
//            this.rabbitTemplate.convertAndSend(
//                    RabbitMQTopology.GAME_EVENTS_EXCHANGE,
//                    "achievement.completed.quoridor",
//                    dto
//            );
//        }
//    }


//    public void sendTurnNotification(Game game) {
//        TurnNotificationDto dto = new TurnNotificationDto(
//                game.getCurrentPlayer().id().id(),
//                "It's your turn in Quoridor!"
//        );
//
//        this.rabbitTemplate.convertAndSend(
//                RabbitMQTopology.TURN_NOTIFICATIONS_EXCHANGE,
//                "turn.notification.quoridor",
//                dto
//        );
//    }
}
