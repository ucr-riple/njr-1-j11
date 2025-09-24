package darwin.work.threadPool;

/**
 * Persist a tuple of two values
 * @author Owner
 *
 * @param <T> the first value type
 * @param <U> the second value type
 */
public class Pair<T, U> {

	private T first;
	private U second;
	
	/**
	 * Create a new pair
	 * @param first  the first value
	 * @param second the second value
	 */
	public Pair(T first, U second)
	{
		this.first = first;
		this.second = second;
	} 
	
	/**
	 * @return the first value
	 */
	public T fst() {
		return first;
	}
	
	/**
	 * @return the second value
	 */
	public U snd() {
		return second;
	}
	
}
