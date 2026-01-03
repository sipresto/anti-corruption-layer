package be.kdg.ipj3.rabbitMQ.messageReceiver.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record MoveMadeInputDto(
        UUID gameId,
        String fromSquare,
        String toSquare,
        String sanNotation,
        String fenAfterMove,
        String player,
        int moveNumber,
        UUID whitePlayerId,
        String whitePlayerName,
        UUID blackPlayerId,
        String blackPlayerName,
        LocalDateTime moveTime,
        String messageType,
        LocalDateTime timestamp
) {
}