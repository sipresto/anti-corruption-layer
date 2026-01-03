package be.kdg.ipj3.service;

import be.kdg.ipj3.rabbitMQ.messageReceiver.dto.AchievementInputDto;
import be.kdg.ipj3.rabbitMQ.messageReceiver.dto.GameEndInputDto;
import be.kdg.ipj3.rabbitMQ.messageReceiver.dto.MoveMadeInputDto;
import be.kdg.ipj3.rabbitMQ.messageReceiver.dto.registration.RegisterAchievementInputDto;
import be.kdg.ipj3.rabbitMQ.messageReceiver.dto.registration.RegisterGameInputDto;
import be.kdg.ipj3.rabbitMQ.messageSender.MessageSendHandler;
import be.kdg.ipj3.rabbitMQ.messageSender.dto.AchievementOutputDto;
import be.kdg.ipj3.rabbitMQ.messageSender.dto.GameResultMessageDto;
import be.kdg.ipj3.rabbitMQ.messageSender.dto.TurnNotificationDto;
import be.kdg.ipj3.rabbitMQ.messageSender.dto.registration.RegisterAchievementOutputDto;
import be.kdg.ipj3.rabbitMQ.messageSender.dto.registration.RegisterGameOutputDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AclService {

    private static final Logger log = LoggerFactory.getLogger(AclService.class);
    private final MessageSendHandler messageSendHandler;

    @Value("${frontend.url}")
    private String frontendUrl;
    @Value("${backend.url}")
    private String backendUrl;

    public AclService(MessageSendHandler messageSendHandler) {
        this.messageSendHandler = messageSendHandler;
    }

    public void gameNotificationTransformation(MoveMadeInputDto message) {
        log.info(message.toString());

        String playerId = "";
        if(message.player().equals("WHITE")) {
            playerId = message.blackPlayerId().toString();
        }
        if(message.player().equals("BLACK")) {
            playerId = message.whitePlayerId().toString();
        }

        var notifDto = new TurnNotificationDto(
                playerId,
                "It's your turn in Chess!"
        );

        messageSendHandler.sendTurnNotification(notifDto);
    }

    public void gameRegistrationTransformation(RegisterGameInputDto message) {
        log.info(message.toString());


        var transformedDate = transformDateToInstant(message.timestamp());

        List<RegisterAchievementOutputDto> transformedAchievements = message.availableAchievements()
                .stream()
                .map(this::transformAchievement)
                .toList();

        var outputMessage = new RegisterGameOutputDto(
                message.registrationId(),
                "Chess",
                "A strategy game where you have to take your opponents king!",
                BigDecimal.TEN,
                backendUrl,
                frontendUrl,
                "Chess is a game where every piece has a set of moves they can do, if you are checked you can only move to prevent being checked, if you get checkmated you lose.",
                transformedAchievements,
                List.of("STRATEGY", "BOARDGAME", "EXTERNAL"),
                message.pictureUrl(),
                transformedDate.toString()
        );

        log.info(outputMessage.toString());
        messageSendHandler.registerGame(outputMessage);
    }

    public void achievementTransformation(AchievementInputDto message) {
        log.info(message.toString());

        var transformedDate = transformDateToInstant(message.timestamp());

        var outputMessage = new AchievementOutputDto(
                message.playerId().toString(),
                message.gameId(),
                message.achievementType(),
                transformedDate.toString()
        );

        log.info(outputMessage.toString());
        messageSendHandler.acquireAchievement(outputMessage);

    }

    //Helpies
    private RegisterAchievementOutputDto transformAchievement(RegisterAchievementInputDto input) {
        String name = codeToReadableName(input.code());
        return new RegisterAchievementOutputDto(
                input.code(),
                name,
                input.description()
        );
    }

    private String codeToReadableName(String code) {
        return Arrays.stream(code.split("_"))
                .map(word -> word.charAt(0) + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

    private Instant transformDateToInstant(LocalDateTime date) {
        return date.atZone(java.time.ZoneId.systemDefault()).toInstant();
    }

    public void gameEndMessageTransformation(GameEndInputDto message) {
        log.info(message.toString());
        String winnerId = null;
        if ("WHITE".equals(message.winner())) {
            winnerId = message.whitePlayerId().toString();
        } else if ("BLACK".equals(message.winner())) {
            winnerId = message.blackPlayerId().toString();
        }
        String status = "PLAYER_WIN";
        if (winnerId == null) {
            status = "DRAW";
        }

        List<String> players = List.of(
                message.whitePlayerId().toString(),
                message.blackPlayerId().toString()
        );
        var gameResult = new GameResultMessageDto(
                message.gameId(),
                players,
                winnerId,
                status,
                transformDateToInstant(message.timestamp()),
                true
        );
        log.info(gameResult.toString());
        messageSendHandler.sendGameResult(gameResult);
    }
}
