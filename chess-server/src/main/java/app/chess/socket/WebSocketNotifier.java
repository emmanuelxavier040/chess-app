package app.chess.socket;

import app.chess.service.PlayerNotifier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@AllArgsConstructor
public class WebSocketNotifier implements PlayerNotifier {

    private WebSocketSession session;
    private ObjectMapper objectMapper;

    @Override
    public void notifyPlayer(Object message) throws IOException {
        this.sendMessage(session, message);
    }

    @Override
    public boolean isOnline() {
        return session.isOpen();
    }

    public void sendMessage(WebSocketSession session, Object message) throws IOException {
        String jsonMessage = formatToJson(message);
        TextMessage response = new TextMessage(jsonMessage);
        if(session.isOpen())
            session.sendMessage(response);
    }

    public String formatToJson(Object message) throws JsonProcessingException {
        return objectMapper.writeValueAsString(message);
    }

}
