package app.chess.game.board;

import app.chess.game.state.InvalidMoveException;

public class Queen extends Piece {

    public Queen(String colour) {
        super(colour);
    }

    @Override
    public void move(ChessBoard board, int[] from , int[] to) throws InvalidMoveException {

    }
}
