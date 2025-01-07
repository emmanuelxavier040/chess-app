package app.chess.socket;


import app.chess.beginner.model.GameState;
import app.chess.advanced.game.state.ChessGame;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = GameState.class, name = "GameState"),
        @JsonSubTypes.Type(value = ChessGame.class, name = "ChessGame")

})
public abstract class Data {

}
