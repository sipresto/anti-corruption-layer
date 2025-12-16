package be.kdg.ipj3.service;

import be.kdg.ipj3.rabbitMQ.config.RabbitMQTopology;
import be.kdg.ipj3.rabbitMQ.messageReciever.dto.RegisterGameMessageDto;
import be.kdg.ipj3.rabbitMQ.messageSender.MessageSendHandler;
import org.springframework.stereotype.Service;

@Service
public class AclService {

    private final MessageSendHandler messageSendHandler;

    public AclService(MessageSendHandler messageSendHandler) {
        this.messageSendHandler = messageSendHandler;
    }

    public void gameRegistrationTransformation(RegisterGameMessageDto message){
        messageSendHandler.registerGame(message);
    }
}
