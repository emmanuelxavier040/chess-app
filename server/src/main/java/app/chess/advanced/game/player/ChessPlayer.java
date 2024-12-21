package app.chess.advanced.game.player;

import app.chess.advanced.action.ChessMove;
import app.chess.advanced.game.state.ChessGame;
import app.chess.advanced.service.PlayerNotifier;
import app.chess.socket.GameMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalTime;

import static app.chess.beginner.model.GameConstants.*;


@Setter
@Getter
public abstract class ChessPlayer {

    String id;
    String name;
    String colour;
    Long secondsLeft;

    @JsonIgnore
    LocalTime startTime;

    @JsonIgnore
    PlayerNotifier playerNotifier;

    public final boolean isOnline() {
        return this.playerNotifier.isOnline();
    }

    public final void notifyMove(ChessMove move) throws IOException {
        move.setPlayerId(id);
        this.playerNotifier.notifyPlayer(move);
    }

    public final void notifyToWait() throws IOException {
        GameMessage message = new GameMessage();
        message.setSessionId(this.id);
        this.playerNotifier.notifyPlayer(message);
    }

    public final void notifyGameStart(ChessGame chessGame) throws IOException {
        chessGame.setStatus(GAME_STATUS_START);
        this.playerNotifier.notifyPlayer(chessGame);
    }

    public final void notifyGameEvent(ChessGame chessGame) throws IOException {
        chessGame.setStatus(GAME_STATUS_IN_PROGRESS);
        this.playerNotifier.notifyPlayer(chessGame);
    }

    public final void notifyError(Object object) throws IOException {
        this.playerNotifier.notifyPlayer(object);
    }

    public final void notifyGameEnd(ChessGame chessGame) throws IOException {
        chessGame.setStatus(GAME_STATUS_END);
        this.playerNotifier.notifyPlayer(chessGame);
    }


}
