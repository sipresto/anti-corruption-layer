package be.kdg.ipj3.rabbitMQ.messageSender.dto.registration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record RegisterGameOutputDto(
        @NotNull UUID id,
        String name,
        String description,
        BigDecimal price,
        @NotBlank String frontendUrl,
        @Size(max = 2000) String rules,
        List<RegisterAchievementOutputDto> achievements,
        List<String> categories,
        String imageUrl,
        @NotNull String timestamp,
        @NotNull int maxPlayers
) {
}