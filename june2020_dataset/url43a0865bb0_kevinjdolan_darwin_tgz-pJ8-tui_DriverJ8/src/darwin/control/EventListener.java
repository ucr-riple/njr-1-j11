package darwin.control;

/**
 * This interface is notified whenever some kind of event occurs in Darwin
 * @author Kevin Dolan
 */
public interface EventListener {

	/**
	 * Called whenever a generation completes
	 * @param generation which generation has completeded
	 */
	public void generationCompleted(Darwin darwin);
	
}
