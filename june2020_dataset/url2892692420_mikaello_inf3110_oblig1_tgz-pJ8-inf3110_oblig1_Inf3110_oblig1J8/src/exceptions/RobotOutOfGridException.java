package exceptions;

/**
 * Exception to be thrown when someone tries to make the
 * robot go outside the grid.
 * 
 * @author mikaello
 */
public class RobotOutOfGridException extends Exception {
    public RobotOutOfGridException() { 
        printStackTrace();
    }
    
    public RobotOutOfGridException(String message) {
        super(message);
    }
}
