package app.chess.advanced.game.board;

import app.chess.advanced.game.state.InvalidMoveException;
import lombok.Getter;

@Getter
public abstract class Piece {
    String colour;

    boolean isEverMoved;

    Piece(String colour) {
        this.colour = colour;
        isEverMoved = false;
    }

    public void moved() {
        isEverMoved = true;
    }

    public abstract void move(ChessBoard board, int[] from , int[] to) throws InvalidMoveException;

    /**
     * This method checks by default the movements in a file or a rank.
     * @param board
     * @param from
     * @param to
     * @return If any piece is found between the from square and the to square, true is returned
     */
    public boolean isHittingAPiece(ChessBoard board, int[] from, int[] to) {

        if(from[0] == to[0]) {
            // movement on the same rank
            from[1]++;
            to[1]--;
            while(from[1] <= to[1]) {
                if(board.getPiece(from[1], to[1]) != null) return true;
                from[1]++;
            }
            return false;
        }

        if(from[1] == to[1]) {
            // movement on the same file
            from[0]++;
            to[0]--;
            while(from[0] <= to[0]) {
                if(board.getPiece(from[0], to[0]) != null) return true;
                from[0]++;
            }
            return false;
        }

        return false;
    }
}
