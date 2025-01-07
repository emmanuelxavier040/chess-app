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
    private NotificationService notificationService;

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
        notificationService.notifyGameStart(chessGame);
        player2.setStartTime(LocalTime.now());
        notificationService.notifyGameStart(chessGame);
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
            notificationService.notifyMove(game.getCurrentPlayerId(), move);
            notificationService.notifyGameEvent(game);
        } catch (InvalidMoveException e) {
            notificationService.notifyError(move.getPlayerId(), e.getMessage());
        }

    }


    @Override
    public void endGame(ChessGame gameState) throws IOException {
        String gameId = gameState.getGameId();
        gameManager.removeGameState(gameId);
        notificationService.notifyGameEnd(gameState);
        notificationService.notifyGameEnd(gameState);
        notificationService.removeNotifier(gameState.getPlayer1().getId());
        notificationService.removeNotifier(gameState.getPlayer2().getId());
    }

    @Override
    public void processNewGameRequest(String playerId, PlayerNotifier playerNotifier) throws IOException {
        ChessPlayer player = playerFactory.createPlayer();
        player.setId(playerId);
        playerStateManager.addPlayerToWaitingList(player);
        notificationService.registerNotifier(playerId, playerNotifier);
        notificationService.notifyToWait(playerId);
    }

    @Override
    public void processEndGameRequest(String playerId) {
        playerStateManager.removeWaitingPlayer(playerId);
    }
}
