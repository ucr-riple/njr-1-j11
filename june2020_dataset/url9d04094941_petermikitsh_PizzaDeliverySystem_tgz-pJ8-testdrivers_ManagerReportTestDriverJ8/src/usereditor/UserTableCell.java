package usereditor;

import gui.scrolltable.ZScrollTableCell;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import model.User;
import model.User.UserPermissions;

/**
 * A table view cell that displays a user's information.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
@SuppressWarnings("serial")
public class UserTableCell extends ZScrollTableCell {

	/**
	 * The width of a cell.
	 */
	public static final int CELL_WIDTH = 300;
	
	/**
	 * The height of a cell.
	 */
	public static final int CELL_HEIGHT = 75;
	
	/**
	 * Creates a new cell from the given user 
	 * 
	 * @param user The user whose data should appear here.
	 */
	public UserTableCell(User user) {

		this.setSize( CELL_WIDTH, CELL_HEIGHT );
		this.setLayout( null );

		// Create a JLabel for the users name.
		JLabel nameLabel = new JLabel( user.getName() );
		nameLabel.setFont( new Font("SansSerif", Font.PLAIN, 22 ) );
		this.add(nameLabel);
		nameLabel.setBounds( 15, -80, 500, 200 );

		// Create a JLabel for the user's permissions.
		boolean isAdmin = ( user.getPermissions() == UserPermissions.ADMIN );
		JLabel permissionsLabel = new JLabel( "(" + 
						(isAdmin ? "Administrator" : "Non-Administrator" )
											 + ")" ); 
		permissionsLabel.setFont( new Font("Plain", Font.PLAIN, 13 ) );
		permissionsLabel.setForeground( new Color( 50, 50, 50 ) );
		this.add(permissionsLabel);
		permissionsLabel.setBounds( 35, -57, 500, 200 );

		// Create a JLabel for the customer's phone number
		JLabel loginIDLabel = new JLabel( user.getLoginId() + ", " + user.getPassword() );
		loginIDLabel.setFont( new Font("Monospaced", Font.PLAIN, 13 ) );
		loginIDLabel.setForeground( new Color( 50, 50, 50 ) );
		this.add(loginIDLabel);
		loginIDLabel.setBounds( 35, -40, 500, 200 );	

	}

}
