package be.kdg.ipj3.rabbitMQ.messageReciever;

import be.kdg.ipj3.rabbitMQ.config.RabbitMQTopology;
import be.kdg.ipj3.rabbitMQ.messageReciever.dto.RegisterGameMessageDto;
import be.kdg.ipj3.service.AclService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageRecieveHandler {

    private final AclService aclService;

    public MessageRecieveHandler(AclService aclService) {
        this.aclService = aclService;
    }

    @RabbitListener(queues = RabbitMQTopology.REGISTER_GAME_EXCHANGE)
    void onGameRegistrationMessageRecieved(RegisterGameMessageDto message) {
        aclService.gameRegistrationTransformation(message);
    }



}
