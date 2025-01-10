package app.chess.game.board;

import app.chess.game.state.InvalidMoveException;
import lombok.Getter;

import static app.chess.game.state.GameConstants.COLOUR_WHITE;

@Getter
public abstract class Piece {
    String colour;

    boolean isEverMoved;

    Piece(String colour) {
        this.colour = colour;
        isEverMoved = false;
    }

    public String getSymbol() {
        switch (this.getClass().getSimpleName().toLowerCase()) {
            case "pawn": return colour.equals(COLOUR_WHITE) ? "♙" : "♟";
            case "rook": return colour.equals(COLOUR_WHITE) ? "♖" : "♜";
            case "knight": return equals(COLOUR_WHITE) ? "♘" : "♞";
            case "bishop": return equals(COLOUR_WHITE) ? "♗" : "♝";
            case "queen": return equals(COLOUR_WHITE) ? "♕" : "♛";
            case "king": return equals(COLOUR_WHITE) ? "♔" : "♚";
            default: return "?";
        }
    }

    public void moved() {
        isEverMoved = true;
    }

    public abstract void move(ChessBoard board, int[] from , int[] to) throws InvalidMoveException;

    /**
     * This method checks the movements in a file or a rank.
     * @param board
     * @param
     * @return If any piece is found between the from square and the to square, true is returned,
     *          else false is returned
     */
    public boolean isHittingAPiece(ChessBoard board, int from_rank, int from_file, int to_rank, int to_file) {


        int rankDirection = (to_rank - from_rank) == 0? 0 : (to_rank - from_rank) > 0 ? 1 : -1;
        int fileDirection = (to_file - from_file) == 0? 0: (to_file - from_file) > 0 ? 1 : -1;

        from_rank = from_rank + rankDirection;
        from_file = from_file + fileDirection;

        while(!(from_rank == to_rank && from_file == to_file)) {
            if(board.getPiece(from_rank, from_file) != null)
                return true;

            from_rank = from_rank + rankDirection;
            from_file = from_file + fileDirection;
        }
        return false;

    }

}
