package app.chess.advanced.game.board;

import app.chess.advanced.game.state.InvalidMoveException;

public class Rook extends Piece {

    public Rook(String colour) {
        super(colour);
    }

    @Override
    public void move(ChessBoard board, int[] from , int[] to) throws InvalidMoveException {

    }
}
