package karlney.tetris.core;

/**
 * TODO javadoc
 * <p/>
 * Date: 2012-11-02
 * Time: 15:06
 *
 * @author karl.neyvaldt
 */
public class UnableToPlacePieceException extends Exception {

    public UnableToPlacePieceException(String msg, Exception e) {
        super(msg,e);
    }

}
