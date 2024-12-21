package app.chess.advanced.game.player;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
public class PlayerStateManager {

    private final Map<String, ChessPlayer> waitingPlayerSessions = new ConcurrentHashMap<>();

    public void addPlayerToWaitingList(ChessPlayer player) {
        this.waitingPlayerSessions.put(player.getId(), player);
    }

    public void removeWaitingPlayer(String playerId) {
        this.waitingPlayerSessions.remove(playerId);
    }


}
