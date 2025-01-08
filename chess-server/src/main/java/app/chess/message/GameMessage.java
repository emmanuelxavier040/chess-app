package app.chess.message;

import app.chess.game.action.PlayerAction;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GameMessage extends BaseMessage {

    String type;

    Data data;

    PlayerAction action;

    String sessionId;
}
