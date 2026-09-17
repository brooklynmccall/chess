package chess;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class KnightRule extends BaseMovementRule {

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece) {
        List<ChessMove> moves = new ArrayList<ChessMove>();
        List<ChessPosition> positions = new ArrayList<ChessPosition>();
        int r = myPosition.getRow();
        int c = myPosition.getColumn();

        ChessPosition leftDown = new ChessPosition(r+1,c-2);
        ChessPosition leftUp = new ChessPosition(r-1,c-2);
        ChessPosition upLeft = new ChessPosition(r-2,c-1);
        ChessPosition upRight = new ChessPosition(r-2,c+1);
        ChessPosition rightUp = new ChessPosition(r-1,c+2);
        ChessPosition rightDown = new ChessPosition(r+1,c+2);
        ChessPosition downRight = new ChessPosition(r+2,c+1);
        ChessPosition downLeft = new ChessPosition(r+2,c-1);

        positions.add(leftDown);
        positions.add(leftUp);
        positions.add(upLeft);
        positions.add(upRight);
        positions.add(rightUp);
        positions.add(rightDown);
        positions.add(downRight);
        positions.add(downLeft);

        for (ChessPosition position : positions) {
            if (validatePosition(board, position, myPiece)) {
                moves.add(new ChessMove(myPosition, position, null));
            }
        }
        return moves;

    }

}
