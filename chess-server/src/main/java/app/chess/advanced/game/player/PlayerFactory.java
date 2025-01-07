package app.chess.advanced.game.player;


import org.springframework.web.socket.WebSocketSession;

/**
 * An abstraction for the factory to create {@link ChessPlayer}.
 *
 * @author Emmanuel Xavier
 */
public interface PlayerFactory  {


    /**
     * Creates a new player for the session {@link WebSocketSession}.
     *

     * @return A new player {@link ChessPlayer}.
     */
    ChessPlayer createPlayer();

}
