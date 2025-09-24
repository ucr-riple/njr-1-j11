package chess.piece.impl;

import java.util.ArrayList;
import java.util.List;

import chess.ChessCoord;
import chess.ChessRules;
import chess.PieceType;
import chess.Position;
import chess.impl.Coords;
import chess.move.Move;
import chess.move.impl.EnPassantMove;
import chess.move.impl.PawnPromotionMove;
import chess.move.impl.SimpleMove;
import chess.move.impl.SimpleTakingMove;
import chess.piece.ChessColor;
import chess.piece.Piece;

public class Pawn extends AbstractPiece {
    private static final int START_WHITE = ChessRules.MIN_RANK + 1;
    private static final int START_BLACK = ChessRules.MAX_RANK - 1;
    private static final int PROMOTION_RANK_WHITE = ChessRules.MAX_RANK;
    private static final int PROMOTION_RANK_BLACK = ChessRules.MIN_RANK;
    private static final int STEP_WHITE = 1;
    private static final int STEP_BLACK = -STEP_WHITE;
    private static final int FIRST_MOVE_FACTOR = 2;
    private final int rankStep;
    private final int startRank;
    private final int promotionRank;
    private final int enPassantRank;

    public Pawn(Position pos, ChessColor cc, ChessCoord initialCoord) {
        super(pos, PieceType.PAWN, cc, initialCoord);
        // Set the directions constant depending on this piece's color.
        if (ChessColor.WHITE.equals(cc)) {
            this.rankStep = STEP_WHITE;
            this.startRank = START_WHITE;
            this.promotionRank = PROMOTION_RANK_WHITE;
        } else {
            this.rankStep = STEP_BLACK;
            this.startRank = START_BLACK;
            this.promotionRank = PROMOTION_RANK_BLACK;
        }
        /*
         * To get our enPassantRank we go back from our promotion rank (-1 to
         * get to the starting position and the number of steps for the opening
         * move to get to the rank which we need to take the pawn by en passent)
         */
        this.enPassantRank = promotionRank - (1 + FIRST_MOVE_FACTOR) * rankStep;
    }

    @Override
    protected List<Move> _generateMoves() {
        // Collect some information to shorten the code later.
        final List<Move> result = new ArrayList<>();
        final ChessCoord currentCoord = this.getCoord();
        final int currentFile = currentCoord.getFile();
        final int currentRank = currentCoord.getRank();
        final int singleStepTargetRank = currentRank + rankStep;
        final boolean isPromoting = singleStepTargetRank == this.promotionRank;

        // First check for the normal forward moves
        ChessCoord tarCoord = Coords.coord(currentFile, singleStepTargetRank);
        // Single step
        if (this.getPos().getPieceAt(tarCoord) == null) {
            if (isPromoting) {
                this.addPromotionMoves(result, tarCoord);
            } else {
                result.add(new SimpleMove(currentCoord, tarCoord));
            }
        }

        // Double step
        if (currentRank == this.startRank) {
            tarCoord = Coords.coord(currentFile, currentRank
                    + FIRST_MOVE_FACTOR * rankStep);
            if (this.getPos().getPieceAt(tarCoord) == null) {
                // We don't bother about checking for being promoted because
                // that would mean that the pawn has a one-move promotion, which
                // really does not make any sense. This would break this code
                // though.
                result.add(new SimpleMove(currentCoord, tarCoord));
            }
        }

        // Now deal with taking moves
        // Lower file (left)
        addPossibleTakingMove(result, currentFile - 1, isPromoting);
        // Higher file (right)
        addPossibleTakingMove(result, currentFile + 1, isPromoting);

        return result;
    }

    @Override
    public List<Move> generatePossibleMovesToCoord(final ChessCoord coord) {
        throw new IllegalArgumentException("This cannot be applied to pawns");
    }

    @Override
    public boolean controls(ChessCoord coord) {
        /*
         * The pawn controls the field if it is diagonally in front of him. This
         * means there has to be an absolute distance of 1 in file direction.
         * And one step towards the regular moving direction.
         */
        return this.getCoord().getRank() + this.rankStep == coord.getRank()
                && Math.abs(this.getCoord().getFile() - coord.getFile()) == 1;
    }

    @Override
    protected Move defaultCorrectMoveTypeForValidCoord(
            final ChessCoord destCoord) {
        // This should never occur anyway so we keep is as a runtime exception
        // instead of something more meaningful like OppNotSuppException
        throw new RuntimeException(
                "This method cannot be applied to pawn pieces");
    }

    /**
     * Private helper method to add promotion moves for all promotable
     * {@link PieceType piece types} to the current move list.
     * 
     * @param currentMoves
     *            The list to add the moves to.
     * @param target
     *            The pawn move's target coordinate.
     */
    private void addPromotionMoves(final List<Move> currentMoves,
            final ChessCoord target) {
        for (PieceType pt : PieceType.values()) {
            if (pt.isPromotable()) {
                currentMoves.add(new PawnPromotionMove(this.getCoord(), target,
                        pt));
            }
        }

    }

    /**
     * Private helper method to deal with taking moves to the right and left to
     * the pawn.
     * <p>
     * This adds the possible move(s) to the passed list in case the passed file
     * valid. Adds nothing if the passed file outside the board.
     * 
     * @param moveList
     *            The {@code List list} the moves are added to.
     * @param targetFile
     *            The target file index.
     * @param isPromoting
     *            Flag stating whether we are promoting at the moment. This
     *            could be calculated by the method itself but was calculated in
     *            the calling context.
     */
    private void addPossibleTakingMove(final List<Move> moveList,
            final int targetFile, final boolean isPromoting) {
        if (ChessRules.fileWithinBounds(targetFile)) {
            final ChessCoord tarCoord = Coords.coord(targetFile, this
                    .getCoord().getRank() + this.rankStep);
            final Piece targetCoordPiece = this.getPos().getPieceAt(tarCoord);
            // Check whether there is an opposing piece at the target
            // coordinate.
            if (targetCoordPiece != null
                    && (!this.getColor().equals(targetCoordPiece.getColor()))) {
                if (isPromoting) {
                    addPromotionMoves(moveList, tarCoord);
                } else {
                    moveList.add(new SimpleTakingMove(this.getCoord(), tarCoord));
                }
            }
            // Also check for en passant moves.
            else if (this.getCoord().getRank() == this.enPassantRank
                    && this.getPos().getEnpassantFile() == targetFile) {
                moveList.add(new EnPassantMove(this.getCoord(), tarCoord));
            }
        }
    }

    @Override
    public boolean canMoveTo(ChessCoord coord) {
        // TODO Implement this for Pawns if necessary
        throw new IllegalArgumentException(
                "Checking for move availability is not implemented for Pawns as it is currently not intended to be used for generating moves from notations");
    }
}
