package app.chess.advanced.service;

import app.chess.advanced.game.state.ChessGame;
import app.chess.advanced.game.state.GameManager;
import app.chess.advanced.game.player.ChessPlayer;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;


@Component
@AllArgsConstructor
public class GameClockServiceImpl implements GameClockService {

    private GameManager gameManager;
    private GameService gameService;
    private NotificationService notificationService;

    @Override
    @Scheduled(fixedRate = 1000)
    public void updateClock() throws IOException {
        var activeGameIds = gameManager.getGameIds();
        for(String gameStateId : activeGameIds) {
            ChessGame gameState = gameManager.getGame(gameStateId);

            if(!notificationService.isOnline(gameState.getPlayer1().getId()) ||
                    !notificationService.isOnline(gameState.getPlayer2().getId()) ) {
                gameManager.removeGameState(gameStateId);
                gameService.endGame(gameState);
            }
            ChessPlayer currentPlayer = gameState.getCurrentPlayer();
            LocalTime startTime = currentPlayer.getStartTime();
            LocalTime currentTime = LocalTime.now();
            Long difference = Duration.between(startTime, currentTime).toSeconds();
            Long remainingSeconds = gameState.getMaxTimeInSeconds() - difference;
            currentPlayer.setSecondsLeft(remainingSeconds);
            if(remainingSeconds <= 0) {
                gameService.endGame(gameState);
            }

        }
    }

}
