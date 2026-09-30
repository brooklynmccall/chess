package chess;

import jdk.jshell.spi.ExecutionControl;

public class Rules {
    public MovementRule getRule(ChessPiece.PieceType type) {
        if (type == ChessPiece.PieceType.KING) {
            return new KingRule();
        } else if (type == ChessPiece.PieceType.ROOK) {
            return new RookRule();
        } else if (type == ChessPiece.PieceType.BISHOP) {
            return new BishopRule();
        } else if (type == ChessPiece.PieceType.QUEEN) {
            return new QueenRule();
        } else if (type == ChessPiece.PieceType.KNIGHT) {
            return new KnightRule();
        } else if (type == ChessPiece.PieceType.PAWN) {
            return new PawnRule();
        } else {
            throw new RuntimeException("Not implemented: " + type + " rule.");
        }
    }
}
