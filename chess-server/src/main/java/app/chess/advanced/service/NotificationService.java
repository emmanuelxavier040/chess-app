package app.chess.advanced.service;

import app.chess.advanced.action.ChessMove;
import app.chess.advanced.game.state.ChessGame;
import app.chess.socket.GameMessage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static app.chess.beginner.model.GameConstants.*;

@Component
public class NotificationService {

    private final Map<String, PlayerNotifier> playerNotifierMap = new HashMap<>();


    private PlayerNotifier getNotifier(String playerId) {
        return this.playerNotifierMap.get(playerId);
    }

    public void registerNotifier(String playerId, PlayerNotifier playerNotifier) {
        this.playerNotifierMap.put(playerId, playerNotifier);
    }

    public void removeNotifier(String playerId) {
        this.playerNotifierMap.remove(playerId);
    }

    public final void notifyClient(String playerId, Object object) throws IOException {
        PlayerNotifier playerNotifier = this.getNotifier(playerId);
        playerNotifier.notifyPlayer(object);
    }

    public boolean isOnline(String playerId) {
        PlayerNotifier playerNotifier = this.getNotifier(playerId);
        return playerNotifier.isOnline();
    }

    public final void notifyMove(String playerId, ChessMove move) throws IOException {
        notifyClient(playerId, move);
    }

    public final void notifyToWait(String playerId) throws IOException {
        GameMessage message = new GameMessage();
        message.setSessionId(playerId);
        this.notifyClient(playerId, message);
    }

    public final void notifyGameStart(ChessGame chessGame) throws IOException {
        chessGame.setStatus(GAME_STATUS_START);
        this.notifyClient(chessGame.getCurrentPlayer().getId(), chessGame);
    }

    public final void notifyGameEvent(ChessGame chessGame) throws IOException {
        chessGame.setStatus(GAME_STATUS_IN_PROGRESS);
        this.notifyClient(chessGame.getCurrentPlayer().getId(), chessGame);
    }

    public final void notifyError(String playerId, Object object) throws IOException {
        this.notifyClient(playerId, object);
    }

    public final void notifyGameEnd(ChessGame chessGame) throws IOException {
        chessGame.setStatus(GAME_STATUS_END);
        this.notifyClient(chessGame.getCurrentPlayer().getId(), chessGame);
    }

}
