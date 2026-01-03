package be.kdg.ipj3.rabbitMQ.messageSender.dto;

import java.util.UUID;

public record AchievementOutputDto(
        String userId,
        UUID gameId,
        String code,
        String timestamp
) {
}
