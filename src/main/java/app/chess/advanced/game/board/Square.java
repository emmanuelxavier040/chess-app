package app.chess.advanced.game.board;

import lombok.Getter;

public class Square {

    @Getter
    Piece piece;

    String colour;

    public Square(String colour) {
        this.colour = colour;
    }

    public void setPiece(Piece piece) {
        if(piece instanceof Bishop && piece.getColour() != this.colour) {
            throw new RuntimeException("Colour of Bishop do not match colour of Square!");
        }
        this.piece = piece;
    }

}
