package darwin.migration;

/**
 * The migration interface abstracts away the work of
 * a migration so that different types of migrations
 * can easily be implemented
 * @author Kevin Dolan
 */
public interface Migration {

	/**
	 * Return the index of the population to migrate the individual to
	 * @param origin the index that the population comes from
	 * @param number the total number of populations
	 * @return		 the index of the population to send the individual to
	 */
	public int getDestination(int origin, int number);
	
}
