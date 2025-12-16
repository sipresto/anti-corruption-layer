package be.kdg.ipj3.rabbitMQ.messageReciever;

import be.kdg.ipj3.rabbitMQ.config.RabbitMQTopology;
import be.kdg.ipj3.rabbitMQ.messageReciever.dto.RegisterGameInputDto;
import be.kdg.ipj3.service.AclService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageRecieveHandler {

    private final AclService aclService;

    public MessageRecieveHandler(AclService aclService) {
        this.aclService = aclService;
    }

    @RabbitListener(queues = RabbitMQTopology.ACL_REGISTER_QUEUE)
    void onGameRegistrationMessageRecieved(RegisterGameInputDto message) {
        aclService.gameRegistrationTransformation(message);
    }



}
