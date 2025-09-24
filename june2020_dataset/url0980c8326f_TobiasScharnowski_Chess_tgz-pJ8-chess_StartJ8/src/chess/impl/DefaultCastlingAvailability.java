package chess.impl;

import chess.CastlingAvailability;
import chess.ChessRules;
import chess.piece.ChessColor;

/**
 * Class representing the castling availabilities per color and
 * kingside/queenside by encapsulating booleans.
 * 
 * @author SCAR023
 * 
 */
public class DefaultCastlingAvailability implements CastlingAvailability {
    private final int CNT_AVAILABILITIES = 4;
    private final int INDICATOR_BLACK = 2;
    private final int INDICATOR_QUEENSIDE = 1;
    boolean[] availabilities;

    /**
     * All availabilities are set to true by default
     */
    public DefaultCastlingAvailability() {
	availabilities = new boolean[CNT_AVAILABILITIES];
	for (int i = 0; i < availabilities.length; ++i) {
	    availabilities[i] = true;
	}
    }

    @Override
    public boolean isCastlingAvailable(boolean kingside, ChessColor color) {
	return availabilities[index(kingside, color)];
    }

    @Override
    public void setCastlingAvailability(boolean kingside, ChessColor color,
	    boolean b) {
	availabilities[index(kingside, color)] = b;
    }

    private int index(boolean kingside, ChessColor color) {
	int index = 0;
	if (!kingside) {
	    index += INDICATOR_QUEENSIDE;
	}
	if (ChessColor.BLACK.equals(color)) {
	    index += INDICATOR_BLACK;
	}
	return index;
    }

    @Override
    public int getCastlingRookFile(boolean kingside, ChessColor color) {
	// IMPROVE Allow arbitrary initial rook positions
	return kingside ? ChessRules.MAX_FILE : ChessRules.MIN_FILE;
    }

}
