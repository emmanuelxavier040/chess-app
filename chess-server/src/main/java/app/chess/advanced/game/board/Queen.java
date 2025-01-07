package app.chess.advanced.game.board;

import app.chess.advanced.game.state.InvalidMoveException;

public class Queen extends Piece {

    public Queen(String colour) {
        super(colour);
    }

    @Override
    public void move(ChessBoard board, int[] from , int[] to) throws InvalidMoveException {

    }
}
