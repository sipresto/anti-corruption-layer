# Chess Game

A web-based chess game built with React frontend and Java Spring Boot backend, using RabbitMQ for messaging and PostgreSQL for data storage.

## Getting Started

This project consists of multiple components that need to be built and run together. Follow the instructions below to get the chess game running locally.

### Prerequisites

- Docker or Podman
- Docker Compose or Podman Compose

### Building and Running the Application

#### 1. Build the Frontend

Navigate to the frontend directory and build the Docker image:

```bash
cd frontend
docker build -t localhost/chessgame-frontend:latest .
# Or with Podman:
# podman build -t localhost/chessgame-frontend:latest .
```

#### 2. Build the Backend

Navigate to the backend directory and build the Docker image:

```bash
cd backend
docker build -t localhost/chessgame-backend:latest .
# Or with Podman:
# podman build -t localhost/chessgame-backend:latest .
```

#### 3. Start the Full Application

From the project root, navigate to the infrastructure directory and start all services:

```bash
cd infrastructure
docker-compose up -d
# Or with Podman:
# podman-compose up -d
```

#### 4. Access the Game

Once all services are running, you can access the chess game at:

**http://localhost:3333**

### Services

The application consists of the following services:

- **Frontend**: React application served on port 3333
- **Backend**: Spring Boot API on port 8080
- **PostgreSQL**: Database on port 5443
- **RabbitMQ**: Message broker on port 5672 (Management UI on port 15672)

### Stopping the Application

To stop all services:

```bash
cd infrastructure
docker-compose down
# Or with Podman:
# podman-compose down
```

## RabbitMQ Messaging

The application uses RabbitMQ for real-time chess game event notifications. All messages are published to the `gameExchange` (TopicExchange) and can be consumed from the `test.queue` for monitoring.

### Exchange Configuration
- **Exchange**: `gameExchange` (TopicExchange)
- **Queue**: `test.queue` (binds to all messages with routing key `#`)

### Routing Keys and Message Types

#### Game Events

| Routing Key | Message Type | Description | Triggered When |
|-------------|--------------|-------------|----------------|
| `game.created` | GameCreatedMessage | New chess game is created | POST `/api/games/{gameId}` |
| `game.player.names.updated` | GameUpdatedMessage | Player names are updated | PUT `/api/games/{gameId}` |
| `game.ended` | GameEndedMessage | Game ends (checkmate/draw) | PATCH `/api/games/{gameId}/mate` or `/draw` |
| `game.registered` | GameRegisteredMessage | Game registered to platform | POST `/api/platform/register/{gameId}` |

#### Move Events

| Routing Key | Message Type | Description | Triggered When |
|-------------|--------------|-------------|----------------|
| `move.made` | MoveMadeMessage | Regular chess move is made | POST `/api/moves/{gameId}` |

#### Achievement Events

| Routing Key | Message Type | Description | Triggered When |
|-------------|--------------|-------------|----------------|
| `achievement.acquired` | AchievementAcquiredMessage | Player earns an achievement | Various game conditions met |

### Message Content

#### GameCreatedMessage
```json
{
  "gameId": "uuid",
  "whitePlayer": "Player white",
  "blackPlayer": "Player black", 
  "currentFen": "starting position",
  "status": "ACTIVE",
  "messageType": "GAME_CREATED",
  "timestamp": "2023-..."
}
```

#### GameUpdatedMessage
```json
{
  "gameId": "uuid",
  "whitePlayer": "Updated white",
  "blackPlayer": "Updated black",
  "currentFen": "current position",
  "status": "ACTIVE",
  "updateType": "PLAYERS",
  "messageType": "GAME_UPDATED",
  "timestamp": "2023-..."
}
```

#### GameEndedMessage
```json
{
  "gameId": "uuid",
  "whitePlayer": "Player white",
  "blackPlayer": "Player black",
  "finalFen": "final position", 
  "endReason": "CHECKMATE|DRAW",
  "winner": "WHITE|BLACK|DRAW",
  "totalMoves": 42,
  "messageType": "GAME_ENDED",
  "timestamp": "2023-..."
}
```

#### MoveMadeMessage
```json
{
  "gameId": "uuid",
  "fromSquare": "e2",
  "toSquare": "e4",
  "sanNotation": "e4",
  "fenAfterMove": "position after move",
  "player": "WHITE|BLACK",
  "moveNumber": 1,
  "whitePlayer": "Player white",
  "blackPlayer": "Player black",
  "moveTime": "2023-...",
  "messageType": "MOVE_MADE",
  "timestamp": "2023-..."
}
```

