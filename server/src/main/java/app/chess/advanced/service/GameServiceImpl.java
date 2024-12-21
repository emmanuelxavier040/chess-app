package app.chess.advanced.service;


import app.chess.advanced.action.ChessMove;
import app.chess.advanced.game.player.PlayerFactory;
import app.chess.advanced.game.player.PlayerStateManager;
import app.chess.advanced.game.state.ChessGame;
import app.chess.advanced.game.state.GameManager;
import app.chess.advanced.game.state.GameFactory;
import app.chess.advanced.game.player.ChessPlayer;
import app.chess.advanced.game.state.InvalidMoveException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalTime;

@AllArgsConstructor
@Component
public class GameServiceImpl implements GameService {

    private GameManager gameManager;
    private GameFactory gameFactory;
    private MatchPlayersService matchPlayersService;
    private PlayerFactory playerFactory;
    private PlayerStateManager playerStateManager;

    @Override
    @Scheduled(fixedRate = 100)
    public void initializeGame() throws IOException {
        var matchedPlayers = matchPlayersService.matchPlayers();
        if(matchedPlayers.isEmpty()) {
            return;
        }
        ChessPlayer player1 = matchedPlayers.get(0);
        ChessPlayer player2 = matchedPlayers.get(1);
        ChessGame chessGame = gameFactory.createGame(player1, player2);
        gameManager.addGameState(chessGame);
        player1.setStartTime(LocalTime.now());
        player1.notifyGameStart(chessGame);
        player2.setStartTime(LocalTime.now());
        player2.notifyGameStart(chessGame);
    }

    @Override
    public void processMove(ChessMove move) throws IOException {
        ChessGame game = gameManager.getGame(move.getGameId());

        boolean invalid = false;
        invalid = invalid || (game == null);
        invalid = invalid || (move.getPlayerId() == null);
        invalid = invalid || !move.getPlayerId().contentEquals(game.getCurrentPlayerId());

        if(invalid) {
            return;
        }
        try {
            game.processChessMove(move);
            game.getCurrentPlayer().notifyMove(move);
            game.getCurrentPlayer().notifyGameEvent(game);
        } catch (InvalidMoveException e) {
            game.getCurrentPlayer().notifyError(e.getMessage());
        }

    }


    @Override
    public void endGame(ChessGame gameState) throws IOException {
        String gameId = gameState.getGameId();
        gameManager.removeGameState(gameId);
        gameState.getPlayer1().notifyGameEnd(gameState);
        gameState.getPlayer2().notifyGameEnd(gameState);
    }

    @Override
    public void processNewGameRequest(String playerId, PlayerNotifier playerNotifier) throws IOException {
        ChessPlayer player = playerFactory.createPlayer();
        player.setPlayerNotifier(playerNotifier);
        player.setId(playerId);
        playerStateManager.addPlayerToWaitingList(player);
        player.notifyToWait();
    }

    @Override
    public void processEndGameRequest(String playerId) {
        playerStateManager.removeWaitingPlayer(playerId);
    }
}
