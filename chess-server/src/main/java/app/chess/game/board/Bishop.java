package app.chess.game.board;

import app.chess.game.state.InvalidMoveException;

public class Bishop extends Piece {

    public Bishop(String colour) {
        super(colour);
    }

    @Override
    public void move(ChessBoard board, int[] from , int[] to) throws InvalidMoveException {

        int from_rank = from[0];
        int from_file = from[1];
        int to_rank = to[0];
        int to_file = to[1];

        if(Math.abs(from_rank - to_rank) != Math.abs(from_file - to_file)) {
              throw new InvalidMoveException("Cannot move a bishop through non-diagonal squares.");
        }
        if(isHittingAPiece(board, from_rank, from_file, to_rank, to_file)) {
            throw new InvalidMoveException("Cannot move a bishop through non-empty squares.");
        }

        Square fromSquare = board.getSquare(from_rank, from_file);
        Square toSquare =  board.getSquare(to_rank, to_file);
        toSquare.setPiece(fromSquare.getPiece());
        toSquare.getPiece().moved();
        fromSquare.setPiece(null);
        this.moved();
    }
}
