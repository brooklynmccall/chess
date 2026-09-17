package chess;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class KingRule extends BaseMovementRule {

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece) {
        List<ChessMove> moves = new ArrayList<ChessMove>();
        List<ChessPosition> positions = new ArrayList<ChessPosition>();
        int r = myPosition.getRow();
        int c = myPosition.getColumn();

        ChessPosition left = new ChessPosition(r,c-1);
        ChessPosition upLeft = new ChessPosition(r-1,c-1);
        ChessPosition up = new ChessPosition(r-1,c);
        ChessPosition upRight = new ChessPosition(r-1,c+1);
        ChessPosition right = new ChessPosition(r,c+1);
        ChessPosition downRight = new ChessPosition(r+1,c+1);
        ChessPosition down = new ChessPosition(r+1,c);
        ChessPosition downLeft = new ChessPosition(r+1,c-1);
        positions.add(left);
        positions.add(upLeft);
        positions.add(up);
        positions.add(upRight);
        positions.add(right);
        positions.add(downRight);
        positions.add(down);
        positions.add(downLeft);

        for (ChessPosition position : positions) {
            if (validatePosition(board, myPosition, myPiece)) {
                moves.add(new ChessMove(myPosition, position, null));
            }
        }
        return moves;

    }

}
