package be.kdg.ipj3.rabbitMQ.messageSender.dto;

public record TurnNotificationDto(
        String userId,
        String description
) {
}