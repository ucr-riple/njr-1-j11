package chess.move.impl;

import chess.ChessCoord;
import chess.ChessRules;
import chess.PieceType;
import chess.Position;
import chess.move.Move;
import chess.piece.ChessColor;
import chess.piece.Piece;

/**
 * Class representing a move that only involves moving a piece around without
 * any taking of pieces.
 * 
 * @author Tobias
 * 
 */
/*
 * This seems to be the base case for most of the other moves. In every other
 * circumstance, even in castling and taking pieces the main piece moves from
 * source to destination coordinates. The only difference in castling is moving
 * the rook as well as the king. The only difference in taking is removing the
 * piece that has been taken and returning it when undoing a move.
 */
// TODO We need to implement updating the state of the position (50 moves rule,
// ...)

public class SimpleMove implements Move {
    private static final int EN_PASSANT_UNDEFINED = -2;
    protected ChessCoord source;
    protected ChessCoord target;
    private int prevEnPassantFile = EN_PASSANT_UNDEFINED;
    private boolean disabledCastlingAvailability_Kingside;
    private boolean disabledCastlingAvailability_Queenside;

    public SimpleMove(ChessCoord source, ChessCoord target) {
        this.source = source;
        this.target = target;
    }

    /*
     * @Override public final Position afterMove(final Position pos) { Position
     * newPos = pos.getCopy(); this.doMove(newPos); return newPos; }
     */

    /*
     * @Override public final Position beforeMove(final Position pos) { Position
     * newPos = pos.getCopy(); this.doMove(newPos); return newPos; }
     */

    @Override
    public void doMove(final Position pos) {
        final Piece movingPiece = pos.getPieceAt(getSourceCoord());

        // Update availability status
        updateCastlingAvailabilities(pos, movingPiece);
        updateEnpassantFile(pos, movingPiece.getType());

        // Do the actual move
        moveFromTo(pos, getSourceCoord(), getTargetCoord());

        // Do general maintenance
        movingPiece.incrMoveCnt();
        pos.advanceMoveNumber();
        pos.flipActiveColor();
    }

    @Override
    public void undoMove(final Position pos) {
        final Piece movingPiece = pos.getPieceAt(getTargetCoord());
        moveFromTo(pos, getTargetCoord(), getSourceCoord());
        undoUpdateCastlingAvailabilities(pos, movingPiece);
        updateEnpassantFile(pos, null);

        movingPiece.decrMoveCnt();
        pos.revertMoveNumber();
        pos.flipActiveColor();
    }

    @Override
    public ChessCoord getSourceCoord() {
        return this.source;
    }

    @Override
    public ChessCoord getTargetCoord() {
        return this.target;
    }

    protected void moveFromTo(final Position pos, final ChessCoord source,
            final ChessCoord dest) {
        pos.setPieceAt(pos.getPieceAt(source), dest);
        pos.setPieceAt(null, source);
    }

    /**
     * Updates the EnpassantFile according to the move. NOTE: This has to be
     * called before the move is done.
     * 
     * @param pos
     */
    private void updateEnpassantFile(final Position pos,
            final PieceType pieceType) {
        // If we did not set that previously, we have are doing the move and we
        // have to calculate the new one for the position.
        if (prevEnPassantFile == EN_PASSANT_UNDEFINED) {
            prevEnPassantFile = pos.getEnpassantFile();
            int updatedEnpassantFile;
            if (PieceType.PAWN.equals(pieceType)
                    && Math.abs(getSourceCoord().getRank()
                            - getTargetCoord().getRank()) == 2) {
                updatedEnpassantFile = getTargetCoord().getFile();
            } else {
                updatedEnpassantFile = ChessRules.ENPASSANT_FILE_DISABLED;
            }
            pos.setEnpassantFile(updatedEnpassantFile);
        } else { // We are undoing the move and we just have to reset the
            // previous state
            pos.setEnpassantFile(prevEnPassantFile);
            prevEnPassantFile = EN_PASSANT_UNDEFINED;
        }
    }

