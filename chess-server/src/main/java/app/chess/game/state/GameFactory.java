package app.chess.game.state;

import app.chess.game.player.ChessPlayer;

/**
 * An abstraction of a factory to create the states of new chess game {@link ChessGame}
 * for players of type {@link ChessPlayer}.
 *
 * @author Emmanuel Xavier
 */
public interface GameFactory {


    /**
     * Creates the state for a new game.
     *
     * @param player1 One of the players.
     * @param player2 The other player.
     * @return The state of the new game {@link ChessGame}.
     */
    ChessGame createGame(ChessPlayer player1, ChessPlayer player2);

}
