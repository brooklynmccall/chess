package chess;

import java.util.Collection;
import java.util.List;

public class KingRule extends BaseMovementRule {

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece) {
        return List.of();
    }

}
