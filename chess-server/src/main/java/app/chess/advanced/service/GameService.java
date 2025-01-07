package app.chess.advanced.service;

import app.chess.advanced.action.ChessMove;
import app.chess.advanced.game.state.ChessGame;

import java.io.IOException;

public interface GameService {

    void initializeGame() throws IOException;

    void processMove(ChessMove move) throws IOException;

    void endGame(ChessGame gameState) throws IOException;

    void processNewGameRequest(String playerId, PlayerNotifier playerNotifier) throws IOException;

    void processEndGameRequest(String playerId);

}
