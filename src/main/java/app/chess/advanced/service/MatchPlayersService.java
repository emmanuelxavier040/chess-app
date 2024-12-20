package app.chess.advanced.service;

import app.chess.advanced.game.player.ChessPlayer;

import java.io.IOException;
import java.util.*;

public interface MatchPlayersService {
    List<ChessPlayer> matchPlayers() throws IOException;
    
}
