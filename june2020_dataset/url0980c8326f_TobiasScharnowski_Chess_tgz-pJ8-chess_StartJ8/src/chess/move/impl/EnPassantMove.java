package chess.move.impl;

import chess.ChessCoord;
import chess.impl.Coords;

/**
 * Move representing an en passant move.
 * 
 * @author Tobias
 * 
 */
/*
 * En Passant in essence is a taking move with the only difference that the
 * taken pawn is not in the position where the moving pawn is going to. That is
 * why we only have to adjust the coordinate for the taken piece.
 */
public class EnPassantMove extends AbstractTakingMove {

    public EnPassantMove(final ChessCoord source, final ChessCoord target) {
	super(source, target);
    }

    @Override
    protected ChessCoord getTakenPieceCoord() {
	return Coords.coord(this.getTargetCoord().getFile(), this
		.getSourceCoord().getRank());
    }

}
