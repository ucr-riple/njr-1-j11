package darwin.migration;

/**
 * Migrate with a uniform distribution
 * @author Kevin Dolan
 */
public class Uniform implements Migration {

	@Override
	public int getDestination(int origin, int number) {
		return (int) (Math.random() * number);
	}

}
