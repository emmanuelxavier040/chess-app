package app.chess.game.state;

public class InvalidMoveException extends Exception {

    public InvalidMoveException(String message) {
        super(message);
    }
}