#### GameRegisteredMessage
```json
{
  "registrationId": "uuid",
  "frontendUrl": "http://localhost:3333/game/abc123",
  "pictureUrl": "https://images.pexels.com/photos/163427/chess-figure-game-play-163427.jpeg",
  "availableAchievements": [
    {
      "code": "FIRST_BLOOD",
      "description": "Capture your opponent's first piece"
    },
    {
      "code": "PAWN_POWER", 
      "description": "Promoted a pawn"
    },
    {
      "code": "SPEEDY_VICTORY",
      "description": "Win in under 20 moves"
    },
    {
      "code": "SPEED_DEMON",
      "description": "Make a move in under 5 second"
    },
    {
      "code": "WINNER_WINNER_CHICKEN_DINNER",
      "description": "Winner winner chicken dinner"
    },
    {
      "code": "CASTLE_TIME",
      "description": "Castle kingside or queenside"
    },
    {
      "code": "ROOKIE_MOVE",
      "description": "Move your rook for the first time"
    },
    {
      "code": "PAWN_STORM",
      "description": "Make 3 pawn moves in a row"
    }
  ],
  "messageType": "GAME_REGISTERED",
  "timestamp": "2023-..."
}
```

#### AchievementAcquiredMessage
```json
{
  "gameId": "uuid",
  "playerId": "uuid", 
  "playerName": "Player Name",
  "achievementType": "FIRST_BLOOD",
  "achievementDescription": "Capture your opponent's first piece",
  "messageType": "ACHIEVEMENT_ACQUIRED",
  "timestamp": "2023-..."
}
```

### Monitoring Messages

Access RabbitMQ Management UI at **http://localhost:15672** (user/password) to monitor message flow and queue activity.

## API Endpoints

### Game Management
- **POST** `/api/games/{gameId}` - Create a new chess game
- **GET** `/api/games/{gameId}` - Get game details  
- **PUT** `/api/games/{gameId}` - Update player names/uuids
- **PATCH** `/api/games/{gameId}/mate` - End game in checkmate
- **PATCH** `/api/games/{gameId}/draw` - End game in draw

### Move Management
- **POST** `/api/moves/{gameId}` - Make a chess move

### Platform Integration
- **POST** `/api/platform/register/{gameId}` - Register game to external platform
  - **Request Body**: `{"frontendUrl": "http://localhost:3333/game/abc123"}`
  - **Response**: `{"success": true, "message": "Game registered to platform successfully", "data": "registration-uuid"}`
  - **RabbitMQ**: Publishes `GameRegisteredMessage` with routing key `game.registered`

## Development

For development purposes, you can also run the services individually:

- Frontend: `cd frontend && npm run dev`
- Backend: `cd backend && ./mvnw spring-boot:run`

Make sure PostgreSQL and RabbitMQ are running (via docker-compose) for the backend to function properly.

## Chess Achievements System

The chess game includes an achievement system that rewards players for various in-game accomplishments. When a player earns an achievement, an `AchievementAcquiredMessage` is published to RabbitMQ with routing key `achievement.acquired`.

### Available Achievements

| Achievement | Code | Description | Trigger Condition |
|-------------|------|-------------|------------------|
| **First Blood** | `FIRST_BLOOD` | Capture your opponent's first piece | Make any capturing move (SAN contains "x") |
| **Pawn Power** | `PAWN_POWER` | Promoted a pawn | Make a pawn promotion move (SAN contains "=") |
| **Speedy Victory** | `SPEEDY_VICTORY` | Win in under 20 moves | Win a game with fewer than 20 total moves |
| **Speed Demon** | `SPEED_DEMON` | Make a move in under 5 seconds | Make a move within 5 seconds of the previous move |
| **Winner Winner Chicken Dinner** | `WINNER_WINNER_CHICKEN_DINNER` | Winner winner chicken dinner | Win any game (awarded once per player) |
| **Castle Time** | `CASTLE_TIME` | Castle kingside or queenside | Perform castling move (SAN contains "O") |
| **Rookie Move** | `ROOKIE_MOVE` | Move your rook for the first time | Make any rook move (SAN contains "R") |
| **Pawn Storm** | `PAWN_STORM` | Make 3 pawn moves in a row | Make 3 consecutive pawn moves as the same player |


## Author

Kevin Smeyers