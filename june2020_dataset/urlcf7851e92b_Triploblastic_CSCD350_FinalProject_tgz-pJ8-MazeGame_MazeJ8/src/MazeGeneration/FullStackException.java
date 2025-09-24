package MazeGeneration;

/**
 * Runtime exception thrown when the capacity of the array used by an
 * ArrayStack has been exceeded.
 * @see ArrayStack
 */
public class FullStackException extends RuntimeException {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public FullStackException(String err) {
    super(err);
  }
}
