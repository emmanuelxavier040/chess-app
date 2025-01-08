package app.chess.game.board;

import app.chess.game.state.InvalidMoveException;

public class King extends Piece {

    public King(String colour) {
        super(colour);
    }

    @Override
    public void move(ChessBoard board, int[] from , int[] to) throws InvalidMoveException {

        /**
         * - If King or Rook ever moved, casting is invalid
         * - If King and Rook never moved, castling through non-empty squares is invalid
         * - If King and Rook never moved, castling through check is invalid
         * - Long and short castling
         * - One step move
         * - Move cannot be to check
         */


        int fromRank = from[0];
        int fromFile = from[1];
        int toRank = to[0];
        int toFile = to[1];

//        is Long Castling?
        int rankDirection = (toRank - fromRank) == 0? 0 : (toRank - fromRank) > 0 ? 1 : -1;
        int fileDirection = (toFile - fromFile) == 0? 0: (toFile - fromFile) > 0 ? 1 : -1;

        if(rankDirection == fileDirection && fileDirection == 0) {
            throw new InvalidMoveException("Invalid move");
        }

        // Short Castle
        if(toFile - fromFile == 2 && rankDirection == 0) {
            if(this.isEverMoved) throw new InvalidMoveException("Invalid move, the king can move only one step at a time.");

            if(board.getPiece(fromRank, toFile) != null || board.getPiece(fromRank, toFile - 1) != null) {
                throw new InvalidMoveException("Cannot castle through non-empty squares.");
            }

            Piece rook = board.getPiece(fromRank, toFile + 1);
            if(rook == null || rook.isEverMoved) throw new InvalidMoveException("Invalid castle, the rook has moved atleast once.");

            board.setPiece(toRank, toFile, board.getPiece(fromRank, fromFile));
            board.setPiece(toRank, toFile-1, board.getPiece(fromRank, toFile + 1));
            board.setPiece(fromRank, fromFile, null);
            board.setPiece(fromRank, toFile + 1, null);
        }

        this.moved();
    }
}
