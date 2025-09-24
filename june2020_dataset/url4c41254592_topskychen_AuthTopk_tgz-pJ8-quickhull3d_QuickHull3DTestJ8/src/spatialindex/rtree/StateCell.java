/**
 * 
 */
package spatialindex.rtree;

/**
 * @author chenqian
 *
 */
public class StateCell {

	

	private int id;
	private int status; // 0 means nearer than kth, 
	private boolean isLeafEntry;
	private int parent;
	private int level;
	private double dist;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	
	public StateCell() {
	}

	public StateCell(int id, int status, boolean isLeafEntry, int parent,
			int level, double dist) {
		this.id = id;
		this.status = status;
		this.isLeafEntry = isLeafEntry;
		this.parent = parent;
		this.level = level;
		this.dist = dist;
	}

	public int getLevel() {
		return level;
	}
	
	
	public void setLevel(int level) {
		this.level = level;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public boolean isLeafEntry() {
		return isLeafEntry;
	}

	public void setLeafEntry(boolean isLeafEntry) {
		this.isLeafEntry = isLeafEntry;
	}

	public double getDist() {
		return dist;
	}

	public void setDist(double dist) {
		this.dist = dist;
	}

}
