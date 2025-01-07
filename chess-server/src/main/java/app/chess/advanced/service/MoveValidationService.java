package app.chess.advanced.service;


import app.chess.advanced.action.ChessMove;
import app.chess.advanced.game.board.ChessBoard;

public interface MoveValidationService {

    void validateMove(ChessMove move, ChessBoard board);
}
