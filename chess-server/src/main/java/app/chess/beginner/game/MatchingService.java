package app.chess.beginner.game;

import app.chess.beginner.model.Player;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MatchingService {

    private Random random = new Random();


    public List<Player> makeMatch(Map<String, Player> waitingPlayerSessions) {
        List<Player> matchedPlayers = getTwoRandomPlayers(waitingPlayerSessions);
        return matchedPlayers;
    }


    private synchronized List<Player> getTwoRandomPlayers(Map<String, Player> map) {

        Set<String> waitingPlayerSessions = map.keySet();
        List<String> waitingPlayerSessionsList = new ArrayList<>(waitingPlayerSessions);

        if (waitingPlayerSessionsList.size() < 2) {
            return Collections.emptyList();
        }

        String player1SessionId = waitingPlayerSessionsList.get(random.nextInt(waitingPlayerSessionsList.size()));
        Player player1= map.get(player1SessionId);
        waitingPlayerSessionsList.remove(player1SessionId);
        map.remove(player1SessionId);

        String player2SessionId = waitingPlayerSessionsList.get(random.nextInt(waitingPlayerSessionsList.size()));
        Player player2= map.get(player2SessionId);
        waitingPlayerSessionsList.remove(player2SessionId);
        map.remove(player2SessionId);
        return Arrays.asList(player1, player2);
    }
}
