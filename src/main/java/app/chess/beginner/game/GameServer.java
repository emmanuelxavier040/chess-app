package app.chess.beginner.game;

import app.chess.beginner.model.GameConstants;
import app.chess.beginner.model.GameState;
import app.chess.beginner.model.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;


@Component
public class GameServer {

    private Map<String, Player> waitingPlayerSessions;
    private Map<String, GameState> games;
    private ObjectMapper objectMapper;
    private PlayerService playerService;
    private MatchingService matchingService;
    private ClockService clockService;

    public GameServer(PlayerService playerService,
                      MatchingService matchingService,
                      ClockService clockService,
                      Map<String, Player> waitingPlayerSessions,
                      ObjectMapper objectMapper,
                      Map<String, GameState> games) {
        this.playerService = playerService;
        this.matchingService = matchingService;
        this.clockService = clockService;
        this.waitingPlayerSessions = waitingPlayerSessions;
        this.objectMapper = objectMapper;
        this.games = games;
    }



    public void initialize(WebSocketSession playerSession) throws IOException {
        Player player = playerService.initializePlayer(playerSession);
        addPlayerToWaitingPool(player);
        playerSession.sendMessage(new TextMessage(playerSession.getId()));
    }


     void addPlayerToWaitingPool(Player player) {
        waitingPlayerSessions.put(player.getId(), player);
    }

    @Scheduled(fixedRate = 2000)
    public void makeMatch() throws IOException {
        List<Player> matchedPlayers = matchingService.makeMatch(waitingPlayerSessions);
        if(matchedPlayers.isEmpty()) {
            return;
        }
        Player player1 = matchedPlayers.get(0);
        Player player2 = matchedPlayers.get(1);
        GameState gameState = createGame(player1, player2);
        clockService.subscribeToClock(gameState);
        String gameJson = objectMapper.writeValueAsString(gameState);
        TextMessage gameMessage = new TextMessage(gameJson);
        player1.getSession().sendMessage(gameMessage);
        player2.getSession().sendMessage(gameMessage);

    }


    public void removeWaitingPlayer(String sessionId) {
        waitingPlayerSessions.remove(sessionId);
    }



    public String generateGameId() {
        return UUID.randomUUID().toString();
    }

    public GameState createGame(Player player1, Player player2) {
        GameState game = new GameState();
        String gameId = generateGameId();
        game.setGameId(gameId);
        game.setStatus(GameConstants.GAME_STATUS_START);
        player1.setColour(GameConstants.PLAYER_COLOUR_BLACK);
        game.setPlayer1(player1);
        player2.setColour(GameConstants.PLAYER_COLOUR_WHITE);
        game.setCurrentPlayer(player2);
        game.setPlayer2(player2);
        games.put(gameId, game);
        return game;
    }


}
