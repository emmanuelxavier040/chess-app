package app.chess.advanced.game.player;


import org.springframework.stereotype.Component;

@Component
public class BlitzPlayerFactory implements PlayerFactory {


    @Override
    public ChessPlayer createPlayer() {
        return new BlitzChessPlayer();
    }
}
