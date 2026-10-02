package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard board;
    private TeamColor turn;

    public ChessGame() {
        board = new ChessBoard();
        board.resetBoard();
        turn = TeamColor.WHITE;

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn;
    }

    /**
     * Sets which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets all valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece current = board.getPiece(startPosition);
        TeamColor color = current.getTeamColor();
        Set<ChessMove> allMoves = new HashSet<>(current.pieceMoves(board, startPosition));
        Set<ChessMove> validMoves = new HashSet<>();
        ChessPosition kingPos = getKingPos(color);

        for (ChessMove move : allMoves) {
            ChessBoard newBoard = new ChessBoard(board);
            ChessPosition endPos = move.getEndPosition();
            if (move.getPromotionPiece() != null) {
                current = new ChessPiece(color, move.getPromotionPiece());
            }

            newBoard.addPiece(endPos, current);
            newBoard.addPiece(startPosition, null);

            if (current.getPieceType() == ChessPiece.PieceType.KING) {
                kingPos = endPos;
            }

            if (!boardInCheck(color, newBoard, kingPos)) {
                validMoves.add(move);
            }
        }

        return validMoves;
    }

    /**
     * Gets all moves for a team
     *
     * @param teamColor the team to get moves for
     * @param board the board to get moves for
     * @return Set of moves for requested team, or null if none
     */
    private static Set<ChessMove> allMoves(TeamColor teamColor, ChessBoard board) {
        Set<ChessMove> moves = new HashSet<>();
        for (int r=1; r<=8; r++) {
            for (int c=1; c<=8; c++) {
                ChessPosition currentPos = new ChessPosition(r, c);
                ChessPiece current = board.getPiece(currentPos);
                if (current != null && current.getTeamColor() == teamColor) {
                    moves.addAll(current.pieceMoves(board, currentPos));
                }
            }
        }
        return moves;
    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPos = move.getStartPosition();
        ChessPosition endPos = move.getEndPosition();
        ChessPiece current = board.getPiece(startPos);
        if (current == null) throw new InvalidMoveException("No piece to move.");

        TeamColor color = current.getTeamColor();
        if (color != turn) throw new InvalidMoveException("Not your turn.");

        Set<ChessMove> moves = new HashSet<>(validMoves(startPos));
        if (moves.contains(move)) {
            if (move.getPromotionPiece() != null) {
                current = new ChessPiece(color, move.getPromotionPiece());
            }

            board.addPiece(endPos, current);
            board.addPiece(startPos, null);

            turn = (color == TeamColor.WHITE)? TeamColor.BLACK : TeamColor.WHITE;
        } else throw new InvalidMoveException("Not a valid move.");

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPos = getKingPos(teamColor);
        return boardInCheck(teamColor, board, kingPos);
    }

    /**
     * Determines if the given team is in check in given board
     *
     * @param teamColor which team to check for check
     * @param board which board to check for check
     * @return True if the specified team is in check
     */
    private static boolean boardInCheck(TeamColor teamColor, ChessBoard board, ChessPosition kingPos) {
        TeamColor enemyColor = (teamColor == TeamColor.WHITE)? TeamColor.BLACK : TeamColor.WHITE;
        Set<ChessMove> enemyMoves = allMoves(enemyColor, board);

        for (ChessMove move : enemyMoves) {
            ChessPosition endPos = move.getEndPosition();
            if (endPos.equals(kingPos)) {
                return true;
            }
        }
        return false;

    }

    /**
     * Returns location of king of given color
     *
     * @param teamColor which color king to find
     * @return position of king
     */
    private ChessPosition getKingPos (TeamColor teamColor) {
        ChessPosition kingPos;

        for (int c=1; c<=8; c++) {
            for (int r=1; r<=8; r++) {
                ChessPosition currentPos = new ChessPosition(r, c);
                ChessPiece current = board.getPiece(currentPos);
                if (current != null &&
                        current.getTeamColor() == teamColor &&
                        current.getPieceType() == ChessPiece.PieceType.KING) {
                    kingPos = currentPos;
                    return kingPos;
                }
            }
        }

        throw new RuntimeException(teamColor + "king not found.");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard to a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(board, chessGame.board) && turn == chessGame.turn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, turn);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "board=" + board +
                ", turn=" + turn +
                '}';
    }
}
