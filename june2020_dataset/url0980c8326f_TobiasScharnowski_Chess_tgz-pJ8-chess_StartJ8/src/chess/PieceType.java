package chess;

public enum PieceType {
    PAWN('P', false), ROOK('R', true), KNIGHT('N', true), BISHOP('B', true), QUEEN(
	    'Q', true), KING('K', false);

    /*
     * The number of promotable pieces in total.
     */
    private static final int NUM_PROMOTABLE = calcNumPromotable();

    /**
     * Flag point out whether this piece can be the result of a pawn promotion.
     */
    private boolean promotable;

    private char shortCut;

    private PieceType(final char shortCut, boolean isPromotable) {
	this.promotable = isPromotable;
	this.shortCut = shortCut;
    }

    public boolean isPromotable() {
	return this.promotable;
    }

    public static final int numPromotable() {
	return NUM_PROMOTABLE;
    }

    public static PieceType getTypeByShortCut(final char shortCut) {
	for (PieceType pt : PieceType.values()) {
	    if (pt.shortCut == shortCut) {
		return pt;
	    }
	}
	return PAWN;
    }

    /**
     * Private helper function to count the number of promotable pieces
     * initially.
     * 
     * @return
     */
    private static int calcNumPromotable() {
	int n = 0;
	for (PieceType pt : PieceType.values()) {
	    if (pt.isPromotable()) {
		++n;
	    }
	}
	return n;
    }

    public char getShortCut() {
	return shortCut;
    }
}
