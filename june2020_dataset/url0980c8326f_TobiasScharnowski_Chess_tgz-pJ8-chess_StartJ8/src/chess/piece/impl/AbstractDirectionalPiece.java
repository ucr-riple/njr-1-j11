package chess.piece.impl;

import java.util.List;

import chess.ChessCoord;
import chess.PieceType;
import chess.Position;
import chess.impl.Coords;
import chess.move.Move;
import chess.piece.ChessColor;
import chess.piece.Piece;

/**
 * Abstract class representing all the pieces that follow one direction and can
 * be blocked by other pieces in that line.
 * 
 * @author Tobias
 * 
 */
public abstract class AbstractDirectionalPiece extends AbstractPiece {
    /**
     * Indicator for upward movement.
     */
    protected static final int STEP_UP = 1;
    /**
     * Indicator for downward movement.
     */
    protected static final int STEP_DOWN = -1;
    /**
     * Indicator for a movement to the right.
     */
    protected static final int STEP_RIGHT = 1;
    /**
     * Indicator for a movement to the left.
     */
    protected static final int STEP_LEFT = -1;
    /**
     * Indicator to stay in the given direction (used for vertical / horizontal
     * movements)
     */
    protected static final int STEP_STAY = 0;

    public AbstractDirectionalPiece(final Position pos,
            final PieceType pieceType, final ChessColor color,
            final ChessCoord initialCoord) {
        super(pos, pieceType, color, initialCoord);
    }

    /**
     * Method adding to {@code result} all moves that are available in the
     * indicated direction. The direction is
     * 
     * @param result
     *            The list to add the moves to.
     * @param fileStep
     *            The step size that should be taken in the file (horizontal)
     *            direction.
     *            <p>
     *            This should be one of the constant values {@link #STEP_RIGHT},
     *            {@link #STEP_LEFT}, {@link #STEP_STAY}
     * @param fileBorder
     *            The file index value at which the iteration is stopped.
     * @param rankStep
     *            The step size that should be taken in the rank (vertical)
     *            direction.
     *            <p>
     *            This should be one of the constant values {@link #STEP_UP},
     *            {@link #STEP_DOWN}, {@link #STEP_STAY}
     * @param rankBorder
     *            The rank index value at which the iteration is stopped.
     */
    protected void addPossibleMovesInDirection(final List<Move> result,
            final int fileStep, final int fileBorder, final int rankStep,
            final int rankBorder) {
        // Catch the condition for infinite loops.
        // IMPROVE add additional sanity checking if necessary
        if (fileStep == 0 && rankStep == 0) {
            throw new IllegalArgumentException(
                    "Passing only zeros to the directional parameters would make this method run infinitely");
        }
        for (int file = this.getCoord().getFile(), rank = this.getCoord()
                .getRank(); file - fileStep != fileBorder
                && rank - rankStep != rankBorder; file += fileStep, rank += rankStep) {
            final Move possibleMove = defaultCorrectMoveTypeForValidCoord(Coords
                    .coord(file, rank));

            if (possibleMove != null) {
                result.add(possibleMove);
            }
            final Piece targetCoordPiece = pos.getPieceAt(file, rank);
            if (targetCoordPiece != null) {
                break;
            }

        }
    }

    /**
     * Checks whether the piece controls the target {@code coord} following the
     * direction indicated by the passed step arguments.
     * 
     * @param coord
     *            The target {@link ChessCoord coordinate} for which the check
     *            is done.
     * @param stepFile
     *            Check the {@link AbstractDirectionalPiece#addPossibleMoves
     *            addPossibleMoves()} documentation.
     * @param stepRank
     *            Check the {@link AbstractDirectionalPiece#addPossibleMoves
     *            addPossibleMoves()} documentation.
     * @return True, if the piece controls the {@link ChessCoord coord}.
     *         Otherwise, false is returned.
     * @see AbstractDirectionalPiece#addPossibleMoves
     */
    protected boolean controls(final ChessCoord coord, final int stepFile,
            final int stepRank) {
        ChessCoord currCoord;
        for (int file = this.getCoord().getFile() + stepFile, rank = this
                .getCoord().getRank() + stepRank; !((file - stepFile == coord
                .getFile()) && (rank - stepRank == coord.getRank())); file += stepFile, rank += stepRank) {
            currCoord = Coords.coord(file, rank);
            if (coord.sameAs(currCoord)) {
                return true;
            }
            if (pos.getPieceAt(currCoord) != null) {
                break;
            }
        }
        return false;
    }
}
