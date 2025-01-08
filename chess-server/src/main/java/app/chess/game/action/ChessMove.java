package app.chess.game.action;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChessMove extends PlayerAction {
    String from;
    String to;
    Long timeTaken;
}
