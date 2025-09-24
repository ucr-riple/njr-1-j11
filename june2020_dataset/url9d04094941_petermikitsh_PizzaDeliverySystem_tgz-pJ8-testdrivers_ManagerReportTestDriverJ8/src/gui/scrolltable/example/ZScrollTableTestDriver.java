package gui.scrolltable.example;

import gui.scrolltable.ZScrollTable;
import gui.scrolltable.ZScrollTableCell;
import gui.scrolltable.ZScrollTableDelegate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * An example use case of a ZScrollTable
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public class ZScrollTableTestDriver implements ZScrollTableDelegate {
	
	/**
	 * The width of a cell.
	 */
	private static final int CELL_WIDTH = 400;
	
	/**
	 * The height of a cell.
	 */
	private static final int CELL_HEIGHT = 100;
	
	/**
	 * A label to display a message.
	 */
	private static JLabel infoLabel;
	
	/**
	 * This is the array from which this class draws its data.
	 */
	private static ArrayList<String> stringList;
		
	public static void main( String[] args ) {
		
		// Create the data source.
		ZScrollTableTestDriver.stringList = new ArrayList<String>();
		for( int i = 0; i < 3; i++ ) {
			ZScrollTableTestDriver.stringList.add( "StringData" + i);
		}
			
		// Create a window.
		JFrame customerFrame = new JFrame();
		
		// Get the container of this frame.
	    Container container = customerFrame.getContentPane();
	    container.setLayout( new BorderLayout() );
		
	    customerFrame.setSize( 640, 480);
	    customerFrame.setLocation( 300, 150 );
	    customerFrame.setResizable( false );
		
		// Set the default close operation and title of the window.
	    customerFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    customerFrame.setTitle( "ScrollTableExample" );
	    
	    // Create a new ZScrollTable and add it to the view.
	    ZScrollTable table = new ZScrollTable( new ZScrollTableTestDriver() );
	    table.setPreferredSize( new Dimension( CELL_WIDTH, CELL_WIDTH ) );
	    container.add( table, BorderLayout.CENTER );
	    table.updateTable();
	    
	    // Create the info label and add it.
	    ZScrollTableTestDriver.infoLabel = new JLabel();
	    container.add( ZScrollTableTestDriver.infoLabel, BorderLayout.SOUTH );
		
		// Finally, set the window as visible.
	    customerFrame.setVisible( true );
			
	}
	
	
	public int getNumberOfCells(ZScrollTable table) {
		return ZScrollTableTestDriver.stringList.size();
	}
	
	public int getCellWidth( ZScrollTable table ) {
		return CELL_WIDTH;
	}
	
	public int getCellHeight(ZScrollTable table, int index ) {
		return CELL_HEIGHT + 20 * index ;
	}
	
	
	public int getCellSpacing(ZScrollTable table) {
		return 2;
	}
	
	public ZScrollTableCell getHeaderCell( ZScrollTable table ){

		// Create a new table cell.
		SimpleTableCell cell = new SimpleTableCell( CELL_WIDTH, CELL_HEIGHT );
		cell.setMainLabelText( "HEADER!" );
		cell.setBackground( Color.BLUE );
		
		return cell;

	}
	
	public String getEmptyTableMessage( ZScrollTable table ) {
		
		return "There are no cells here!  Why did you delete all of them?  What a terrible idea.";
		
	}

	public Color getTableBGColor(ZScrollTable table) {
		return Color.BLACK;
	}
	
	
	public ZScrollTableCell getCell(ZScrollTable table, int index) {
		
		// Get the string data for this cell.
		String data = ZScrollTableTestDriver.stringList.get(index);
		
		// Create a new table cell.
		SimpleTableCell cell = new SimpleTableCell( CELL_HEIGHT,
													CELL_WIDTH + 20 * index );
		
		// Set the displayed text in the cell.
		cell.setMainLabelText( "Data in this cell: " + data );
		cell.setDetailLabelText( "This the detailed information for " + data );
		
		// Shade the cells according to the index (so it alternates between
		// dark and light).
		int shade = 220 + (index % 2) * 20;
		cell.setBackground( new Color( shade, shade, shade ) );
		
		return cell;
		
	}
	
	
	public void cellWasClicked(ZScrollTable table, int index) {
		
		// Update the info label.
		ZScrollTableTestDriver.infoLabel.setText( 
				ZScrollTableTestDriver.stringList.get(index)+ " was clicked!" );
		
		table.scrollToBottom();

	}
	
	
	public boolean canDeleteCell(ZScrollTable table, int index) {
		
		return true;
		
	}
	
	
	public void cellWasDeleted(ZScrollTable table, int index) {
		
		// Delete the String from the data list.
		ZScrollTableTestDriver.stringList.remove(index);
		
	}
	
}
