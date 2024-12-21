package app.chess.beginner.game;


import app.chess.beginner.model.GameState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;


@Service
public class ClockService {

    List<GameState> clockSubscribers;
    private ObjectMapper objectMapper;

    public ClockService(List<GameState> clockSubscribers, ObjectMapper objectMapper) {
        this.clockSubscribers = clockSubscribers;
        this.objectMapper = objectMapper;
    }

    public void subscribeToClock(GameState gameState) {
        gameState.getPlayer1().setStartTime(LocalTime.now());
        gameState.getPlayer2().setStartTime(LocalTime.now());
        clockSubscribers.add(gameState);
    }

    @Scheduled(fixedRate = 1000)
    public void updateClock() throws IOException {
        for(GameState gameState : clockSubscribers) {
            LocalTime startTime = gameState.getCurrentPlayer().getStartTime();
            LocalTime currentTime = LocalTime.now();
            Long difference = Duration.between(startTime, currentTime).toMillis();
            gameState.getCurrentPlayer().setMillisecondsLeft(difference);
            String gameStateJson = objectMapper.writeValueAsString(gameState);
            TextMessage textMessage = new TextMessage(gameStateJson);
            gameState.getPlayer1().getSession().sendMessage(textMessage);
            gameState.getPlayer2().getSession().sendMessage(textMessage);
        }
    }



}
