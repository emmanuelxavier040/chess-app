package app.chess.socket;

import app.chess.advanced.action.PlayerAction;
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
