package app.chess.service;

import app.chess.game.player.ChessPlayer;

import java.io.IOException;
import java.util.*;

public interface MatchPlayersService {
    List<ChessPlayer> matchPlayers() throws IOException;
    
}
