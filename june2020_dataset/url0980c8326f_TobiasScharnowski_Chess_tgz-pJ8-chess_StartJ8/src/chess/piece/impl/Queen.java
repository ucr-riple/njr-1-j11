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

public class Queen extends AbstractDirectionalPiece {

    public Queen(Position pos, ChessColor cc, ChessCoord initialCoord) {
        super(pos, PieceType.QUEEN, cc, initialCoord);
    }

    // IMPROVE See Rook#getPossibleMoves
    @Override
    protected List<Move> _generateMoves() {
        List<Move> result;
        if (!this.isActive()) {
            result = Collections.emptyList();
        } else {
            result = new LinkedList<Move>();

            // Possible Rook move section
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

            // Possible Bishop move section
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

        // Here we are sure we are not at the coordinate ourselves
        final int currentRank = this.getCoord().getRank();
        final int currentFile = this.getCoord().getFile();
        final int destFile = coord.getFile();
        final int destRank = coord.getRank();

        // First check the rook possibilities
        /*
         * Now check if we are either on the same file or rank. Afterwards check
         * where the coordinate is relative to us (right/left, above/below) and
         * set the step accordingly. Afterwards loop to we can see whether
         * something is in our way. If not, we control the piece.
         */
        if (currentRank == destRank) {
            final int stepFile = currentFile < destFile ? STEP_RIGHT
                    : STEP_LEFT;
            return this.controls(coord, stepFile, STEP_STAY);
        } else if (currentFile == destFile) {
            final int stepRank = currentRank < destRank ? STEP_UP : STEP_DOWN;
            return this.controls(coord, STEP_STAY, stepRank);
        }
        // The rook moving did not yield results, check for the Bishop moves
        // instead.
        if ((Math.abs(currentFile - destFile) == Math.abs(currentRank
                - destRank))) {
            final int stepFile = currentFile < destFile ? STEP_RIGHT
                    : STEP_LEFT;
            final int stepRank = currentRank < destRank ? STEP_UP : STEP_DOWN;
            return this.controls(coord, stepFile, stepRank);
        }
        // Nothing found
        return false;
    }

}
