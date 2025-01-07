package app.chess.beginner.model;


import app.chess.socket.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GameState extends Data {

    String gameId;

    String status;

    String gameType;

    Long maxTime;

    @JsonIgnore
    Board board;

    Player player1;

    Player player2;

    @JsonIgnore
    Player currentPlayer;

}
