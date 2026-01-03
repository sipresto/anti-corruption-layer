package be.kdg.ipj3.rabbitMQ.messageSender.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record GameResultMessageDto(
        UUID gameId,
        List<String> players,
        String winnerId,
        String status,
        Instant completedAt,
        boolean endOfGame
) {

}