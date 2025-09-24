package chess.move.impl;

import chess.ChessCoord;
import chess.Position;
import chess.piece.Piece;

/**
 * Abstract class providing the default functionality for moves that involve
 * taking pieces.
 * 
 * @author Tobias
 * 
 */
/*
 * 
 */
public abstract class AbstractTakingMove extends SimpleMove {
	private Piece takenPiece = null;

	public AbstractTakingMove(ChessCoord source, ChessCoord target) {
		super(source, target);
	}

	/**
	 * Method determining the Coordinate on which the taken piece is positioned.
	 * This may either be the move's target coordinate in case of a normal
	 * taking move or the position next to a pawn in case of an en passant
	 * taking move.
	 * 
	 * @return The {@link ChessCoord coordinate} the piece that is being taken
	 *         is positioned.
	 */
	abstract protected ChessCoord getTakenPieceCoord();

	@Override
	public void doMove(Position pos) {
		this.setTakenPiece(pos.getPieceAt(getTakenPieceCoord()));
		// Set the taken piece inactive
		enableTakenPiece(false);
		super.doMove(pos);
	}

	@Override
	public void undoMove(Position pos) {
		super.undoMove(pos);
		pos.setPieceAt(getTakenPiece(), getTakenPieceCoord());
		// Set the taken piece back active
		enableTakenPiece(true);
	}

	public Piece getTakenPiece() {
		return this.takenPiece;
	}

	public void setTakenPiece(Piece takenPiece) {
		this.takenPiece = takenPiece;
	}

	/**
	 * Wrapper method to set the move's assigned taken piece active in the
	 * process of doing or undoing this taking move.
	 * <p>
	 * There may be no taken piece assigned to this move in case of a non-taking
	 * {@link PawnPromotionMove}. This leads to nothing being done.
	 * 
	 * @param active
	 *            Indicates whether the piece is set to active. If this is
	 *            false, the piece is taken to inactive.
	 */
	private void enableTakenPiece(final boolean active) {
		// There can be no piece at the position in case of PawnPromotionMoves
		if (getTakenPiece() != null) {
			getTakenPiece().setActive(active);
		}
	}
}
