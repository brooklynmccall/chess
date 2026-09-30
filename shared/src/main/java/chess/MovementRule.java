package chess;

import java.util.ArrayList;

public interface MovementRule {
    ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessPiece myPiece);
}
