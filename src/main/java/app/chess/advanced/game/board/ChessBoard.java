package app.chess.advanced.game.board;

import lombok.Getter;

@Getter
public class ChessBoard {

    private Square[][] squares;

    public ChessBoard() {
        squares = new Square[8][8];
    }

    public Square getSquare(int row, int column) {
        return squares[row][column];
    }

    public Square setSquare(Square square, int row, int column) {
        return squares[row][column];
    }

}
