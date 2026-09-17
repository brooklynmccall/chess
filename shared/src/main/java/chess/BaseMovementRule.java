package chess;

import java.util.Collection;

public abstract class BaseMovementRule implements MovementRule {

    public abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece);

    /**
    private Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece) {
        return List.of();
    } */

    protected boolean isOnBoard(ChessPosition myPosition) {
        return (
            myPosition.getRow() >= 1
            && myPosition.getRow() <= 8
            && myPosition.getColumn() >= 1
            && myPosition.getColumn() <= 8
        );
    }

    protected boolean isOccupiedByTeam(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece) {
        ChessPiece target = board.getPiece(myPosition);
        if (target == null) {
            return false;
        } else if (target.getTeamColor() != myPiece.getTeamColor()) {
            return false;
        }
        return true;
    }
}
