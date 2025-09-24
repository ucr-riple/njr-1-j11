package darwin.migration;

/**
 * Only allow migration from even-to-even or odd-to-odd
 * @author Kevin Dolan
 */
public class Parity implements Migration {

	@Override
	public int getDestination(int origin, int number) {
		if(origin % 2 == 0) {
			int max = (int) Math.ceil(number / 2.);
			return 2 * (int) (Math.random() * max);
		}
		else {
			int max = number / 2;
			return 1 + 2 * (int) (Math.random() * max);
		}
	}

	
	
}
