package app.chess.game.board;

import app.chess.game.state.InvalidMoveException;

public class Queen extends Piece {

    public Queen(String colour) {
        super(colour);
    }

    @Override
    public void move(ChessBoard board, int[] from , int[] to) throws InvalidMoveException {
        Square fromSquare = board.getSquare(from[0], from[1]);
        Square toSquare =  board.getSquare(to[0], to[1]);
        toSquare.setPiece(fromSquare.getPiece());
        toSquare.getPiece().moved();
        fromSquare.setPiece(null);
    }
}