    /**
     * This disables the castling availabilities of castling according to this
     * move. This is only done while doing the move. While undoing the move
     * {@link #undoUpdateCastlingAvailabilities(Position, Piece)} has to be
     * called.
     * 
     * @param pos
     *            The position this move is done in.
     * @param movingPiece
     *            The piece that is being moved.
     */
    private void updateCastlingAvailabilities(final Position pos,
            final Piece movingPiece) {
        /*
         * We are doing our move at the moment, so we have to figure out whether
         * we affect the castling availability and whether it is for king - or
         * queenside
         */
        final ChessColor pieceColor = movingPiece.getColor();
        final PieceType pieceType = movingPiece.getType();
        /*
         * First check for the king. In this case both sides have to be disabled
         * if they are not already
         */
        if (PieceType.KING.equals(pieceType)) {
            // Disable kingside if this is still available
            this.disableCastlingAvailability(pos,
                    pos.isCastlingAvailable(true, pieceColor), true, pieceColor);

            // Disable queenside if this is still available
            this.disableCastlingAvailability(pos,
                    pos.isCastlingAvailable(false, pieceColor), false,
                    pieceColor);
            // Here we return just to save the checks for rook PieceType
        }
        /*
         * If it was no king, maybe we got a rook. In this case the question is
         * whether the rook is on the king - or queenside
         */
        else if (PieceType.ROOK.equals(pieceType)) {
            final ChessCoord movingPieceCoord = movingPiece.getCoord();
            final int neededRank = ChessRules.baseRank(pieceColor);
            /*
             * We need to check whether one of the side's castling has to be
             * disabled. We therefore first check whether the rook is actually
             * at the side's starting position and the castling of the side is
             * still available.
             */
            // First check kingside
            final boolean disableKingside = movingPieceCoord.getFile() == pos
                    .getCastlingRookFile(true, pieceColor)
                    && movingPieceCoord.getRank() == neededRank
                    && pos.isCastlingAvailable(true, pieceColor);
            disableCastlingAvailability(pos, disableKingside, true, pieceColor);

            // Now check queenside
            final boolean disableQueenside = movingPieceCoord.getFile() == pos
                    .getCastlingRookFile(false, pieceColor)
                    && movingPieceCoord.getRank() == neededRank
                    && pos.isCastlingAvailable(false, pieceColor);
            disableCastlingAvailability(pos, disableQueenside, false,
                    pieceColor);
        }
    }

    /**
     * Disables the castling availability for the passed side.
     * <p>
     * This also deals with setting the flags for properly undoing the changes
     * to the castling availability. That is why this has to be called even if
     * the castling availability does not actually change.
     * 
     * @param pos
     *            The position which the move is done in.
     * @param disable
     *            Flag indicating whether the availability has to be disabled.
     * @param kingside
     *            True: Kingside, false: queenside
     * @param pieceColor
     *            The color to set the castling availability for.
     */
    private void disableCastlingAvailability(final Position pos,
            final boolean disable, final boolean kingside,
            final ChessColor pieceColor) {
        if (kingside ? (disabledCastlingAvailability_Kingside = disable)
                : (disabledCastlingAvailability_Queenside = disable)) {
            pos.setCastlingAvailable(kingside, pieceColor, false);
        }
    }

    /**
     * This undoes the changes to the {@link chess.Position Position}'s castling
     * availabilities done by this move.
     * <p>
     * This should be called while undoing the move and
     * {@link #updateCastlingAvailabilities(Position, Piece)} has to have been
     * called before (this is done in the {@link #doMove(Position)} doMove
     * method)
     * 
     * @param pos
     *            The position the move is played in.
     * @param movingPiece
     *            The piece being moved.
     */
    private void undoUpdateCastlingAvailabilities(final Position pos,
            final Piece movingPiece) {
        /*
         * We have already dealt with the castling availability, so we are
         * undoing our move. We only have to do anything in case we actually
         * changed the CastlingAvailability. As we can only have affected the
         * castling availability negatively, we set it back to available.
         */
        // Check for changes to kingside castling availability
        if (disabledCastlingAvailability_Kingside) {
            pos.setCastlingAvailable(true, movingPiece.getColor(), true);
        }
        // Check for changes to queenside castling availability
        if (disabledCastlingAvailability_Queenside) {
            pos.setCastlingAvailable(false, movingPiece.getColor(), true);
        }
    }

    @Override
    public String toString() {
        return "Move " + this.getSourceCoord() + " to " + this.getTargetCoord();
    }
}
