package app.chess.service;

import app.chess.game.player.BlitzChessPlayer;
import app.chess.game.player.ChessPlayer;
import app.chess.game.player.PlayerStateManager;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MatchPlayersBlitzPlayers implements MatchPlayersService {
    private final Random random = new Random();
    private final PlayerStateManager playerStateManager;

    public MatchPlayersBlitzPlayers(PlayerStateManager playerStateManager) {
        this.playerStateManager = playerStateManager;
    }

    @Override
    public List<ChessPlayer>  matchPlayers() {
        return getTwoRandomPlayers(playerStateManager.getWaitingPlayerSessions());
    }


    private synchronized List<ChessPlayer> getTwoRandomPlayers(Map<String, ChessPlayer> map) {
        Set<String> waitingPlayerSessions =  map.entrySet()
                .stream()
                .filter(entry -> entry.getValue() instanceof BlitzChessPlayer)
                .map(entry -> entry.getKey())
                .collect(Collectors.toSet());
        List<String> waitingPlayerSessionsList = new ArrayList<>(waitingPlayerSessions);

        if (waitingPlayerSessionsList.size() < 2) {
            return Collections.emptyList();
        }

        String player1SessionId = waitingPlayerSessionsList.get(random.nextInt(waitingPlayerSessionsList.size()));
        ChessPlayer player1= map.get(player1SessionId);
        waitingPlayerSessionsList.remove(player1SessionId);
        map.remove(player1SessionId);

        String player2SessionId = waitingPlayerSessionsList.get(random.nextInt(waitingPlayerSessionsList.size()));
        ChessPlayer player2 = map.get(player2SessionId);
        waitingPlayerSessionsList.remove(player2SessionId);
        map.remove(player2SessionId);
        return Arrays.asList(player1, player2);
    }
}
