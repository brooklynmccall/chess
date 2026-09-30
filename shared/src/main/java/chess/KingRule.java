package chess;

import java.util.ArrayList;

public class KingRule extends BaseMovementRule {

    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece) {
        ArrayList<ChessPosition> positions = new ArrayList<>();
        ArrayList<ChessMove> moves = new ArrayList<>();
        int r = myPosition.getRow();
        int c = myPosition.getColumn();

        positions.add(new ChessPosition(r, c-1)); // Left
        positions.add(new ChessPosition(r+1, c-1)); // Up left
        positions.add(new ChessPosition(r+1, c)); // Up
        positions.add(new ChessPosition(r+1, c+1)); // Up right
        positions.add(new ChessPosition(r, c+1)); // Right
        positions.add(new ChessPosition(r-1, c+1)); // Down right
        positions.add(new ChessPosition(r-1, c)); // Down
        positions.add(new ChessPosition(r-1, c-1)); // Down left

        for (ChessPosition position : positions) {
            if(validatePosition(board, position, myPiece)) {
                moves.add(new ChessMove(myPosition, position, null));
            }
        }

        return moves;

    }

}
