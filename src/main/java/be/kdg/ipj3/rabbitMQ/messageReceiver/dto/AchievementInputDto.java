package be.kdg.ipj3.rabbitMQ.messageReceiver.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AchievementInputDto(
        UUID gameId,
        UUID playerId,
        String playerName,
        String achievementType,
        String achievementDescription,
        String messageType,
        LocalDateTime timestamp
) {
}
