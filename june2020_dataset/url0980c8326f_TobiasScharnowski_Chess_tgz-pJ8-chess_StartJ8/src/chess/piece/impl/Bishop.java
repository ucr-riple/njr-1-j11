package chess.piece.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import chess.ChessCoord;
import chess.ChessRules;
import chess.PieceType;
import chess.Position;
import chess.move.Move;
import chess.piece.ChessColor;

public class Bishop extends AbstractDirectionalPiece {

    public Bishop(Position pos, ChessColor cc, ChessCoord initialCoord) {
	super(pos, PieceType.BISHOP, cc, initialCoord);
    }

    @Override
    protected List<Move> _generateMoves() {
	List<Move> result;
	if (!this.isActive()) {
	    result = Collections.emptyList();
	} else {
	    result = new LinkedList<Move>();
	    /*
	     * We have to check moving diagonal directions.
	     */

	    // .. top left
	    addPossibleMovesInDirection(result, STEP_LEFT, ChessRules.MIN_FILE, STEP_UP,
		    ChessRules.MAX_RANK);

	    // .. top right
	    addPossibleMovesInDirection(result, STEP_RIGHT, ChessRules.MAX_FILE, STEP_UP,
		    ChessRules.MAX_RANK);

	    // .. bottom left
	    addPossibleMovesInDirection(result, STEP_LEFT, ChessRules.MIN_FILE, STEP_DOWN,
		    ChessRules.MIN_RANK);

	    // .. bottom right
	    addPossibleMovesInDirection(result, STEP_RIGHT, ChessRules.MAX_FILE,
		    STEP_DOWN, ChessRules.MIN_RANK);
	}
	return result;
    }

    @Override
    public boolean controls(ChessCoord coord) {
	if (!super.controls(coord)) {
	    return false;
	}
	final int currentFile = this.getCoord().getFile();
	final int currentRank = this.getCoord().getRank();
	final int destFile = coord.getFile();
	final int destRank = coord.getRank();
	if (Math.abs(currentFile - destFile) == Math
		.abs(currentRank - destRank)) {
	    final int stepFile = currentFile < destFile ? STEP_RIGHT
		    : STEP_LEFT;
	    final int stepRank = currentRank < destRank ? STEP_UP : STEP_DOWN;
	    return this.controls(coord, stepFile, stepRank);
	}
	return false;
    }
}
