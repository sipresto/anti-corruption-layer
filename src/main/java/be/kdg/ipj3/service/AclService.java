package be.kdg.ipj3.service;

import be.kdg.ipj3.rabbitMQ.messageReciever.dto.RegisterAchievementInputDto;
import be.kdg.ipj3.rabbitMQ.messageReciever.dto.RegisterGameInputDto;
import be.kdg.ipj3.rabbitMQ.messageSender.MessageSendHandler;
import be.kdg.ipj3.rabbitMQ.messageSender.dto.RegisterAchievementOutputDto;
import be.kdg.ipj3.rabbitMQ.messageSender.dto.RegisterGameOutputDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AclService {

    private static final Logger log = LoggerFactory.getLogger(AclService.class);
    private final MessageSendHandler messageSendHandler;

    public AclService(MessageSendHandler messageSendHandler) {
        this.messageSendHandler = messageSendHandler;
    }

    public void gameRegistrationTransformation(RegisterGameInputDto message){
        log.info(message.toString());

        java.time.Instant outputTimestamp = message.timestamp()
                .atZone(java.time.ZoneId.systemDefault())
                .toInstant();


        List<RegisterAchievementOutputDto> transformedAchievements = message.availableAchievements()
                .stream()
                .map(this::transformAchievement)
                .toList();

        var outputMessage = new RegisterGameOutputDto(
                message.registrationId(),
                "Chess",
                "A strategy game where you have to take your opponents king!",
                BigDecimal.TEN,
                message.frontendUrl(),
                "Every piece has a set of moves they can do, if you are checked you can only move to prevent being checked, if you get checkmated you lose.",
                transformedAchievements,
                List.of("STRATEGY", "BOARDGAME"),
                message.pictureUrl(),
                outputTimestamp.toString()
        );

        log.info(outputMessage.toString());
        messageSendHandler.registerGame(outputMessage);
    }

    private RegisterAchievementOutputDto transformAchievement(RegisterAchievementInputDto input) {
        String name = codeToReadableName(input.code());
        return new RegisterAchievementOutputDto(
                name,
                input.description(),
                input.code()
        );
    }

    private String codeToReadableName(String code) {
        return Arrays.stream(code.split("_"))
                .map(word -> word.charAt(0) + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
