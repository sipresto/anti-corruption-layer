package be.kdg.ipj3.rabbitMQ.messageReciever.dto;

import be.kdg.ipj3.rabbitMQ.messageSender.dto.RegisterAchievementMessageDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record RegisterGameMessageDto(
        @NotNull UUID id,
        String name,
        String description,
        @NotBlank String frontendUrl,
        String imageUrl,
        List<RegisterAchievementMessageDto> achievements,
        @NotNull Instant timestamp
) {
}
