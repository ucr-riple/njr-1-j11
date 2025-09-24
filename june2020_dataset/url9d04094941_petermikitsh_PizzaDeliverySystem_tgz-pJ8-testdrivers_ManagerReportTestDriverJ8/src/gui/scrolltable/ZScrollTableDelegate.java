package gui.scrolltable;

import java.awt.Color;

/**
 * This interfaces provides the methods needed for a delegate to a ZScrollTable
 *  object.  The responsibilities of a table delegate include providing
 *  information about a table (in particular, the cells that it contains)
 *  as well as responding to events that the user performs on table.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public interface ZScrollTableDelegate {
	
	/**
	 * Returns a nonnegative integer indicating the number of cells in
	 *  the given table.
	 *  
	 * @param table 	The table in question.
	 * @return The number of cells in the table.
	 */
	public int getNumberOfCells( ZScrollTable table );

	/**
	 * Returns the (uniform) spacing between each cell in the given table.
	 * 
	 * @param table		The table in question.
	 * @return The distance (in pixels) between each cell.
	 */
	public int getCellSpacing( ZScrollTable table );
	
	/**
	 * Returns the background color of the given table.
	 * 
	 * @param table		The table in question.
	 * @return The background color of the table.
	 */
	public Color getTableBGColor( ZScrollTable table );
	
	/**
	 * Returns the header cell of this table.  This can be null to avoid
	 *  drawing a table.
	 *  
	 * @param table		The table in question.
	 * @return The cell that will be displayed as a header. 
	 */
	public ZScrollTableCell getHeaderCell( ZScrollTable table );
	
	/**
	 * Returns a string that is displayed in the table when there are no cells.
	 * 
	 * @param table		The table in question.
	 * @return A string containing the message for empty tables. 
	 */
	public String getEmptyTableMessage( ZScrollTable table );
	
	/**
	 * Constructs and returns the cell of the table at the given index.  
	 *  Note: the cells for each index must be distinct objects.
	 *  
	 * @param table		The table in question.
	 * @param index 	The index of the cell which is needed.
	 * @return A ZScrollTableCell containing the appropriate data.
	 */
	public ZScrollTableCell getCell( ZScrollTable table, int index );
	
	/**
	 * Returns the (uniform) width of a cell in the table.
	 * @param table		The table in question.	
	 * @return The width of a cell in the table.
	 */
	public int getCellWidth( ZScrollTable table );
	
	/**
	 * Returns the height of the given cell in the given table.
	 *  
	 * @param table		The table in question.	
	 * @param index 	The index of the cell whose size is needed.
	 * @return The height of the cell in the table.
	 */
	public int getCellHeight( ZScrollTable table, int index );
	
	
	/**
	 * Called when the cell of a table is clicked by the user.
	 * 
	 * @param table		The table containing the cell that was clicked.
	 * @param index		The index of the cell that was clicked.
	 */
	public void cellWasClicked( ZScrollTable table, int index );
	
	/**
	 * Indicates if the cell at the given index can be deleted.  (If yes,
	 *  a delete button will be added to the cell.
	 * 
	 * @param table 	The table containing the cell in question.
	 * @param index		The index of the cell in question.
	 * @return Whether or not the cell should display a delete button.
	 */
	public boolean canDeleteCell( ZScrollTable table, int index );
	
	/**
	 * Called when the cell of a table is deleted. 
	 * 
	 * @param table		The table containing the cell that was deleted.
	 * @param index		The index of the cell that was deleted.
	 */
	public void cellWasDeleted( ZScrollTable table, int index );
	
}
