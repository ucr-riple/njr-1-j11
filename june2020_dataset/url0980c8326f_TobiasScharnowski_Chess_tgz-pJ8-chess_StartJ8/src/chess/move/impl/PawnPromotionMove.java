package chess.move.impl;

import chess.ChessCoord;
import chess.PieceType;
import chess.Position;
import chess.piece.PieceFactory;
import chess.piece.impl.DefaultPieceFactory;
import chess.piece.impl.Pawn;

/**
 * Class representing a move where a pawn is promoted.
 * 
 * @author Tobias
 * 
 */
/*
 * This is implemented as a SimpleTakingMove. This is done due to the
 * restriction of java to single inheritance. We rely on the fact that
 * SimpleTakingMove does not expect an actual piece to be at the destination
 * location. An other solution would be to implement two separate classes like
 * PawnPromotionSimpleMove and PawnPromotionTakingMove. This would probably
 * result in code duplication, though. Maybe we can think of a better solution
 * here.
 */
public class PawnPromotionMove extends SimpleTakingMove {
    // IMPROVE Make PieceFactory a bean.
    private final PieceFactory pieceFactory = new DefaultPieceFactory();
    private final PieceType pieceType;
    private Pawn movingPawn;

    public PawnPromotionMove(ChessCoord source, ChessCoord target,
	    final PieceType pieceType) {
	super(source, target);
	this.pieceType = pieceType;
    }

    @Override
    public void doMove(final Position pos) {
	this.movingPawn = (Pawn) pos.getPieceAt(getSourceCoord());
	super.doMove(pos);
	movingPawn.setActive(false);
	pos.addPiece(pieceFactory.createPiece(movingPawn.getPos(),
		this.pieceType, movingPawn.getColor(), this.target));
    }

    @Override
    public void undoMove(final Position pos) {
	pos.removePiece(pos.getPieceAt(target));
	pos.setPieceAt(movingPawn, getTargetCoord());
	movingPawn.setActive(true);
	super.undoMove(pos);
    }

}
