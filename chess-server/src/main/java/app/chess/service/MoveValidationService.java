package app.chess.service;


import app.chess.game.action.ChessMove;
import app.chess.game.board.ChessBoard;

public interface MoveValidationService {

    void validateMove(ChessMove move, ChessBoard board);
}
