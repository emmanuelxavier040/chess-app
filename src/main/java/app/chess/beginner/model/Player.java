package app.chess.beginner.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalTime;

@Getter
@Setter
public class Player {
    String id;
    String name;
    String colour;

    @JsonIgnore
    LocalTime startTime;

    Long millisecondsLeft;

    @JsonIgnore
    WebSocketSession session;

}
