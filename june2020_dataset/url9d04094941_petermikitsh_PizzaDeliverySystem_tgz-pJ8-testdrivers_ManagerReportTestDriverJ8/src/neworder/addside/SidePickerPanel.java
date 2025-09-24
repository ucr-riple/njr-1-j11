package neworder.addside;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import main.PDSViewManager;
import model.SideFoodItem;

/**
 * Displays the information of a side food item, including its name and 
 *  price, and provides a textfield and buttons to increase or decease
 *  the quantity of food items desired.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
@SuppressWarnings("serial")
class SidePickerPanel extends JPanel implements ActionListener, KeyListener {

	/**
	 * The default width of this panel.
	 */
	public static final int WIDTH = 165;

	/**
	 * The default height of this panel.
	 */
	public static final int HEIGHT = 90;

	/**
	 * The plus button.
	 */
	private JButton _plusButton;
	
	/**
	 * The minus button.
	 */
	private JButton _minusButton;
	
	/**
	 * The quantity field.
	 */
	private JTextField _quantityField;	

	/**
	 * Creates a new picker panel.
	 * 
	 * @param item 		The SFI to display.
	 * @param quantity  An initial quantity value.
	 */
	public SidePickerPanel( SideFoodItem item, int quantity ) {

		this.setPreferredSize( new Dimension( WIDTH, HEIGHT ) );
		this.setBorder( BorderFactory.createEtchedBorder(EtchedBorder.LOWERED) );
		this.setLayout( new GridBagLayout() );
		GridBagConstraints constraints = new GridBagConstraints();

		// Create a label for the name.
		JLabel nameLabel = new JLabel( item.getName() );
		nameLabel.setFont( new Font( "SansSerif", Font.BOLD, 18 ) );
		this.add( nameLabel, constraints );

		// Create a label for the price.
		JPanel pricePanel = new JPanel();
		pricePanel.setLayout( new GridBagLayout() );
		JLabel priceLabel = new JLabel( "$" + item.getFormattedPrice() );
		priceLabel.setFont( new Font( "SansSerif", Font.PLAIN, 16 ) );
		pricePanel.add( priceLabel, new GridBagConstraints() );
		constraints.gridy = 1;
		constraints.ipady = 5;
		this.add( pricePanel, constraints );
		
		// Add a panel with the buttons and field.
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize( new Dimension( 130, 30 ) );
		bottomPanel.setLayout( new GridLayout( 1, 3, 5, 0 ) );
		constraints.ipady = 0;
		constraints.gridy = 2;
		this.add( bottomPanel, constraints );
		
		_minusButton = new JButton( new ImageIcon( this.getClass().getResource("/resources/minusbutton.png") ) );
		_minusButton.addActionListener( this );
		bottomPanel.add( _minusButton );
		
		_quantityField = new JTextField( "" + quantity );
		_quantityField.setFont( PDSViewManager.DEFAULT_TEXTFIELD_FONT );
		_quantityField.setHorizontalAlignment( JTextField.CENTER );
		_quantityField.addKeyListener( this );
		bottomPanel.add( _quantityField );

		_plusButton = new JButton( new ImageIcon( this.getClass().getResource("/resources/plusbutton.png") ) );
		_plusButton.addActionListener( this );
		bottomPanel.add( _plusButton );
		
	}

	/**
	 * An accessor for the quanity in the quantity textfield.
	 */
	public int getQuantity() {
		// Parse an integer from the quantity field.
		return Integer.parseInt( _quantityField.getText() );
	}

	/**
	 * Adds a keylistener to the quantity textfield.
	 */
	public void addKeyListener( KeyListener kl ) {
		_quantityField.addKeyListener( kl );
	}

	/**
	 * Called when a button is pressed
	 */
	public void actionPerformed(ActionEvent aEvent ) {
		 
		// Check which button was pressed.
		int dQ = 0;
		if( aEvent.getSource() == _plusButton ) {
			dQ = 1;
		} else if( aEvent.getSource() == _minusButton ) { 
			dQ = -1;
		}
		
		// Now, change the value of the textfield by amount dQ;
		int currentQ = getQuantity();
		int newQ = currentQ + dQ;
		if( newQ >= 0 && newQ <= 999 ) {
			_quantityField.setText( "" + newQ );
			
		}
		
	}

	/**
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent arg0) {}

	/**
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent arg0) {

		// Check the text field for validity.
		boolean validInput = true;
		try { 
			int quantity = this.getQuantity();
			if( quantity < 0 || quantity > 999 ) {
				validInput = false;
			}
		} catch( NumberFormatException exc ) {
			validInput = false;
		}
		
		_plusButton.setEnabled( validInput );
		_minusButton.setEnabled( validInput );

	}

	/**
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent arg0) {}

}
