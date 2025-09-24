package karlney.tetris.core;

/**
 * TODO javadoc
 * <p/>
 * Date: 2012-11-01
 * Time: 15:15
 *
 * @author karl.neyvaldt
 */
public interface Piece {

    /**
     * This method will rotate the piece if its possible, otherwise it will do nothing
     * @return true if the rotate was successful, false otherwise
     */
    boolean rotateIfPossible();

    /**
     * This method will move the piece down one row, or if the piece is in its lowest possible position (i.e it has hit the 'column floor')
     * then it will do nothing.
     *
     * Note that this method will not fix the piece to the board but only return true or false as a hint to the user to take further action.
     *
     * Also note that this method will not return true immediately but only after the second time the 'column floor' is hit at the same position,
     * this allows the player to continue to move and rotate the piece some more even after the first contact of the 'column floor' is made,
     * which makes the game a lot easier and arguably more fun as well :-)
     *
     * @return true if the piece can not move further and should be fixed to the board, false if its still movable
     */
    boolean moveDown();

    /**
     * This method will move the piece to the left or to the right (as given by dir param) if possible, otherwise it will do nothing
     * @param dir either LEFT or RIGHT, all other values will do nothing
     */
    void moveSideWays(PlayerInput dir);

    /**
     * This method will set the falling flag to true, preventing this piece to accept any further interactions with it
     */
    void drop();

    /**
     *
     * @return true if this piece is falling down
     */
    boolean isDropped();


    /**
     * Moves the piece down as far as possible (useful for AI planning)
     */
    void stepDownAFAP();


    /**
     *
     * @return the size of the bounding box of the piece, all pieces have square bounding boxes (2x2, 3x3 or 4x4)
     */
    int getSize();

    /**
     * Returns if a given Square at position (i,j) realive to the piece shape origin is filled
     * @param i
     * @param j
     * @return true if the Square at the given position (i,j) is filled
     */
    boolean isFilled(int i, int j);

    /**
     * @return the x (column) position of the origin of this piece.
     * NOTE that the origin is in the upper left corner and NOT in the middle of the piece
     */
    int getX();

    /**
     * @return the y (row) position of the origin of this piece.
     * NOTE that the origin is in the upper left corner and NOT in the middle of the piece
     */
    int getY();

    /**
     * Returns the number of possible orientations for this piece. For O piece this is 0, for I,Z and S it is 2 and for L, J and T it is 4.
     * Too see more about rotations please look here: http://harddrop.com/wiki/TGM_Rotation
     *
     * @return the number of possible rotations for this piece.
     */
    int getPossibleOrientations();

    /**
     *
     * @return
     */
    int getOrientation();

    PieceType getType();

    Square[][] getShape();

    Square getSquare(int i, int j);

    /**
     * This method returns a copy of this Piece with its current shape (that could already be rotated!)
     * and at the given position (x,y) on the given Board.
     * This method moves and rotates the copy without checking against the board if the position is valid
     *
     * @param x the x position
     * @param y the y position
     * @param deltaRotation the new rotation (relative to the current rotation), 0 means current rotation, 1 means to rotate one time, 2 two times etc.
     * @param board the board the copy piece should be attached to
     *
     * @return a new Piece that is a translated copy of this Piece
     */
    Piece getTranslatedCopy(int x, int y, int deltaRotation, Board board);

}
