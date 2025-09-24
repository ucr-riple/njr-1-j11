package chess.move.impl;

import chess.ChessCoord;

/**
 * Move in which a piece is taken normally without en passant being involved.
 * 
 * @author Tobias
 * 
 */
/*
 * Here we have to take care about removing and returning the taken piece over
 * the functionality SimpleMove provides.
 */
public class SimpleTakingMove extends AbstractTakingMove {

	public SimpleTakingMove(ChessCoord source, ChessCoord target) {
		super(source, target);
	}

	@Override
	protected ChessCoord getTakenPieceCoord() {
		return this.getTargetCoord();
	}

}
