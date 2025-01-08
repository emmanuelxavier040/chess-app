package app.chess.game.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;


@Setter
@Getter
public abstract class ChessPlayer {

    String id;
    String name;
    String colour;
    Long secondsLeft;

    @JsonIgnore
    LocalTime startTime;



}
