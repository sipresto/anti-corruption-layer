package be.kdg.ipj3.rabbitMQ.messageReceiver;

import be.kdg.ipj3.rabbitMQ.config.RabbitMQTopology;
import be.kdg.ipj3.rabbitMQ.messageReceiver.dto.AchievementInputDto;
import be.kdg.ipj3.rabbitMQ.messageReceiver.dto.GameEndInputDto;
import be.kdg.ipj3.rabbitMQ.messageReceiver.dto.MoveMadeInputDto;
import be.kdg.ipj3.rabbitMQ.messageReceiver.dto.registration.RegisterGameInputDto;
import be.kdg.ipj3.service.AclService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiveHandler {

    private final AclService aclService;

    public MessageReceiveHandler(AclService aclService) {
        this.aclService = aclService;
    }

    @RabbitListener(queues = RabbitMQTopology.ACL_REGISTER_QUEUE)
    void onGameRegistrationMessageReceived(RegisterGameInputDto message) {
        aclService.gameRegistrationTransformation(message);
    }

    @RabbitListener(queues = RabbitMQTopology.ACHIEVEMENT_MADE_QUEUE)
    void onAchievementMadeMessageReceived(AchievementInputDto message) {
        aclService.achievementTransformation(message);
    }

    @RabbitListener(queues = RabbitMQTopology.MOVEMADE_QUEUE)
    void onMoveMadeMessageReceived(MoveMadeInputDto message) {
        aclService.gameNotificationTransformation(message);
    }

    @RabbitListener(queues = RabbitMQTopology.ENDGAME_QUEUE)
    void onGameEndMessageReceived(GameEndInputDto message) {
        aclService.gameEndMessageTransformation(message);
    }

}
