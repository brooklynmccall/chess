package chess;

import java.util.ArrayList;

public class KnightRule extends BaseMovementRule {

    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece) {
        ArrayList<ChessPosition> positions = new ArrayList<ChessPosition>();
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
        int r = myPosition.getRow();
        int c = myPosition.getColumn();

        positions.add(new ChessPosition(r-1, c-2)); // Left down
        positions.add(new ChessPosition(r+1, c-2)); // Left up
        positions.add(new ChessPosition(r+2, c-1)); // Up left
        positions.add(new ChessPosition(r+2, c+1)); // Up right
        positions.add(new ChessPosition(r+1, c+2)); // Right up
        positions.add(new ChessPosition(r-1, c+2)); // Right down
        positions.add(new ChessPosition(r-2, c+1)); // Down right
        positions.add(new ChessPosition(r-2, c-1)); // Down left

        for (ChessPosition position : positions) {
            if(validatePosition(board, position, myPiece)) {
                moves.add(new ChessMove(myPosition, position, null));
            }
        }

        return moves;

    }

}
