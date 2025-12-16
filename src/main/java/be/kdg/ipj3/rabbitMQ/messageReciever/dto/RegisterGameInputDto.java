package be.kdg.ipj3.rabbitMQ.messageReciever.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record RegisterGameInputDto(
        @NotNull UUID registrationId,
        @NotBlank String frontendUrl,
        String pictureUrl,
        List<RegisterAchievementInputDto> availableAchievements,
        @NotNull LocalDateTime timestamp,
        String messageType
) {}
