package gui.scrolltable;

import javax.swing.JPanel;

public class ZScrollTableCell extends JPanel {

	/**
	 * The serial ID of this class.
	 */
	private static final long serialVersionUID = 1725970309798344366L;
	
	/**
	 * An integer identifier for this cell (usually the index in the table).
	 */
	private int _tag = 0;
	
	/**
	 * Getter for the tag.
	 * 
	 * @return This cell's tag.
	 */
	public int getTag() {
		return _tag;
	}
	
	/**
	 * Setter for the tag.
	 * 
	 * @param tag	The new tag.
	 */
	public void setTag( int tag ) {
		_tag = tag;
	}

}
