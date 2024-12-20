package app.chess.beginner.config;

import app.chess.beginner.game.GameServer;
import app.chess.socket.GameMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

@Component
public class ChessAppHandler implements WebSocketHandler {

    private GameServer gameServer;

    private ObjectMapper objectMapper;

    public ChessAppHandler(GameServer gameServer, ObjectMapper objectMapper) {
        this.gameServer = gameServer;
        this.objectMapper = objectMapper;
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.printf("Connection established by %s \n", session.getId());
        gameServer.initialize(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        TextMessage textMessage = (TextMessage) message;
        try {
            GameMessage gameMessage = objectMapper.readValue(textMessage.getPayload(), GameMessage.class);

            session.sendMessage(new TextMessage(""));
        } catch (Exception exception) {
            System.out.printf("Something went wrong! %s", exception.getMessage() );
            session.sendMessage(new TextMessage("Something went wrong! "+exception.getMessage()));
        }

    }

    private void cleanUponSessionTermination(WebSocketSession playerSession) {
        gameServer.removeWaitingPlayer(playerSession.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.printf("Transport Error for session %s \n", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.printf("Connection closing by %s \n", session.getId());
        cleanUponSessionTermination(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
