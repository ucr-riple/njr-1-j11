package Utils;

/**
 * Encapsulates the data needed to carry out an animation.
 * 
 * @author Harry Trieu
 */
public abstract class Animation {
	private Location location;
	private int timeToMove;
	
	/**
	 * Constructor
	 * @param l location to move the object to
	 * @param t time you have to move the object to the location
	 */
	public Animation(Location l, int t) {
		location = l;
		timeToMove = t;
	}
	
	public Location getLocation() {
		return(location);
	}
	
	public int getTime() {
		return(timeToMove);
	}
	
	//TODO add this to the Wiki
}
