package app.chess.beginner.game;

import app.chess.beginner.model.Player;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class PlayerService {

    Player initializePlayer(WebSocketSession playerSession) {
        Player player = new Player();
        player.setId(playerSession.getId());
        player.setSession(playerSession);
        return player;
    }

}
