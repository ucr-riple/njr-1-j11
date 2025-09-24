package menueditor;

import java.awt.*;
import javax.swing.*;

import model.Topping;
import gui.scrolltable.ZScrollTableCell;

/**
 * A table view cell that displays a menu topping.
 * 
 * @author  Casey Klimkowsky	cek3403@g.rit.edu
 */
@SuppressWarnings("serial")
public class ToppingTableCell extends ZScrollTableCell {

	/**
	 * The width of a cell.
	 */
	public static final int CELL_WIDTH = 300;
	
	/**
	 * The height of a cell.
	 */
	public static final int CELL_HEIGHT = 50;
	
	/**
	 * Creates a new cell from the given pizza topping.
	 * 
	 * @param topping 
	 * 		The topping whose data should appear here.
	 */
	public ToppingTableCell(Topping topping) {
		
		this.setSize( CELL_WIDTH, CELL_HEIGHT );
		this.setLayout( null );
		
		// Create a JLabel for the topping's name.
		JLabel nameLabel = new JLabel( topping.getName() );
		nameLabel.setFont( new Font("SansSerif", Font.PLAIN, 16 ) );
		this.add(nameLabel);
		nameLabel.setBounds( 15, -75, 500, 200 );
		
	}
	
}
