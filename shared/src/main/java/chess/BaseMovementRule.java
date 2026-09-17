package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseMovementRule implements MovementRule {

    public abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece);

    protected boolean isOnBoard(ChessPosition newPosition) {
        return (
            newPosition.getRow() >= 1
            && newPosition.getRow() <= 8
            && newPosition.getColumn() >= 1
            && newPosition.getColumn() <= 8
        );
    }

    protected boolean isOccupiedBySame(ChessBoard board, ChessPosition newPosition, ChessPiece myPiece) {
        if (isOnBoard(newPosition)) {
            ChessPiece target = board.getPiece(newPosition);
            if (target == null) {
                return false;
            } else if (target.getTeamColor() != myPiece.getTeamColor()) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    protected boolean isOccupiedByOther(ChessBoard board, ChessPosition newPosition, ChessPiece myPiece) {
        if (isOnBoard(newPosition)) {
            ChessPiece target = board.getPiece(newPosition);
            if (target == null) {
                return false;
            } else if (target.getTeamColor() == myPiece.getTeamColor()) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    protected boolean validatePosition(ChessBoard board, ChessPosition newPosition, ChessPiece myPiece) {
        if (isOnBoard(newPosition) && !isOccupiedBySame(board, newPosition, myPiece)) {
            return true;
        } else return false;
    }

    protected List<ChessPosition> positionsInLine(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece, int rChange, int cChange) {
        List<ChessPosition> positions = new ArrayList<>();
        int oldR = myPosition.getRow();
        int oldC = myPosition.getColumn();
        ChessPosition nextPos = new ChessPosition(oldR + rChange, oldC + cChange);

        while (validatePosition(board, nextPos, myPiece)) {
            positions.add(nextPos);
            if (isOccupiedByOther(board, nextPos, myPiece)) {
                return positions;
            }
            oldR = nextPos.getRow();
            oldC = nextPos.getColumn();
            nextPos = new ChessPosition(oldR + rChange, oldC + cChange);
        }
        return positions;
    }

}
