package chess.piece;

public enum ChessColor {
    WHITE, BLACK;
    
    public static ChessColor oppositeColor(ChessColor color) {
	return color.equals(WHITE)?BLACK:WHITE;
    }
}
