package app.chess.game.board;

import app.chess.game.state.InvalidMoveException;

public class Knight extends Piece {

    public Knight(String colour) {
        super(colour);
    }

    @Override
    public void move(ChessBoard board, int[] from , int[] to) throws InvalidMoveException {

        int from_rank = from[0];
        int from_file = from[1];
        int to_rank = to[0];
        int to_file = to[1];

        boolean validMove = (from_rank + 2 == to_rank) && (from_file + 1 == to_file);
        validMove = validMove || (from_rank + 2 == to_rank) && (from_file - 1 == to_file);
        validMove = validMove || (from_rank - 2 == to_rank) && (from_file + 1 == to_file);
        validMove = validMove || (from_rank - 2 == to_rank) && (from_file - 1 == to_file);
        validMove = validMove || (from_rank + 1 == to_rank) && (from_file + 2 == to_file);
        validMove = validMove || (from_rank + 1 == to_rank) && (from_file - 2 == to_file);
        validMove = validMove || (from_rank - 1 == to_rank) && (from_file + 2 == to_file);
        validMove = validMove || (from_rank - 1 == to_rank) && (from_file - 2 == to_file);

        if(!validMove) {
            throw new InvalidMoveException("A Knight must be moved along an L shape from the current square.");
        }

        Square fromSquare = board.getSquare(from[0], from[1]);
        Square toSquare =  board.getSquare(to[0], to[1]);
        toSquare.setPiece(fromSquare.getPiece());
        toSquare.getPiece().moved();
        fromSquare.setPiece(null);
    }
}
