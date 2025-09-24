package darwin.migration;

/**
 * Migrate to the immediate next population
 * @author Kevin Dolan
 */
public class Next implements Migration {

	@Override
	public int getDestination(int origin, int number) {
		int dest = origin + 1;
		if(dest == number)
			dest = 0;
		return dest;
	}

}
