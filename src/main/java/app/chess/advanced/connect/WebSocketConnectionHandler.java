package app.chess.advanced.connect;

import app.chess.advanced.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;

@Component
@AllArgsConstructor
public class WebSocketConnectionHandler implements WebSocketHandler {

    private GameService gameService;
    private SimpleMessageProcessor messageProcessor;
    private ApplicationContext applicationContext;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        System.out.printf("Connection established by %s \n", session.getId());
        PlayerNotifier playerNotifier = new WebSocketNotifier(session, applicationContext.getBean(ObjectMapper.class));
        gameService.processNewGameRequest(session.getId(), playerNotifier);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        TextMessage textMessage = (TextMessage) message;
        messageProcessor.processIncomingMessage(session, textMessage);
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.out.printf("Transport Error for session %s \n", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        System.out.printf("Connection closed by %s \n", session.getId());
        gameService.processEndGameRequest(session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
