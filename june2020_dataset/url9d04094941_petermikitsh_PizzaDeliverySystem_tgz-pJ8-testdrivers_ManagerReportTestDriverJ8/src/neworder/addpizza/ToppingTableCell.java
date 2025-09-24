package neworder.addpizza;

import gui.scrolltable.ZScrollTableCell;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import main.PDSViewManager;
import model.FoodItem;
import model.Topping;

/**
 * A table view cell that displays the toppings selection options
 * 
 * @author Colleen DiVincenzo   cmd3188@rit.edu
 *
 */
@SuppressWarnings("serial")
public class ToppingTableCell extends ZScrollTableCell 
	implements ActionListener {

	
	/**
	 * The width of a cell.
	 */
	public static final int CELL_WIDTH = 320;
	
	/**
	 * The height of a cell.
	 */
	public static final int CELL_HEIGHT = 62;
	
	/**
	 * The radio button that represents toppings on the
	 * whole pizza.
	 */
	public JButton wholeButton;
	
	/**
	 * The radio button that represents toppings on the left 
	 * side of the pizza.
	 */
	public JButton leftButton;
	
	/**
	 * The radio button that represent toppings on right side
	 * of the pizza.
	 */
	public JButton rightButton;
	
	
	/**
	 * The currently selected location. Defaults to whole.
	 */
	private JButton selectedButton = wholeButton;
	
	/**
	 * The combo box that contains the topping selected.
	 */
	private JComboBox toppingOptions;
	
	/**
	 * The panel that holds all of the toppings location
	 * buttons.
	 */
	private JPanel toppingsLocationPanel = new JPanel();
	
	/**
	 * Local cache of the available options.
	 */
	private ArrayList<String> _toppingsNames = new ArrayList<String>();
	
	/**
	 * Reference to the add pizza gui view
	 */
	private AddPizzaViewGUI guiView;
	
	/**
	 * Creates a new topping options cell 
	 */
	public ToppingTableCell( AddPizzaViewGUI view ) {

		this.setSize( CELL_WIDTH, CELL_HEIGHT );
		this.setLayout( new FlowLayout() );
		
		guiView = view;

		
		// Collects available toppings names
		ArrayList<FoodItem> _availToppings = Topping.getDb().list();
		for (FoodItem topp : _availToppings){
			_toppingsNames.add(topp.getName());
		}
		int index = 0;
		for (String toppName: _toppingsNames){
			if (toppName.equalsIgnoreCase("pepperoni")){
				_toppingsNames.remove(index);
				_toppingsNames.add(0, toppName);
			}
			index ++;
		}
		
		// Create a JComboBox for the available toppings.
		toppingOptions = new JComboBox( _toppingsNames.toArray());
		toppingOptions.setFont( PDSViewManager.DEFAULT_TEXTFIELD_FONT );
		toppingOptions.setPreferredSize( new Dimension( 100, 40));
		
		this.add(toppingOptions);

		// Create das buttons.
		wholeButton = new JButton();
		leftButton = new JButton();
		rightButton = new JButton();
		
		// Set names for the buttons.
		wholeButton.setToolTipText( "Whole" );
		leftButton.setToolTipText( "Left" );
		rightButton.setToolTipText( "Right" );
		
		// Remove borders.
		wholeButton.setBorder( null );
		leftButton.setBorder( null );
		rightButton.setBorder( null );
		
		// Remove backgrounds.
		wholeButton.setBackground( null );
		leftButton.setBackground( null );
		rightButton.setBackground( null );
		
		// Set an "unselected" icon.
		wholeButton.setIcon( new ImageIcon( this.getClass().getResource("/resources/deselectedtoppingwholeicon.png") ) );
		leftButton.setIcon( new ImageIcon( this.getClass().getResource("/resources/deselectedtoppinglefticon.png") ) );
		rightButton.setIcon( new ImageIcon( this.getClass().getResource("/resources/deselectedtoppingrighticon.png") ) );
		
		// Set the "selected" icon.
		wholeButton.setSelectedIcon( new ImageIcon( this.getClass().getResource("/resources/selectedtoppingwholeicon.png") ) );
		leftButton.setSelectedIcon( new ImageIcon( this.getClass().getResource("/resources/selectedtoppinglefticon.png") ) );
		rightButton.setSelectedIcon( new ImageIcon( this.getClass().getResource("/resources/selectedtoppingrighticon.png") ) );
		
		// Add action listeners.
		wholeButton.addActionListener(this);
		leftButton.addActionListener(this);
		rightButton.addActionListener(this);
		
		wholeButton.setPreferredSize( new Dimension( 40, 40));
		leftButton.setPreferredSize( new Dimension( 40, 40));
		rightButton.setPreferredSize( new Dimension( 40, 40));
		
		ButtonGroup toppingsLocationGroup = new ButtonGroup();
		toppingsLocationGroup.add(wholeButton);
		toppingsLocationGroup.add(leftButton);
		toppingsLocationGroup.add(rightButton);
		
		// Select the whole button by default.
		this.selectedButton = wholeButton;
		wholeButton.setSelected( true );
			
		toppingsLocationPanel.add(leftButton);
		toppingsLocationPanel.add(wholeButton);
		toppingsLocationPanel.add(rightButton);
		
		this.add(toppingsLocationPanel);
	}
	
	public JComboBox getToppingOptions(){
		return toppingOptions;
	}
	
	public String getToppingLocationKey() {
		
		// Check the current selection.
		String name = selectedButton.getToolTipText();
		
		if( name.equals( "Whole" ) ) {
			return AddPizzaView.WHOLE_KEY;
		} else if( name.equals( "Left" ) ) {
			return AddPizzaView.LEFT_KEY;
		} else if( name.equals( "Right" ) ) {
			return AddPizzaView.RIGHT_KEY;
		} 
		
		// Default to whole (this shouldn't happen).
		return AddPizzaView.WHOLE_KEY;
		
	}
	
	public JButton getSelectedButton(){
		return selectedButton;
	}
	
	public void setSelectedButton(JButton sideButton){
		
		if( selectedButton != null ) {
			selectedButton.setSelected( false );
		}
		
		this.selectedButton = sideButton;
		sideButton.setSelected( true );
		
		guiView.updateProgressBars();
		
	}
	
	public JPanel getToppingsLocationPanel(){
		return toppingsLocationPanel;
	}
	
	public ArrayList<JButton> getSideButtons(){
		
		ArrayList<JButton> sideButtons = new ArrayList<JButton>();
		sideButtons.add(wholeButton);
		sideButtons.add(leftButton);
		sideButtons.add(rightButton);
		
		return sideButtons;
	}

	@Override
	public void actionPerformed(ActionEvent aEvent) {

		// Store the button that was pressed.
		if( aEvent.getSource() instanceof JButton ) {
			
			setSelectedButton( (JButton)aEvent.getSource() );
			
		}
		
	}
}
