package app.chess.advanced.game.board;

import lombok.Getter;
import lombok.Setter;

public class Square {

    @Getter
    @Setter
    Piece piece;

    String colour;

    public Square(String colour) {
        this.colour = colour;
    }


}
