package be.kdg.ipj3.rabbitMQ.messageSender.dto;

import java.util.UUID;

public record AchievementOutputDto(
        UUID userId,
        UUID gameSessionId,
        String code,
        String timestamp
) {
}
