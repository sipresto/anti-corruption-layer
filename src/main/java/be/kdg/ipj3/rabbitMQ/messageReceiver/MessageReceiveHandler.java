package be.kdg.ipj3.rabbitMQ.messageReceiver;

import be.kdg.ipj3.rabbitMQ.config.RabbitMQTopology;
import be.kdg.ipj3.rabbitMQ.messageReceiver.dto.RegisterGameInputDto;
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



}
