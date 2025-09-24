package chess.piece.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import chess.ChessCoord;
import chess.ChessRules;
import chess.PieceType;
import chess.Position;
import chess.impl.Coords;
import chess.move.Move;
import chess.piece.ChessColor;

public class Knight extends AbstractPiece {
    /**
     * The number of steps the Knight jumps in the far direction.
     */
    private static final int FAR_STEP = 2;
    /**
     * The number of coordinates the Knight jumps in the short direction.
     */
    private static final int SHORT_STEP = 1;
    /**
     * Inner helper array representing the possible jumps that can be done by a
     * Knight piece. This is used to guide the target coordinate generation
     * process.
     */
    private static final KnightJump[] POSSIBLE_JUMPS = KnightJump.JUMPS;

    public Knight(Position pos, ChessColor cc, ChessCoord initialCoord) {
        super(pos, PieceType.KNIGHT, cc, initialCoord);
    }

    @Override
    protected List<Move> _generateMoves() {
        List<Move> result = new ArrayList<>(POSSIBLE_JUMPS.length * 2);
        List<ChessCoord> targetCoords = this.getPossibleCoords();
        for (ChessCoord coord : targetCoords) {
            final Move possibleMove = this
                    .defaultCorrectMoveTypeForValidCoord(coord);
            if (possibleMove != null) {
                result.add(possibleMove);
            }
        }
        return result;
    }

    @Override
    public boolean controls(ChessCoord coord) {
        /*
         * In the case of a Knight we only have to check whether the
         * coordinate's difference alters between the far jump and the short
         * jump.
         */
        final int fileDiff = Math.abs(coord.getFile()
                - this.getCoord().getFile());
        final int rankDiff = Math.abs(coord.getRank()
                - this.getCoord().getRank());
        return (fileDiff == FAR_STEP && rankDiff == SHORT_STEP)
                || (fileDiff == SHORT_STEP && rankDiff == FAR_STEP);
    }

    /**
     * Private helper method to get all the accessible coordinates.
     * <p>
     * This is particularly useful for the Knight piece as this is going to
     * directly yield the target fields for possible further analysis.
     * 
     * @return Returns a {@link java.util.List list} of the coordinates
     *         controlled by this piece.
     */
    private List<ChessCoord> getPossibleCoords() {
        List<ChessCoord> result;
        if (!this.isActive()) {
            result = Collections.emptyList();
        } else {
            final int currentFile = this.getCoord().getFile();
            final int currentRank = this.getCoord().getRank();
            result = new LinkedList<ChessCoord>();

            for (KnightJump step : POSSIBLE_JUMPS) {
                final int file = currentFile + step.getFileJump();
                // First check whether the file valid
                if (ChessRules.fileWithinBounds(file)) {
                    // Now check for the ranks to be in bounds as well
                    for (int rankJump : step.getRankJumps()) {
                        final int rank = currentRank + rankJump;
                        if (ChessRules.rankWithinBounds(rank)) {
                            result.add(Coords.coord(file, rank));
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Helper class to represent the combinations of rank / file differences
     * that a knight can jump to.
     * <p>
     * The knight always has a far and a short jump for the rank and file part.
     * The sign is also alternated.
     * <p>
     * The way we represent it here is that for each file jump (long to the
     * left, short to the left, short to the right, long to the right) we have
     * to move up and down far or short depending on which jump we took for the
     * horizontal direction.
     * 
     */
    private static class KnightJump {
        public static final KnightJump JUMPS[] = {
                new KnightJump(FAR_STEP, SHORT_STEP),
                new KnightJump(SHORT_STEP, FAR_STEP),
                new KnightJump(-FAR_STEP, SHORT_STEP),
                new KnightJump(-SHORT_STEP, FAR_STEP) };
        /**
         * The number of steps to go in the file (horizontal) direction.
         */
        private final int fileJump;
        /**
         * The possible numbers of steps to go in the rank (vertical) direction
         * for each fileJump.
         */
        private final int rankJumps[] = new int[2];

        private KnightJump(int fileJump, int rankJump) {
            this.fileJump = fileJump;
            rankJumps[0] = rankJump;
            rankJumps[1] = -rankJump;
        }

        public int getFileJump() {
            return this.fileJump;
        }

        public int[] getRankJumps() {
            return rankJumps;
        }
    }
}
