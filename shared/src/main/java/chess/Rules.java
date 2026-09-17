package chess;

public class Rules {
    public MovementRule getRule(ChessPiece myPiece) {
        if (myPiece.getPieceType() == ChessPiece.PieceType.KING) {
            return new KingRule();
        } else if (myPiece.getPieceType() == ChessPiece.PieceType.QUEEN) {
            return new QueenRule();
        } else if (myPiece.getPieceType() == ChessPiece.PieceType.BISHOP) {
            return new BishopRule();
        } else if (myPiece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
            return new KnightRule();
        } else if (myPiece.getPieceType() == ChessPiece.PieceType.ROOK) {
            return new RookRule();
        }
        return null;
    }
}
