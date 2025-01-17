package app.chess.game.state;

import app.chess.game.player.BlitzChessPlayer;
import app.chess.game.player.ChessPlayer;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GameFactoryImpl implements GameFactory {
    @Override
    public ChessGame createGame(ChessPlayer player1, ChessPlayer player2) {
        String gameId = UUID.randomUUID().toString();
        ChessGame chessGame = createState(gameId, player1, player2);
//        chessGame.setGameId("123");
        chessGame.setCurrentPlayer(player1);
        chessGame.setCurrentPlayerId(player1.getId());

        chessGame.setStatus(GameConstants.GAME_STATUS_START);
        player1.setColour(GameConstants.COLOUR_WHITE);
        player2.setColour(GameConstants.COLOUR_BLACK);
        return chessGame;
    }

    protected ChessGame createState(String gameId, ChessPlayer player1, ChessPlayer player2) {

        if(player1 instanceof BlitzChessPlayer) {
            ChessGame chessGame = new BlitzChessGame(gameId, player1, player2);
            player1.setSecondsLeft(chessGame.getMaxTimeInSeconds());
            player2.setSecondsLeft(chessGame.getMaxTimeInSeconds());
            return chessGame;
        } else {
            throw new RuntimeException("Class not supported for game creation!");
        }
    }


}
