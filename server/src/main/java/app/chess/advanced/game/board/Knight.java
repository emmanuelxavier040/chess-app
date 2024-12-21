package app.chess.advanced.game.board;

import app.chess.advanced.game.state.InvalidMoveException;

public class Knight extends Piece {

    public Knight(String colour) {
        super(colour);
    }

    @Override
    public void move(ChessBoard board, int[] from , int[] to) throws InvalidMoveException {

    }
}