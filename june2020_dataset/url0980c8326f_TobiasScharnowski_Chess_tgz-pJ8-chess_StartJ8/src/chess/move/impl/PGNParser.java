package chess.move.impl;

import java.util.LinkedList;
import java.util.List;

import chess.ChessCoord;
import chess.ChessRules;
import chess.PieceType;
import chess.Position;
import chess.impl.Coords;
import chess.move.Move;
import chess.move.MoveParser;
import chess.piece.ChessColor;

/**
 * {@link chess.move.MoveParser MoveParser} implementation for Portable Game
 * Notation (PGN).
 */
public class PGNParser implements MoveParser {
    private static final char INDICATOR_PAWN_PROMOTION = '=';
    private static final char INDICATOR_CHECK = '+';
    private static final char INDICATOR_TAKING_MOVE = 'x';
    private static final String CASTLING = "O-O";
    private static final String CASTLING_LONG = "O-O-O";
    private static final int COORD_NOTATION_LENGTH = 2;

    @Override
    public Move parseMove(Position pos, String notation) {
        // Handle the edge cases first
        if (CASTLING.equals(notation)) {
            return createCastlingMove(pos, true);
        }
        if (CASTLING_LONG.equals(notation)) {
            return createCastlingMove(pos, false);
        }

        Move result;
        final ChessColor activeColor = pos.getActiveColor();
        final ChessCoord sourceCoord;

        PieceType pt = PieceType.getTypeByShortCut(notation.charAt(0));

        int indexIndicatorTakingMove = notation.indexOf(INDICATOR_TAKING_MOVE);

        int indexIndicatorCheck = notation.indexOf(INDICATOR_CHECK);

        int indexIndicatorPawnPromotion = notation
                .indexOf(INDICATOR_PAWN_PROMOTION);

        int endIndexTargetFieldNotation;
        if (indexIndicatorPawnPromotion != -1) {
            endIndexTargetFieldNotation = indexIndicatorPawnPromotion;
        } else if (indexIndicatorCheck != -1) {
            endIndexTargetFieldNotation = indexIndicatorCheck;
        } else {
            endIndexTargetFieldNotation = notation.length();
        }

        final String algebraicCoordNotation = notation.substring(
                endIndexTargetFieldNotation - COORD_NOTATION_LENGTH,
                endIndexTargetFieldNotation);
        final ChessCoord targetCoord = Coords
                .coordByAlgebraic(algebraicCoordNotation);

        if (!PieceType.PAWN.equals(pt)) {
            // A Piece other that a pawn is moving
            List<Move> possibleMovesForPieceTypeBeforeCoordinateDisambiguation = pos
                    .possibleMovesToCoordForType(pt, targetCoord);
            if (possibleMovesForPieceTypeBeforeCoordinateDisambiguation.size() == 1) {
                // We only have one Move to chose from anyways, so we just pick
                // it.
                result = possibleMovesForPieceTypeBeforeCoordinateDisambiguation
                        .get(0);
            } else {
                // There are ambiguities between different pieces of the type
                // that are able to get to the position.
                /*
                 * We know that starting from the second character in the
                 * notation some disambuing coordinate has to be found. We now
                 * have to find out which additional information are supplied
                 * (file, rank, file+rank).
                 */
                int firstDisambiguingIndex = 2;
                int lastDisambiguingIndex;
                if (indexIndicatorTakingMove != -1) {
                    // We have a taking move, so we end at the taking move
                    // indicator indice.
                    lastDisambiguingIndex = indexIndicatorTakingMove;
                } else {
                    lastDisambiguingIndex = endIndexTargetFieldNotation
                            - COORD_NOTATION_LENGTH;
                }
                String disambiguatingInfos = notation.substring(
                        firstDisambiguingIndex, lastDisambiguingIndex);

                // First check if we actually have a full source coordinate
                // inside the disambiguation. We then just take this.
                if (disambiguatingInfos.length() > 1) {
                    sourceCoord = Coords.coordByAlgebraic(disambiguatingInfos);
                    // After having found both the source coordinate and the
                    // target coordinate, we just have to pick the right move
                    // from our candidates (this is assuming we don't have pawn
                    // promotion moves in here).
                    result = null;
                    for (Move candidate : possibleMovesForPieceTypeBeforeCoordinateDisambiguation) {
                        if (sourceCoord.sameAs(candidate.getSourceCoord())) {
                            result = candidate;
                            break;
                        }
                    }
                    if (result == null) {
                        throw new IllegalStateException(
                                "Error while trying to parse the full disambiguation coordinate.");
                    }
                } else {
                    /*
                     * As we don't have the full coordinate, we have to figure
                     * out whether we have to do the disambiguation for the file
                     * or the rank index.
                     */
                    char fileOrRank = disambiguatingInfos.charAt(0);
                    final List<Move> disambiguatedCoordCandidates = new LinkedList<>();
                    if (Character.isDigit(fileOrRank)) {
                        /*
                         * We have a digit, it is a file. From this we can
                         * assume that there is only one source coordinate with
                         * this particular file.
                         */
                        int file = fileOrRank - '1';
                        for (Move candidate : possibleMovesForPieceTypeBeforeCoordinateDisambiguation) {
                            if (candidate.getSourceCoord().getFile() == file) {
                                disambiguatedCoordCandidates.add(candidate);
                            }
                        }

                    } else {
                        // We have no digit, we assume a character representing
                        // the rank.
                        final int rank = fileOrRank - 'a';
                        for (Move candidate : possibleMovesForPieceTypeBeforeCoordinateDisambiguation) {
                            if (candidate.getSourceCoord().getRank() == rank) {
                                disambiguatedCoordCandidates.add(candidate);
                            }
                        }
                    }

                    if (disambiguatedCoordCandidates.size() == 1) {
                        result = disambiguatedCoordCandidates.get(0);
                    } else {
                        // IMPROVE consolidate the move creation so that
                        // pawn moves are handled in here as well.
                        /*
                         * We have the right source coordinate, but we still
                         * have more than one move. This can only occur in case
                         * we have a pawn promotion move. Pawn moves are handled
                         * separately, though. This is why we assume something
                         * went wrong and we throw an exception for now.
                         */
                        throw new IllegalStateException(
                                "Reached an unexpected branch while parsing a PGN move notation. Could not disambiguate the move.");
                    }
                }
            }

        } else {
            // Pawn is moving
            // FIXME Implement pawn move generation
            result = null;
        }

        // FIXME Go on from here

        return result;
    }

    private CastlingMove createCastlingMove(final Position pos,
            final boolean kingSide) {
        final ChessColor activeColor = pos.getActiveColor();
        int rookFile = pos.getCastlingRookFile(kingSide, activeColor);
        final ChessCoord sourceCoord = pos.activeKingCoord();
        return new CastlingMove(sourceCoord, ChessRules.getCastlingTargetCoord(
                kingSide, activeColor), Coords.coord(rookFile,
                sourceCoord.getRank()));
    }
}
