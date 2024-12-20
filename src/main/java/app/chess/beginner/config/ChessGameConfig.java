package app.chess.beginner.config;

import app.chess.beginner.model.GameState;
import app.chess.beginner.model.Player;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableScheduling
public class ChessGameConfig {

    @Bean
    Map<String, Player> getWaitingPlayerSessions() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    Map<String, GameState> getActiveGames() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    List<GameState> getClockSubscribers() {
        return Collections.synchronizedList(new ArrayList<>());
    }


}
