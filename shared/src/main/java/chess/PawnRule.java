package chess;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class PawnRule extends BaseMovementRule {

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece) {
        List<ChessMove> moves = new ArrayList<>();
        List<ChessPosition> positions = new ArrayList<>();
        ChessGame.TeamColor color = myPiece.getTeamColor();
        int r = myPosition.getRow();
        int c = myPosition.getColumn();

        // If White
        if (color == ChessGame.TeamColor.WHITE) {
            ChessPosition down = new ChessPosition(r+1, c);
            ChessPosition downLeft = new ChessPosition(r+1, c-1);
            ChessPosition downRight = new ChessPosition(r+1, c+1);

            if (isEmpty(board, down, myPiece)) {
                positions.add(new ChessPosition(r+1, c));
                // If in row 2 and spot in front is empty give option to advance 2
                if (r==2) {
                    ChessPosition downTwo = new ChessPosition(r+2, c);
                    if (isEmpty(board, downTwo, myPiece)) {
                        positions.add(downTwo);
                    }
                }
            }

            // If BLACK at diagonal give option to advance diagonally
            if (isOccupiedByOther(board, downLeft, myPiece)) {
                positions.add(downLeft);
            }
            if (isOccupiedByOther(board, downRight, myPiece)) {
                positions.add(downRight);
            }
        }

        // If Black
        if (color == ChessGame.TeamColor.BLACK) {
            ChessPosition up = new ChessPosition(r-1, c);
            ChessPosition upLeft = new ChessPosition(r-1, c-1);
            ChessPosition upRight = new ChessPosition(r-1, c+1);

            if (isEmpty(board, up, myPiece)) {
                positions.add(new ChessPosition(r-1, c));
                // If in row 7 and spot in front is empty give option to advance 2
                if (r==7) {
                    ChessPosition upTwo = new ChessPosition(r-2, c);
                    if (isEmpty(board, upTwo, myPiece)) {
                        positions.add(upTwo);
                    }
                }
            }

            // If WHITE at diagonal give option to advance diagonally
            if (isOccupiedByOther(board, upLeft, myPiece)) {
                positions.add(upLeft);
            }
            if (isOccupiedByOther(board, upRight, myPiece)) {
                positions.add(upRight);
            }
        }

        for (ChessPosition position : positions) {
            int newR = position.getRow();
            if (isOnBoard(position)) {
                if (color == ChessGame.TeamColor.WHITE && newR == 8) { // If White and in newPos is in row 8 give option to promote
                    moves.add(new ChessMove(myPosition, position, ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, position, ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, position, ChessPiece.PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, position, ChessPiece.PieceType.ROOK));
                } else if (color == ChessGame.TeamColor.BLACK && newR == 1) { // If Black and in newPos is in row 1 give option to promote
                    moves.add(new ChessMove(myPosition, position, ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, position, ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, position, ChessPiece.PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, position, ChessPiece.PieceType.ROOK));
                } else {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        }
        return moves;

    }

    private boolean isEmpty(ChessBoard board, ChessPosition position, ChessPiece myPiece) {
        return (!(isOccupiedBySame(board, position, myPiece) ||
                isOccupiedByOther(board, position, myPiece)));
    }

}
