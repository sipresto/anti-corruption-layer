package be.kdg.ipj3.rabbitMQ.messageSender.dto;

import java.util.List;
import java.util.UUID;

public record GameCreatedOutputDto(
        UUID gameId,
        UUID sessionId,
        List<UUID> playerIds
){
}
