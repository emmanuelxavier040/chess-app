package app.chess.game.board;

import app.chess.game.state.InvalidMoveException;

import static app.chess.game.state.GameConstants.*;

public class Pawn extends Piece {

    public Pawn(String colour) {
        super(colour);
    }

    @Override
    public void move(ChessBoard board, int[] from , int[] to) throws InvalidMoveException {

        Square fromSquare = board.getSquare(from[0], from[1]);
        Piece movedPiece = fromSquare.getPiece();
        String movedPawnColour = movedPiece.getColour();

        if(isHittingAPiece(board, from[0], from[1], to[0], to[1])) {
            throw new InvalidMoveException("Cannot move a pawn through non-empty squares.");
        }

//        Only valid moves
//        0. One step forward for the first move
//        1. Two steps forward for the first move
//        2. One step forward for non-first moves
//        0. the above moves to empty square
//        3. Capture sideways opposite colour piece
//        4. Move that do not introduce check
//        5. En passant
//        6. Promotion

        boolean invalidMove = false;
        // Checking the move direction based on pawn colour
        int direction = movedPiece.getColour().equals(COLOUR_WHITE)? 1 : -1;
        invalidMove = (to[0] - from[0] != direction) && (to[0] - from[0] != 2 * direction) && !movedPiece.isEverMoved();
        invalidMove = invalidMove || (movedPiece.isEverMoved() && (to[0] - from[0] != direction));

        if (invalidMove) {
            // The first movement can be 1 or 2 squares forward.
            // From the second movement onwards, a pawn can move only 1 square forward.
            throw new InvalidMoveException(INVALID_MOVE);
        }

        // TODO: implement enpasaant

        if(from[1] == to[1] && board.getPiece(to[0], to[1]) != null) { // When moved forward on the  same file
            throw new InvalidMoveException("Cannot move forward to a non-empty square!");
        }

        if(Math.abs(to[1] - from[1]) == 1 && board.getPiece(to[0], to[1]) == null) {
            // A sideways capture should move the pawn 1 square to any of the adjacent files.
            throw new InvalidMoveException("Sideways movement should not be to an empty square!");
        }


        Square toSquare =  board.getSquare(to[0], to[1]);
        toSquare.setPiece(fromSquare.getPiece());
        toSquare.getPiece().moved();
        fromSquare.setPiece(null);

        if((movedPawnColour.equals(COLOUR_WHITE) && from[0] == 6) || (movedPawnColour.equals(COLOUR_BLACK) && from[0] == 2)) {
            // Default promotion to Queen
            toSquare.setPiece(new Queen(this.getColour()));
        }
        this.moved();
    }
}
