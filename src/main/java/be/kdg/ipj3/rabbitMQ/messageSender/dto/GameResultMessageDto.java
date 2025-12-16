package be.kdg.ipj3.rabbitMQ.messageSender.dto;

import be.kdg.ipj3.domain.MessageType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record GameResultMessageDto(
        UUID gameId,
        List<String> players,
        //"whitePlayer": "Player white",
        //"blackPlayer": "Player black",
        String winner,
        //"winner": "WHITE|BLACK|DRAW",
        Instant completedAt,
        //"timestamp": "2023-..."
        Integer totalMoves,
        //"totalMoves": 42
        MessageType messageType
        // "MessageType": "GAME_ENDED",
) {

}