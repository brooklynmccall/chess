package chess;

public class Rules {
    public MovementRule getRule(ChessPiece myPiece) {
        if (myPiece.getPieceType() == ChessPiece.PieceType.KING) {
            return new KingRule();
        } else if (myPiece.getPieceType() == ChessPiece.PieceType.QUEEN) {
            return new QueenRule();
        } else {
            return null;
        }
    }
}
