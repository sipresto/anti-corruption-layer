package be.kdg.ipj3.service;

import be.kdg.ipj3.rabbitMQ.messageReciever.dto.RegisterGameMessageDto;
import be.kdg.ipj3.rabbitMQ.messageSender.MessageSendHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AclService {

    private static final Logger log = LoggerFactory.getLogger(AclService.class);
    private final MessageSendHandler messageSendHandler;

    public AclService(MessageSendHandler messageSendHandler) {
        this.messageSendHandler = messageSendHandler;
    }

    public void gameRegistrationTransformation(RegisterGameMessageDto message){
        log.info(message.toString());
        messageSendHandler.registerGame(message);
    }
}
