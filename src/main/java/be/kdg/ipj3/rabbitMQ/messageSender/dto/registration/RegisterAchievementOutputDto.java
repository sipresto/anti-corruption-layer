package be.kdg.ipj3.rabbitMQ.messageSender.dto.registration;

import jakarta.validation.constraints.NotBlank;

public record RegisterAchievementOutputDto(
        @NotBlank String code,
        @NotBlank String name,
        @NotBlank String description
) {
}