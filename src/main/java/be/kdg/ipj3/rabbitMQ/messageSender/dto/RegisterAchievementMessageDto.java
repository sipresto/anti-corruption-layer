package be.kdg.ipj3.rabbitMQ.messageSender.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterAchievementMessageDto(
@NotBlank String code,
@NotBlank String description
) {
}