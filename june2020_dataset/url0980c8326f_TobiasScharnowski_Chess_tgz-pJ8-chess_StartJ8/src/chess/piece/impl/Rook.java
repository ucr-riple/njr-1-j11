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

public class Rook extends AbstractDirectionalPiece {
    public Rook(Position pos, ChessColor cc, ChessCoord initialCoord,
            final boolean isKingside) {
        super(pos, PieceType.ROOK, cc, initialCoord);

    }

    /*
     * IMPROVE We can think about uing the DirectionalPiece capabilities here
     * for better readability and easier to maintain code. This would cost us
     * some performance because of unnecessary arithmetic operations which we
     * need in case of diagonal movements but not in case of the rook's
     * movement, though.
     */
    @Override
    protected List<Move> _generateMoves() {
        List<Move> result;
        if (!this.isActive()) {
            result = Collections.emptyList();
        } else {
            result = new LinkedList<Move>();

            /*
             * We have to check moving in horizontal/vertical directions.
             */

            // .. left
            addPossibleMovesInDirection(result, STEP_LEFT, ChessRules.MIN_FILE, STEP_STAY,
                    -1);

            // .. right
            addPossibleMovesInDirection(result, STEP_RIGHT, ChessRules.MAX_FILE,
                    STEP_STAY, -1);

            // .. bottom
            addPossibleMovesInDirection(result, STEP_STAY, -1, STEP_DOWN,
                    ChessRules.MIN_RANK);

            // .. top
            addPossibleMovesInDirection(result, STEP_STAY, -1, STEP_UP,
                    ChessRules.MAX_RANK);
        }
        return result;
    }

    @Override
    public boolean controls(final ChessCoord coord) {
        // First check for general reasons that we don't control the position.
        // This includes checking that we are not placed on the coordinate
        // ourselves. This is important to know for the following checks.
        if (!super.controls(coord)) {
            return false;
        }

        // Here we are sure we are not at the coordinate ourselves
        final int currRank = this.getCoord().getRank();
        final int currFile = this.getCoord().getFile();

        /*
         * Now check for if we are either on the same file or rank. Afterwards
         * check where the coordinate is relative to us (right/left,
         * above/below) and set the step accordingly. Afterwards loop to we can
         * see whether something is in our way. If not, we control the piece.
         */
        if (currRank == coord.getRank()) {
            final int stepFile = currFile < coord.getFile() ? STEP_RIGHT
                    : STEP_LEFT;
            return this.controls(coord, stepFile, STEP_STAY);
        } else if (currFile == coord.getFile()) {
            final int stepRank = currRank < coord.getRank() ? STEP_UP
                    : STEP_DOWN;
            return this.controls(coord, STEP_STAY, stepRank);
        }
        // Not on the same line, we cannot reach the coordinate.
        return false;
    }
}
