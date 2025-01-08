package app.chess.game.action;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * An abstraction for the action of a player
 *
 * @author Emmanuel Xavier
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ChessMove.class, name = "ChessMove")
})
@Getter
@Setter
public abstract class PlayerAction {

    String playerId;
    String gameId;

}
