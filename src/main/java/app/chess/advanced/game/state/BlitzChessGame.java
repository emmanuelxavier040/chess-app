package app.chess.advanced.game.state;

import app.chess.advanced.game.player.ChessPlayer;

public class BlitzChessGame extends ChessGame {
    public static final Long maxTimeInSeconds = Long.valueOf(60);

    public BlitzChessGame(String gameId, ChessPlayer player1, ChessPlayer player2) {
        super(gameId, maxTimeInSeconds, player1, player2);
    }
}
