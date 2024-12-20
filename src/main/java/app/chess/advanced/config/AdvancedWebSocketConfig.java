package app.chess.advanced.config;

import app.chess.advanced.connect.WebSocketConnectionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class AdvancedWebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WebSocketConnectionHandler webSocketConnectionHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketConnectionHandler, "/new-game")
                .setAllowedOrigins("*");
    }

}
