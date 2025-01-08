package app.chess.game.state;

import app.chess.game.action.ChessMove;
import app.chess.game.board.*;
import app.chess.game.player.ChessPlayer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static app.chess.game.state.GameConstants.*;

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

    @JsonIgnore
    ChessPlayer currentPlayer;

    @JsonIgnore
    ChessBoard board;

    @JsonIgnore
    List<ChessMove> moves;

    ChessGame(String gameId, Long maxTimeInSeconds, ChessPlayer player1, ChessPlayer player2) {
        this.gameId = gameId;
        this.maxTimeInSeconds = maxTimeInSeconds;
        this.player1 = player1;
        this.player2 = player2;
        initializeBoard();
        this.moves = new ArrayList<>();
    }

    private void printBoard() {
        StringBuilder boardRepresentation = new StringBuilder();
        for (int row = 7; row >= 0; row--) {
            boardRepresentation.append(row + 1).append(" ");
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null) {
                    boardRepresentation.append(piece.getSymbol()).append(" ");
                } else {
                    boardRepresentation.append(". ");
                }
            }
            boardRepresentation.append("\n");
        }

        boardRepresentation.append("  ");
        for (char colLabel = 'a'; colLabel <= 'h'; colLabel++) {
            boardRepresentation.append(colLabel).append(" ");
        }

        System.out.println(boardRepresentation.toString());
    }


    private ChessBoard initializeBoard() {
        board = new ChessBoard();
        board.setPiece(0,0, new Rook(COLOUR_WHITE));
        board.setPiece(0,1, new Knight(COLOUR_WHITE));
        board.setPiece(0,2, new Bishop(COLOUR_WHITE));
        board.setPiece(0,3, new Queen(COLOUR_WHITE));
        board.setPiece(0,4, new King(COLOUR_WHITE));
        board.setPiece(0,5, new Bishop(COLOUR_WHITE));
        board.setPiece(0,6, new Knight(COLOUR_WHITE));
        board.setPiece(0,7, new Rook(COLOUR_WHITE));
        board.setPiece(1,0, new Pawn(COLOUR_WHITE));
        board.setPiece(1,1, new Pawn(COLOUR_WHITE));
        board.setPiece(1,2, new Pawn(COLOUR_WHITE));
        board.setPiece(1,3, new Pawn(COLOUR_WHITE));
        board.setPiece(1,4, new Pawn(COLOUR_WHITE));
        board.setPiece(1,5, new Pawn(COLOUR_WHITE));
        board.setPiece(1,6, new Pawn(COLOUR_WHITE));
        board.setPiece(1,7, new Pawn(COLOUR_WHITE));

        board.setPiece(7,0, new Rook(COLOUR_BLACK));
        board.setPiece(7,1, new Knight(COLOUR_BLACK));
        board.setPiece(7,2, new Bishop(COLOUR_BLACK));
        board.setPiece(7,3, new Queen(COLOUR_BLACK));
        board.setPiece(7,4, new King(COLOUR_BLACK));
        board.setPiece(7,5, new Bishop(COLOUR_BLACK));
        board.setPiece(7,6, new Knight(COLOUR_BLACK));
        board.setPiece(7,7, new Rook(COLOUR_BLACK));
        board.setPiece(6,0, new Pawn(COLOUR_BLACK));
        board.setPiece(6,1, new Pawn(COLOUR_BLACK));
        board.setPiece(6,2, new Pawn(COLOUR_BLACK));
        board.setPiece(6,3, new Pawn(COLOUR_BLACK));
        board.setPiece(6,4, new Pawn(COLOUR_BLACK));
        board.setPiece(6,5, new Pawn(COLOUR_BLACK));
        board.setPiece(6,6, new Pawn(COLOUR_BLACK));
        board.setPiece(6,7, new Pawn(COLOUR_BLACK));
        return board;
    }



    public void processChessMove(ChessMove move) throws InvalidMoveException {
        int[] from = getSquare(move.getFrom());
        int[] to = getSquare(move.getTo());
        Square fromSquare = board.getSquare(from[0], from[1]);
        Square toSquare =  board.getSquare(to[0], to[1]);
        verifyMovedPiece(fromSquare, toSquare);

        fromSquare.getPiece().move(board, from, to);
        moves.add(move);
        printBoard();
        verifyMoveInviteCheck();
        verifyStalemate();
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

    private int[] getSquare(String squareLocation) throws InvalidMoveException {
        boolean invalidSquare = squareLocation.length() != 2;
        invalidSquare = invalidSquare || !squareLocation.matches("[a-h][1-8]");
        if(invalidSquare)
            throw new InvalidMoveException("Invalid from square!");
        return new int[]{squareLocation.charAt(1) - 49, squareLocation.charAt(0) - 'a'};
    }

    private void verifyMovedPiece(Square fromSquare, Square toSquare) throws InvalidMoveException {
        Piece pieceMoved = fromSquare.getPiece();

        if (pieceMoved == null)
            throw new InvalidMoveException("From Square is empty!");

        if(!pieceMoved.getColour().contentEquals(currentPlayer.getColour())) {
            throw new InvalidMoveException("Did not move own piece!");
        }

        if(toSquare.getPiece() != null && toSquare.getPiece().getColour().contentEquals(pieceMoved.getColour())) {
            throw new InvalidMoveException("Cannot capture own piece!");
        }
    }


    private void verifyMoveInviteCheck() { }

    private void verifyStalemate() { }

    private boolean isUnderCheck() {
        // Move a piece and then see if it is still check.
        return false;
    }

    private void verifyCheckMate() { }



}
