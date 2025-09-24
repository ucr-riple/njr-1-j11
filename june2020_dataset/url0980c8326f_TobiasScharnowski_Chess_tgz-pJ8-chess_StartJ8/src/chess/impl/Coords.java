package chess.impl;

import chess.ChessCoord;
import chess.ChessRules;

public class Coords {
    private static ChessCoord coords[][] = initCoords();

    private static ChessCoord[][] initCoords() {
        ChessCoord coords[][] = new ChessCoord[ChessRules.MAX_FILE + 1][ChessRules.MAX_RANK + 1];
        for (int file = ChessRules.MIN_FILE; file <= ChessRules.MAX_FILE; ++file) {
            for (int rank = ChessRules.MIN_RANK; rank <= ChessRules.MAX_RANK; ++rank) {
                coords[file][rank] = new DefaultChessCoord(file, rank);
            }
        }
        return coords;
    }

    public static ChessCoord coord(final int file, final int rank) {
        return coords[file][rank];
    };

    /**
     * Retrieves the coordinate for the respective {@code algebraic} coordinate
     * notation.
     * <p>
     * Examples for valid algebraic notations are "a1", "d6", "h7".
     *
     * @param algebraic
     *            The algebraic coordinate String.
     * @return Returns the respective {@link chess.ChessCoord ChessCoord}.
     */
    public static ChessCoord coordByAlgebraic(final String algebraic) {
        if (algebraic.length() != 2) {
            throw new IllegalArgumentException(
                    "The algebraic coordinate has to be of length 2");
        } else {
            final int file = algebraic.charAt(0) - 'a';
            final int rank = algebraic.charAt(1) - '1';
            return coord(file, rank);
        }
    }

    /**
     * This is a private implementation of the ChessCoord interface to make sure
     * it is supplied only by the coords class an no instances of it are
     * generated that are not necessary.
     *
     * @author Tobias
     *
     */
    private static class DefaultChessCoord implements ChessCoord {
        private final int file;
        private final int rank;

        /**
         * This constructor has limited visibility as it should not be used
         * directly by any class.
         *
         * @param file
         * @param rank
         */
        DefaultChessCoord(final int file, final int rank) {
            assertBounds(file, rank);
            this.file = file;
            this.rank = rank;
        }

        @Override
        public int getRank() {
            return rank;
        }

        @Override
        public int getFile() {
            return file;
        }

        private void assertBounds(final int file, final int rank) {
            this.assertFileBound(file);
            this.assertRankBound(rank);
        }

        private void assertFileBound(final int file) {
            if (!(ChessRules.fileWithinBounds(file))) {
                throw new IllegalStateException("The passed file indice: "
                        + file + " is not within legal bounds");
            }
        }

        private void assertRankBound(final int rank) {
            if (!(ChessRules.fileWithinBounds(rank))) {
                throw new IllegalStateException("The passed rank indice: "
                        + rank + " is not within legal bounds");
            }
        }

        @Override
        public boolean sameAs(final ChessCoord other) {
            return this.getFile() == other.getFile()
                    && this.getRank() == other.getRank();
        }

        @Override
        public String toString() {
            return ((char) ('a' + this.getFile())) + "" + (this.getRank() + 1);
        }
    }
}
