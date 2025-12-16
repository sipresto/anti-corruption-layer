package be.kdg.ipj3.rabbitMQ.messageReciever.dto;

import be.kdg.ipj3.rabbitMQ.messageSender.dto.RegisterAchievementMessageDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record RegisterGameMessageDto(
        @NotNull UUID registrationId,
        @NotBlank String frontendUrl,
        String pictureUrl,
        List<RegisterAchievementMessageDto> availableAchievements,
        @NotNull LocalDateTime timestamp
) {}
