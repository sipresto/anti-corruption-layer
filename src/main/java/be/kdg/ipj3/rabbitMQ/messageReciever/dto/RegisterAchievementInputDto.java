package be.kdg.ipj3.rabbitMQ.messageReciever.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterAchievementInputDto(
@NotBlank String code,
@NotBlank String description
) {
}