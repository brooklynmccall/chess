package chess;

import java.util.ArrayList;

public class RookRule extends BaseMovementRule {

    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        moves.addAll(movesInLine(board, myPosition, myPiece, 0, -1)); // Left
        moves.addAll(movesInLine(board, myPosition, myPiece, 1, 0)); // Up
        moves.addAll(movesInLine(board, myPosition, myPiece, 0, 1)); // Right
        moves.addAll(movesInLine(board, myPosition, myPiece, -1, 0)); // Down

        return moves;
    }

}