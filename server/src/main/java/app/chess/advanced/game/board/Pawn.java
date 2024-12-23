package app.chess.advanced.game.board;

import app.chess.advanced.game.state.InvalidMoveException;

import static app.chess.beginner.model.GameConstants.*;
import static app.chess.beginner.model.GameConstants.INVALID_MOVE;

public class Pawn extends Piece {

    public Pawn(String colour) {
        super(colour);
    }

    @Override
    public void move(ChessBoard board, int[] from , int[] to) throws InvalidMoveException {

        Square fromSquare = board.getSquare(from[0], from[1]);
        Piece movedPiece = fromSquare.getPiece();
        String movedPawnColour = movedPiece.getColour();

        if(isHittingAPiece(board, from, to)) {
            throw new InvalidMoveException("Cannot move a pawn through non-empty squares.");
        }

        boolean invalidMove = false;
        // Checking the move direction based on pawn colour
        int direction = movedPiece.equals(COLOUR_WHITE)? 1 : -1;
        invalidMove = to[0] - from[0] == direction * 2 && movedPiece.isEverMoved();
        invalidMove = invalidMove || (movedPiece.isEverMoved() && (to[0] - from[0] != direction));
        if (invalidMove) {
            // The first movement can be 1 or 2 squares forward.
            // From the second movement onwards, a pawn can move only 1 square forward.
            throw new InvalidMoveException(INVALID_MOVE);
        }
        // TODO: implement enpasaant

        if(board.getPiece(to[0], to[1]) != null) {
            throw new InvalidMoveException("Cannot move forward to a non-empty square!");
        }
        if(!(Math.abs(to[1] - from[1]) == 1 || to[1] == from[1])) {
            // A sideways capture should move the pawn 1 square to any of the adjacent files.
            // Or the pawn should be in the same file.
            throw new InvalidMoveException("Invalid movement of pawn!");
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
