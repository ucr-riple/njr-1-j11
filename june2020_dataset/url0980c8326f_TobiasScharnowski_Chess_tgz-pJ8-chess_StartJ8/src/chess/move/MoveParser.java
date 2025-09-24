package chess.move;

import chess.Position;

/**
 * Interface supplying functionality to construct {@link chess.move.Move}
 * implementation objects from notation Strings.
 * 
 * @author Tobias
 *
 */
public interface MoveParser {
    /**
     * Parses a move from the passed String for the Position
     * 
     * @param pos
     *            The {@link chess.Position position} that makes the context to
     *            generate the move for.
     * @param notation
     *            The notation for the {@link chess.move.Move move} to be
     *            generated.
     * @return The generated {@link chess.move.Move move}.
     */
    public Move parseMove(final Position pos, String notation);
}
