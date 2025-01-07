package app.chess.beginner.model;


import app.chess.socket.Data;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Move extends Data {

    private String moveId;
    private String message;

    @Override
    public String toString() {
        return "Move{" +
                "moveId='" + moveId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
