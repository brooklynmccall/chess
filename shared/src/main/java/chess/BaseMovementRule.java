package chess;
import java.util.ArrayList;

public abstract class BaseMovementRule implements MovementRule{
    public abstract ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece);

    protected boolean isOnBoard(ChessPosition position) {
        int r = position.getRow();
        int c = position.getColumn();

        return r >= 1 && r <= 8 && c >= 1 && c <= 8;
    }

    protected boolean isOccupiedBySame(ChessBoard board, ChessPosition position, ChessPiece myPiece) {
        ChessGame.TeamColor color = myPiece.getTeamColor();

        ChessPiece other = board.getPiece(position);

        return other != null && other.getTeamColor() == color;
    }

    protected boolean isOccupiedByOther(ChessBoard board, ChessPosition position, ChessPiece myPiece) {
        ChessGame.TeamColor color = myPiece.getTeamColor();

        ChessPiece other = board.getPiece(position);

        return other != null && other.getTeamColor() != color;
    }

    protected boolean validatePosition(ChessBoard board, ChessPosition position, ChessPiece myPiece) {
        return isOnBoard(position) && !isOccupiedBySame(board, position, myPiece);
    }

    protected ArrayList<ChessMove> movesInLine(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece, int rDif, int cDif) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int newR = myPosition.getRow() + rDif;
        int newC = myPosition.getColumn() + cDif;
        ChessPosition newPosition = new ChessPosition(newR, newC);

        while(validatePosition(board, newPosition, myPiece)) {
            moves.add(new ChessMove(myPosition, newPosition, null));
            if (isOccupiedByOther(board, newPosition, myPiece)) {
                break;
            }

            newR += rDif;
            newC += cDif;
            newPosition = new ChessPosition(newR, newC);
        }
        return moves;
    }
}
