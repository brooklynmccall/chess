package chess;

public class Rules {
    public MovementRule getRule(ChessPiece myPiece) {
        if (myPiece.getPieceType() == ChessPiece.PieceType.KING) {
            return new KingRule();
        } else {
            return new KingRule();
        }
    }
}
