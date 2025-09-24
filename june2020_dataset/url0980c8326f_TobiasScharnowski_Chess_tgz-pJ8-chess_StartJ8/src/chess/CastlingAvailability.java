package chess;

import chess.piece.ChessColor;

public interface CastlingAvailability {
    public boolean isCastlingAvailable(boolean kingside, ChessColor color);

    public void setCastlingAvailability(boolean kingside, ChessColor color,
	    boolean b);

    /**
     * Retrieves the starting file for a Rook piece of the given color and side
     * of the king
     * 
     * @param kingside
     *            King - or queenside. True if kingside.
     * @param color
     *            The color the starting file index is retrieved for.
     * @return The starting file index of the chosen Rook.
     */
    public int getCastlingRookFile(final boolean kingside,
	    final ChessColor color);
}
