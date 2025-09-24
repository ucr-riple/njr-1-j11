package chess.piece.impl;

import java.util.Collections;
import java.util.List;

import chess.ChessCoord;
import chess.PieceType;
import chess.Position;
import chess.move.Move;
import chess.move.impl.SimpleMove;
import chess.move.impl.SimpleTakingMove;
import chess.piece.ChessColor;
import chess.piece.Piece;

public abstract class AbstractPiece implements Piece {
    protected Position pos;
    protected PieceType pieceType;
    protected ChessColor color;
    protected ChessCoord coord;
    private int moveCnt = 0;
    private boolean active = true;

    /**
     * Creates a Piece. The piece's coordinate is set using the position's
     * {@link chess.Position#setPieceAt(Piece, ChessCoord)} method.
     *
     * @param pos
     *            The {@link chess.Position Position} the piece is assigned to.
     * @param pieceType
     *            The {@link chess.PieceType PieceType} the piece is assigned.
     * @param color
     *            The {@link chess.piece.ChessColor ChessColor} the piece is
     *            assigned.
     * @param initialCoord
     *            The {@link chess.ChessCoord ChessCoord} the piece is set to
     *            within the position.
     */
    public AbstractPiece(final Position pos, final PieceType pieceType,
            final ChessColor color, final ChessCoord initialCoord) {
        this.pieceType = pieceType;
        this.color = color;
        this.pos = pos;
        pos.setPieceAt(this, initialCoord);
    }

    /*
     * This default implementation filters out whether there is an overarching
     * reason that a piece does not control the destination field. No piece
     * controls the field it is currently placed on.
     * 
     * @return Returns false in case there is a standard reason for the piece
     * not to be able to control the target field.
     */
    @Override
    public boolean controls(final ChessCoord coord) {
        return !coord.equals(this.getCoord());
    }

    @Override
    public final List<Move> getPossibleMoves() {
        if (!this.isActive()) {
            return Collections.emptyList();
        } else {
            return this._generateMoves();
        }
    }

    protected abstract List<Move> _generateMoves();

    @Override
    public PieceType getType() {
        return this.pieceType;
    }

    @Override
    public ChessColor getColor() {
        return this.color;
    }

    @Override
    public ChessCoord getCoord() {
        return this.coord;
    }

    @Override
    public Position getPos() {
        return this.pos;
    }

    @Override
    public void setCoord(ChessCoord coord) {
        this.coord = coord;
    }

    @Override
    public void incrMoveCnt() {
        ++this.moveCnt;
    }

    @Override
    public void decrMoveCnt() {
        --this.moveCnt;
    }

    @Override
    public int getMoveCnt() {
        return this.moveCnt;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    // this as well

    /**
     * Returns a {@link chess.move.Move Move} implementation Object depending on
     * the situation on the board. In case an opposing piece is present on the
     * board, a {@link chess.move.impl.SimpleTakingMove SimpleTakingMove} is
     * returned. If there is no Piece present at the destination location, a
     * {@link chess.move.impl.SimpleMove SimpleMove} is created. If the field is
     * blocked, null is returned.
     *
     * @param destCoord
     *            The {@link chess.ChessCoord Coordinate} the
     *            {@link chess.piece.Piece Piece} moves to. The coordinate is
     *            expected to have valid indices.
     * @return True in case the field was empty (meaning we can go on searching
     *         for moves in this direction). False is returned otherwise.
     */
    protected Move defaultCorrectMoveTypeForValidCoord(
            final ChessCoord destCoord) {
        Move result;
        final Piece destCoordPiece = this.getPos().getPieceAt(destCoord);
        if (destCoordPiece == null) {
            // There is no Piece at the target position so we are
            // doing a simple move.
            result = new SimpleMove(this.getCoord(), destCoord);
        } else {
            if ((!destCoordPiece.getColor().equals(this.getColor()))) {
                // We have an opposing piece at the destination
                // Coordinate, so we are taking it
                result = new SimpleTakingMove(this.getCoord(), destCoord);
            } else {
                // We are blocked by a piece of our color
                result = null;
            }
        }
        return result;
    }

    @Override
    public char shortCut() {
        char shortCut = this.getType().getShortCut();
        if (ChessColor.BLACK.equals(this.getColor())) {
            shortCut = Character.toLowerCase(shortCut);
        }
        return shortCut;
    }

    /**
     * Helper method determining whether the passed {@link chess.ChessCoord
     * coordinate} in our associated {@link chess.Position position} is blocked
     * by a piece of our color.
     *
     * @param coord
     *            The {@link chess.ChessCoord coordinate} to check against.
     * @return True, if there is is a piece of the same color on {@code coord}
     *         in our associated {@link chess.Position position}.
     */
    protected boolean isBlocked(final ChessCoord coord) {
        final Piece targetCoordPiece = this.getPos().getPieceAt(coord);
        return targetCoordPiece != null
                && targetCoordPiece.getColor().equals(this.getColor());
    }

    @Override
    public List<Move> generatePossibleMovesToCoord(final ChessCoord destCoord) {
        List<Move> result;
        if (this.canMoveTo(destCoord)) {
            result = Collections.singletonList(this
                    .defaultCorrectMoveTypeForValidCoord(destCoord));
        } else {
            result = Collections.emptyList();
        }
        return result;
    }

    @Override
    public boolean canMoveTo(final ChessCoord destCoord) {
        return controls(destCoord) && !isBlocked(destCoord);
    }

}
