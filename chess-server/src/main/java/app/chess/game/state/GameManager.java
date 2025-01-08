package app.chess.game.state;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
public class GameManager {

    private final Map<String, ChessGame> games = new ConcurrentHashMap<>();

    public void addGameState(ChessGame gameState) {
        this.games.put(gameState.getGameId(), gameState);
    }

    public void removeGameState(String gameId) { this.games.remove(gameId); }

    public ChessGame getGame(String gameId) { return this.games.get(gameId); }

    public Set<String> getGameIds() { return games.keySet(); }

}
