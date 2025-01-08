package app.chess.game.state;

import app.chess.game.player.ChessPlayer;

public class BlitzChessGame extends ChessGame {
    public static final Long maxTimeInSeconds = Long.valueOf(600);

    public BlitzChessGame(String gameId, ChessPlayer player1, ChessPlayer player2) {
        super(gameId, maxTimeInSeconds, player1, player2);
    }
}
