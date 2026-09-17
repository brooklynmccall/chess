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
        positions.add(left);
        ChessPosition right = new ChessPosition(r,c+1);
        positions.add(right);
        ChessPosition up = new ChessPosition(r-1,c);
        positions.add(up);
        ChessPosition down = new ChessPosition(r+1,c);
        positions.add(down);

        for (ChessPosition position : positions) {
            if (isOnBoard(position) && !isOccupiedByTeam(board, position, myPiece)) {
                moves.add(new ChessMove(myPosition, position, null));
            }
        }

        return moves;
    }

}
