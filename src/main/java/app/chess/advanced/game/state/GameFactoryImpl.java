package app.chess.advanced.game.state;

import app.chess.advanced.game.board.ChessBoard;
import app.chess.advanced.game.player.BlitzChessPlayer;
import app.chess.advanced.game.player.ChessPlayer;
import app.chess.beginner.model.GameConstants;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GameFactoryImpl implements GameFactory {
    @Override
    public ChessGame createGame(ChessPlayer player1, ChessPlayer player2) {
        String gameId = UUID.randomUUID().toString();
        ChessGame chessGame = createState(gameId, player1, player2);
        chessGame.setGameId("123");
        chessGame.setBoard(new ChessBoard());
        chessGame.setCurrentPlayer(player1);
        chessGame.setCurrentPlayerId(player1.getId());

        chessGame.setStatus(GameConstants.GAME_STATUS_START);
        player1.setColour(GameConstants.PLAYER_COLOUR_WHITE);
        player2.setColour(GameConstants.PLAYER_COLOUR_BLACK);
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
