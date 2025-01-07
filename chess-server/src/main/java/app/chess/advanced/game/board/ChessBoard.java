package app.chess.advanced.game.board;

import lombok.Getter;

import static app.chess.beginner.model.GameConstants.COLOUR_BLACK;
import static app.chess.beginner.model.GameConstants.COLOUR_WHITE;

@Getter
public class ChessBoard {

    private Square[][] squares;

    public ChessBoard() {
        squares = new Square[8][8];
        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                squares[row][col] = new Square((row + col)%2 == 0? COLOUR_BLACK: COLOUR_WHITE);
            }
        }
    }

    public Square getSquare(int rank, int file) {
        return squares[rank][file];
    }

    public Piece getPiece(int rank, int file) {
        return squares[rank][file].getPiece();
    }

    public void setPiece(int rank, int file, Piece piece) {
        squares[rank][file].setPiece(piece);
    }

    public boolean isKingOnCheck(String colour) {
//        TODO:
        return false;
    }

}
