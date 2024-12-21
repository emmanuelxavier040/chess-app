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

    public Square getSquare(int row, int column) {
        return squares[row][column];
    }

    public Piece getPiece(int row, int column) {
        return squares[row][column].getPiece();
    }

    public void setPiece(int row, int column, Piece piece) {
        squares[row][column].setPiece(piece);
    }

}
