package chess.move.impl;

import chess.ChessCoord;
import chess.Position;
import chess.impl.Coords;

/**
 * Class representing a Move where a king is castled.
 * 
 * @author Tobias
 */
public class CastlingMove extends SimpleMove {
    private ChessCoord rookSource;
    private ChessCoord rookDest = null;

    public CastlingMove(ChessCoord source, ChessCoord target, ChessCoord rookSrc) {
	super(source, target);
    }

    @Override
    public void doMove(Position pos) {
	rookDest = rookDest();
	this.moveFromTo(pos, rookSource, rookDest);
	super.doMove(pos);
    }

    @Override
    public void undoMove(Position pos) {
	this.moveFromTo(pos, rookDest, rookSource);
	super.undoMove(pos);
    }

    public ChessCoord getRookSource() {
	return this.rookSource;
    }

    private boolean isKingside() {
	return getSourceCoord().getFile() < getTargetCoord().getFile();
    }

    /**
     * Calculates the rook destination coordinate depending on the rook and king
     * source coords.
     * 
     * @return Destination of the rook while doing the castling move.
     */
    private ChessCoord rookDest() {
	final int rookFileRelativeToKingTarget = isKingside() ? -1 : 1;
	ChessCoord rookDest = Coords
		.coord(this.getTargetCoord().getFile()
			+ rookFileRelativeToKingTarget, this.getSourceCoord()
			.getRank());
	return rookDest;
    }
}
