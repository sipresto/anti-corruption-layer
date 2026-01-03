package be.kdg.ipj3.rabbitMQ.messageReceiver.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameEndInputDto(
        UUID gameId,
        UUID whitePlayerId,
        String whitePlayerName,
        UUID blackPlayerId,
        String blackPlayerName,
        String finalFen,
        String endReason,
        String winner,
        int totalMoves,
        String messageType,
        LocalDateTime timestamp
) {}