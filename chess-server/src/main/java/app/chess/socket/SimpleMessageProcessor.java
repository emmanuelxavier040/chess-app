package app.chess.socket;

import app.chess.game.action.ChessMove;
import app.chess.service.GameClockService;
import app.chess.service.GameService;
import app.chess.message.GameMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static app.chess.game.state.GameConstants.GAME_STATUS_MOVE;

@Component
@AllArgsConstructor
public class SimpleMessageProcessor {

    private ObjectMapper objectMapper;
    private GameService gameService;
    private GameClockService gameClockService;

    public void processIncomingMessage(WebSocketSession session, TextMessage message) throws IOException {
        GameMessage gameMessage = parseTextMessage(message);

        String messageType = gameMessage.getType();
        switch (messageType) {
            case GAME_STATUS_MOVE:
                gameService.processMove((ChessMove) gameMessage.getAction());
                break;
            default:

        }
    }

    private GameMessage parseTextMessage(TextMessage message) throws JsonProcessingException {
        GameMessage gameMessage = null;
        gameMessage = objectMapper.readValue(message.getPayload(), GameMessage.class);
        return gameMessage;
    }
}
