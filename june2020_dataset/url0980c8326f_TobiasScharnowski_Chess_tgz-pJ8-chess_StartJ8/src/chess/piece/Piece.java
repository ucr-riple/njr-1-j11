package chess.piece;

import java.util.List;

import chess.ChessCoord;
import chess.PieceType;
import chess.Position;
import chess.move.Move;

/**
 * Interface representing a chess piece. It bundles information about the type
 * of piece, its assigned color, current coordinate on the field and other
 * environmental information.
 *
 * @author Tobias
 *
 */
/*
 * TODO Taken Piece: Maybe we need a mechanism to indicate that the piece is no
 * longer active because it is taken. This might be needed when we keep a list
 * of all pieces in a Position and we cannot know whether the piece is actually
 * on the coordinate or not. We can also do this by checking for consistency
 * between the piece's coordinate and the position's piece at the coordinate.
 */
public interface Piece {
	public ChessColor getColor();

	public PieceType getType();

	// TODO Add more position related information to leverage that knowledge for
	// easier move creation (ie. not creating moves when the king is in double
	// check, a Piece is pinned and so on)
	/**
	 * Generates a {@link java.util.List List} of pseudo possible moves not
	 * taking into account whether the position resulting from the move actually
	 * leaves the king in check.
	 *
	 * @return A {@link java.util.List List} of pseudo possible moves.
	 */
	public List<Move> getPossibleMoves();

	public List<Move> generatePossibleMovesToCoord(ChessCoord destCoord);

	public ChessCoord getCoord();

	/**
	 * Sets the current coordinate the piece is on. This should only be called
	 * by the Position/Move class to keep things consistent. The other class is
	 * going to take care of keeping the piece's coordinate up to date
	 *
	 * @param coord
	 *            Coordinate to set to.
	 */
	void setCoord(ChessCoord coord);

	public Position getPos();

	public void incrMoveCnt();

	public void decrMoveCnt();

	public int getMoveCnt();

	public boolean isActive();

	public void setActive(boolean active);

	/**
	 * Checks whether the piece currently controls the field at the passed
	 * coordinate.
	 *
	 * @param coord
	 *            The {@link chess.ChessCoord Coordinate} that is checked to be
	 *            controlled.
	 * @return True, in case this piece currently controls the passed field.
	 */
	public boolean controls(final ChessCoord coord);

	/**
	 * Checks whether the piece can currently move to {@code coord} using a
	 * normal move. Castling is not checked here.
	 *
	 * @param coord
	 *            The coordinate to move to.
	 * @return True, if the piece can currently move to the passed location. If
	 *         not, false is returned.
	 */
	public boolean canMoveTo(final ChessCoord coord);

	public char shortCut();
}
