package app.chess.game.board;

import app.chess.game.state.InvalidMoveException;

public class Rook extends Piece {

    public Rook(String colour) {
        super(colour);
    }

    @Override
    public void move(ChessBoard board, int[] from , int[] to) throws InvalidMoveException {

        int from_rank = from[0];
        int from_file = from[1];
        int to_rank = to[0];
        int to_file = to[1];

        if(isHittingAPiece(board, from_rank, from_file, to_rank, to_file)) {
            throw new InvalidMoveException("Cannot move a Rook through non-empty squares.");
        }

        Square fromSquare = board.getSquare(from[0], from[1]);
        Square toSquare =  board.getSquare(to[0], to[1]);
        toSquare.setPiece(fromSquare.getPiece());
        toSquare.getPiece().moved();
        fromSquare.setPiece(null);
    }
}
