package chess;

import java.util.ArrayList;

public interface MovementRule {
    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece);
}
