package app.chess.advanced.game.state;

import app.chess.advanced.action.ChessMove;
import app.chess.advanced.game.board.*;
import app.chess.advanced.game.player.ChessPlayer;
import ch.qos.logback.core.joran.sanity.Pair;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public abstract class ChessGame {
    String gameId;
    String status;
    String gameType;
    Long maxTimeInSeconds;
    ChessPlayer player1;
    ChessPlayer player2;
    String currentPlayerId;
    List<ChessMove> moves;

    ChessGame(String gameId, Long maxTimeInSeconds, ChessPlayer player1, ChessPlayer player2) {
        this.gameId = gameId;
        this.maxTimeInSeconds = maxTimeInSeconds;
        this.player1 = player1;
        this.player2 = player2;
        this.moves = new ArrayList<>();
    }

    @JsonIgnore
    ChessBoard board;

    @JsonIgnore
    ChessPlayer currentPlayer;

    public void processChessMove(ChessMove move) {
        doMove(move);
        moves.add(move);
        switchPlayer();
    }


    private void switchPlayer() {
        if(!player1.getId().contentEquals(getCurrentPlayerId())) {
            setCurrentPlayer(player1);
            setCurrentPlayerId(player1.getId());

        } else {
            setCurrentPlayer(getPlayer2());
            setCurrentPlayerId(getPlayer2().getId());
        }
    }

    private void doMove(ChessMove move) {
        int[] from = getSquare(move.getFrom());
        int[] to = getSquare(move.getTo());
        Square fromSquare = board.getSquare(from[0], from[1]);
        Square toSquare =  board.getSquare(to[0], to[1]);
        doMove(fromSquare, toSquare);

    }

    private int[] getSquare(String squarelocation) {
        boolean invalidSquare = squarelocation.length() != 2;
        invalidSquare = invalidSquare || !squarelocation.matches("[a-h][1-8]");
        if(invalidSquare)
            throw new RuntimeException("Invalid from square!");
        return new int[]{'a'- squarelocation.charAt(0), squarelocation.charAt(0)};
    }

    private void doMove(Square fromSquare, Square toSquare) {
        Piece pieceMoved = fromSquare.getPiece();
        if (pieceMoved == null)
            throw new RuntimeException("From Square is empty!");

        if(toSquare.getPiece() != null) {
            throw new RuntimeException("To Square is not empty!");
        }

        if(pieceMoved instanceof King) {
            moveKing(fromSquare, toSquare);
        } else if(pieceMoved instanceof Queen) {
            moveQueen(fromSquare, toSquare);
        } else if(pieceMoved instanceof Rook) {
            moveRook(fromSquare, toSquare);
        } else if(pieceMoved instanceof Bishop) {
            moveBishop(fromSquare, toSquare);
        } else if(pieceMoved instanceof Knight) {
            moveKnight(fromSquare, toSquare);
        } else if(pieceMoved instanceof Pawn) {
            movePawn(fromSquare, toSquare);
        }
    }


    private boolean isUnderCheck() {
        return false;
    }

    private boolean isCheckMate() {
        return false;
    }

    private void moveKing(Square fromSquare, Square toSquare) {

    }

    private void moveQueen(Square fromSquare, Square toSquare) {

    }

    private void moveRook(Square fromSquare, Square toSquare) {

    }


    private void moveBishop(Square fromSquare, Square toSquare) {

    }


    private void moveKnight(Square fromSquare, Square toSquare) {

    }


    private void movePawn(Square fromSquare, Square toSquare) {

    }

}
