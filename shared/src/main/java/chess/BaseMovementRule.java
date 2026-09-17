package chess;

import java.util.Collection;

public abstract class BaseMovementRule implements MovementRule {

    public abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece);

}
