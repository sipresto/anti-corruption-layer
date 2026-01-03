package be.kdg.ipj3.rabbitMQ.messageReceiver.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameCreatedInputDto(
        UUID gameId,
        UUID whitePlayerId,
        String whitePlayerName,
        UUID blackPlayerId,
        String blackPlayerName,
        String currentFen,
        String status,
        String messageType,
        LocalDateTime timestamp
) {
}