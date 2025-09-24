package chess.move;

import chess.ChessCoord;
import chess.Position;

/**
 * Interface representing a move that can be played in a chess game.
 * 
 * @author Tobias
 * 
 */
public interface Move {
    // IMPROVE Copying: Copying positions is very expensive and we don't need
    // that until we want to go concurrent in our application
    /*
     * Calculates the resulting position.
     * 
     * @param pos Position in which the move is played.
     * 
     * @return New Object of resulting position.
     */
    // public Position afterMove(final Position pos);

    /*
     * Calculates the position before the move. It undoes the move.
     * 
     * @param pos Position from which the move has to be undone.
     * 
     * @return New Object of position before move.
     */
    // public Position beforeMove(final Position pos);

    /**
     * Changes the position to the resulting position after the move.
     * 
     * @param pos
     *            Position in which the move is played.
     */
    public void doMove(final Position pos);

    /**
     * Changes the position to the position before the move.
     * 
     * @param pos
     *            Position in which the move is played.
     */
    public void undoMove(final Position pos);

    /**
     * Retrieves the move's source code.
     * 
     * @return Returns the coordinate from which the piece is moved.
     */
    public ChessCoord getSourceCoord();

    /**
     * Retrieves the move's target code.
     * 
     * @return Returns the coordinate to which the piece is moved.
     */
    public ChessCoord getTargetCoord();
}
