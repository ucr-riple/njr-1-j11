package chess;

import chess.impl.Coords;
import chess.piece.ChessColor;

/**
 * A Class holding constants and static methods to consolidate some rules of the
 * default chess game.
 * <p>
 * This has to be refactored at some point to fully support different versions
 * of chess rule sets and non-default starting positions.
 * 
 * @author Tobias
 * 
 */
public class ChessRules {
    public static final int ENPASSANT_FILE_DISABLED = -1;
    // IMPROVE We can at some point move that into the coordinate
    // implementations to support non-square chess boards
    public static final int MAX_FILE = 7;
    public static final int MIN_FILE = 0;
    public static final int MAX_RANK = 7;
    public static final int MIN_RANK = 0;

    public static int baseRank(final ChessColor color) {
	return ChessColor.WHITE.equals(color) ? MIN_RANK : MAX_RANK;
    }

    // IMPROVE We should move that into a "RuleService" class at some point
    /**
     * Checks whether the given {@code file} index is within the allowed bounds.
     * 
     * @param file
     *            The file index to be checked.
     * @return True in case {@code file} is within bounds.
     */
    public static boolean fileWithinBounds(final int file) {
	return file >= ChessRules.MIN_FILE && file <= ChessRules.MAX_FILE;
    }

    /**
     * Checks whether the given {@code rank} index is within the allowed bounds.
     * 
     * @param file
     *            The rank index to be checked.
     * @return True in case {@code rank} is within bounds.
     */
    public static boolean rankWithinBounds(final int rank) {
	return rank >= ChessRules.MIN_RANK && rank <= ChessRules.MAX_RANK;
    }

    public static boolean withinBounds(final ChessCoord coord) {
	return fileWithinBounds(coord.getFile())
		&& rankWithinBounds(coord.getRank());
    }

    public static ChessCoord getCastlingTargetCoord(final boolean kingside,
	    final ChessColor color) {
	final int file = kingside ? ChessRules.MAX_FILE - 1
		: ChessRules.MIN_FILE + 2;
	final int rank = ChessColor.WHITE.equals(color) ? ChessRules.MIN_RANK
		: ChessRules.MAX_RANK;
	return Coords.coord(file, rank);
    }
}
