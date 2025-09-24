package darwin.nodeFilter;


/**
 * Accept all nodes
 * @author Kevin Dolan
 */
public class All extends Filter {

	@Override
	public boolean accept(Filterable node) {
		return true;
	}
}
