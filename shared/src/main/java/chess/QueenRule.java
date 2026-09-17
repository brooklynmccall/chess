package chess;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class QueenRule extends BaseMovementRule {

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece) {
        List<ChessMove> moves = new ArrayList<ChessMove>();
        List<ChessPosition> positions = new ArrayList<ChessPosition>();
        int r = myPosition.getRow();
        int c = myPosition.getColumn();

        positions.addAll(positionsInLine(board, myPosition, myPiece, 0, -1)); // left
        positions.addAll(positionsInLine(board, myPosition, myPiece, -1, -1)); // up left
        positions.addAll(positionsInLine(board, myPosition, myPiece, -1, 0)); // up
        positions.addAll(positionsInLine(board, myPosition, myPiece, -1, 1)); // up right
        positions.addAll(positionsInLine(board, myPosition, myPiece, 0, 1)); // right
        positions.addAll(positionsInLine(board, myPosition, myPiece, 1, 1)); // down right
        positions.addAll(positionsInLine(board, myPosition, myPiece, 1, 0)); // down
        positions.addAll(positionsInLine(board, myPosition, myPiece, 1, -1)); // down left

        for (ChessPosition position : positions) {
            moves.add(new ChessMove(myPosition, position, null));
        }
        return moves;

    }
}
