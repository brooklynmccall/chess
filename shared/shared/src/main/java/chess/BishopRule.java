package chess;

import java.util.ArrayList;

public class BishopRule extends BaseMovementRule {

    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece) {
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();

        moves.addAll(movesInLine(board, myPosition, myPiece, 1, -1)); // Up left
        moves.addAll(movesInLine(board, myPosition, myPiece, 1, 1)); // Up right
        moves.addAll(movesInLine(board, myPosition, myPiece, -1, 1)); // Down right
        moves.addAll(movesInLine(board, myPosition, myPiece, -1, -1)); // Down left

        return moves;
    }

}
