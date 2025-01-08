package app.chess.game.board;

import app.chess.game.state.InvalidMoveException;

public class Rook extends Piece {

    public Rook(String colour) {
        super(colour);
    }

    @Override
    public void move(ChessBoard board, int[] from , int[] to) throws InvalidMoveException {

    }
}
