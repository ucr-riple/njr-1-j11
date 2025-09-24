package chess;

import java.util.List;

import chess.move.Move;
import chess.piece.ChessColor;
import chess.piece.Piece;

/**
 * Interface representing the state of a chess board. It holds information about
 * the piece's coordinates and other position details like move counters or
 * castling availabilities.
 * 
 * @author Tobias
 * 
 */
public interface Position {
	// Commented that out for the time being for the lack of a use case
	// public Position getCopy();

	public Piece getPieceAt(ChessCoord coord);

	public Piece getPieceAt(final int file, final int rank);

	/**
	 * Sets the {@code piece} to the specified coordinate and updates the
	 * piece's internal coordinate as well. Setting coordinates is only
	 * triggered from within the position implementations, not the
	 * {@link chess.piece.Piece} itself.
	 * 
	 * @param piece
	 *            The piece which goes to the coordinate.
	 * @param coord
	 *            The coordinate the piece is set to.
	 */
	public void setPieceAt(final Piece piece, final ChessCoord coord);

	public int getEnpassantFile();

	public void setEnpassantFile(final int enpassantFile);

	/**
	 * Retreives the number of halve moves that were done since the last pawn
	 * advance or taking of a piece took place. The cap is 50, then the game is
	 * a draw.
	 * 
	 * @return Number of halve moves since the last pawn advance/taking of a
	 *         piece.
	 */
	public int getHalveMoveClock();

	/**
	 * Can be triggered by a move to indicate another move was done that
	 * qualifies for advancing the halve move clock
	 */
	public void advanceHalveMoveClock();

	public void revertHalveMoveClock();

	public boolean isCastlingAvailable(final boolean kingside,
			final ChessColor color);

	public void setCastlingAvailable(final boolean kingside,
			final ChessColor color, boolean b);

	public int getMoveNumber();

	public void advanceMoveNumber();

	public void revertMoveNumber();

	public ChessColor getActiveColor();

	public void flipActiveColor();

	/**
	 * Add a piece to the {@code Position}. It is assigned to the corresponding
	 * color's list of pieces and is associated to the piece's
	 * {@link chess.ChessCoord ChessCoord} as well.
	 * 
	 * @param piece
	 *            The piece to be added
	 */
	public void addPiece(final Piece piece);

	/**
	 * Remove the piece from the {@code Position}. It is removed from the
	 * corresponding color's list of pieces and removed from the associated
	 * field as well.
	 * <p>
	 * This is intended to be used for promoted pieces that have to be removed
	 * from the piece lists when the move is undone.
	 * 
	 * @param piece
	 *            The piece to be removed.
	 */
	public void removePiece(final Piece piece);

	// IMPROVE This does not support multiple "kingside rooks". We might do that
	// more genericly
	public int getCastlingRookFile(final boolean kingside,
			final ChessColor color);

	public ChessCoord activeKingCoord();

	/**
	 * Checks whether target coordinate is controlled by the current opponent.
	 * This can be used to figure out whether the king is being in check or
	 * moves into one at the moment.
	 * 
	 * @param coord
	 *            The coordinate for which the attack should be checked
	 * @return True in case the coordinate is being controlled by the opponent
	 */
	public boolean isControlled(ChessCoord coord);

	/**
	 * Processes the move for the position. The move is added to the Position's
	 * Move List, is executed and afterwards checked for legality because of
	 * possible checks. The result of those checks is returned.
	 * 
	 * @param move
	 *            The move to be executed in the position.
	 * @return True in case the {@link chess.Move Move} was actually legal and
	 *         was done in the position. Otherwise the move is reverted and
	 *         false is returned.
	 */
	public boolean processMove(final Move move);

	public void undoLastMove();

	public List<Move> possibleMoves();

	public List<Move> possibleMovesToCoordForType(PieceType pt,
			ChessCoord targetCoord);
}
