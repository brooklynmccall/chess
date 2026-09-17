package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    protected boolean isOccupiedBySame(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece) {
        ChessPiece target = board.getPiece(myPosition);
        if (target == null) {
            return false;
        } else if (target.getTeamColor() != myPiece.getTeamColor()) {
            return false;
        }
        return true;
    }

    protected boolean isOccupiedByOther(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece) {
        ChessPiece target = board.getPiece(myPosition);
        if (target == null) {
            return false;
        } else if (target.getTeamColor() == myPiece.getTeamColor()) {
            return false;
        }
        return true;
    }

    protected boolean validatePosition(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece) {
        if (isOnBoard(myPosition) && !isOccupiedBySame(board, myPosition, myPiece)) {
            return true;
        } else return false;
    }

    protected List<ChessPosition> positionsInLine(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece, int rChange, int cChange) {
        List<ChessPosition> positions = new ArrayList<ChessPosition>();
        int oldR = myPosition.getRow();
        int oldC = myPosition.getColumn();
        ChessPosition nextPos = new ChessPosition(oldR + rChange, oldC + cChange);

        while (validatePosition(board, nextPos, myPiece)) {
            positions.add(nextPos);
            if (isOccupiedByOther(board, myPosition, myPiece)) {
                break;
            }
        }
        return positions;
    }

}
