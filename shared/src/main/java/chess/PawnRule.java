package chess;

import java.util.ArrayList;

public class PawnRule extends BaseMovementRule {

    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece) {
        ArrayList<ChessPosition> positions = new ArrayList<>();
        ArrayList<ChessMove> moves = new ArrayList<>();
        int r = myPosition.getRow();
        int c = myPosition.getColumn();
        ChessGame.TeamColor color = myPiece.getTeamColor();

        if (color == ChessGame.TeamColor.WHITE) {
            ChessPosition up = new ChessPosition(r+1, c);
            if (isEmpty(board, up)) {
                positions.add(up);

                ChessPosition twoUp = new ChessPosition(r+2, c);
                if(r==2 && isEmpty(board, twoUp)) {
                    positions.add(twoUp);
                }
            }

            ChessPosition upLeft = new ChessPosition(r+1, c-1);
            ChessPosition upRight = new ChessPosition(r+1, c+1);
            if (isOnBoard(upLeft) && isOccupiedByOther(board, upLeft, myPiece)) {
                positions.add(upLeft);
            }
            if (isOnBoard(upRight) && isOccupiedByOther(board, upRight, myPiece)) {
                positions.add(upRight);
            }
        }

        if (color == ChessGame.TeamColor.BLACK) {
            ChessPosition down = new ChessPosition(r-1, c);
            if (isEmpty(board, down)) {
                positions.add(down);

                ChessPosition twoDown = new ChessPosition(r-2, c);
                if(r==7 && isEmpty(board, twoDown)) {
                    positions.add(twoDown);
                }
            }

            ChessPosition downLeft = new ChessPosition(r-1, c-1);
            ChessPosition downRight = new ChessPosition(r-1, c+1);
            if (isOnBoard(downLeft) && isOccupiedByOther(board, downLeft, myPiece)) {
                positions.add(downLeft);
            }
            if (isOnBoard(downRight) && isOccupiedByOther(board, downRight, myPiece)) {
                positions.add(downRight);
            }
        }

        for (ChessPosition position : positions) {
            if (validatePosition(board, position, myPiece)) {
                if ((position.getRow() == 8 && color == ChessGame.TeamColor.WHITE) ||
                        (position.getRow() == 1 && color == ChessGame.TeamColor.BLACK)) {
                    moves.add(new ChessMove(myPosition, position, ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, position, ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, position, ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, position, ChessPiece.PieceType.KNIGHT));
                } else {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        }

        return moves;
    }

    private boolean isEmpty(ChessBoard board, ChessPosition position) {
        return board.getPiece(position) == null;
    }
}
