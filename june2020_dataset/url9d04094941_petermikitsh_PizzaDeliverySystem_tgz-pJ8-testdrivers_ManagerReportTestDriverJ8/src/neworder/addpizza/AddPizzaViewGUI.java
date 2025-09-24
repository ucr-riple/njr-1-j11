package neworder.addpizza;

import gui.scrolltable.ZScrollTable;
import gui.scrolltable.ZScrollTableCell;
import gui.scrolltable.ZScrollTableDelegate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import main.PDSViewManager;
import model.PizzaFoodItem;
import model.PizzaFoodItem.PizzaSize;
import model.Topping;
import viewcontroller.GeneralViewGUI;

public class AddPizzaViewGUI extends AddPizzaView
	implements GeneralViewGUI, ZScrollTableDelegate, KeyListener {
	
	/**
	 * The name of the add to order button.
	 */
	public static final String ADD_TO_ORDER_BUTTON_NAME = "Add to Order";
	
	/**
	 * The name of the size small button.
	 */
	public static final String SMALL_BUTTON = "Small";
	
	/**
	 * The name of the size medium button.
	 */
	public static final String MEDIUM_BUTTON = "Medium";
	
	/**
	 * The name of the size large button.
	 */
	public static final String LARGE_BUTTON = "Large";
	
	/**
	 * The name of the add more toppings button.
	 */
	public static final String ADD_MORE_TOPPINGS_BUTTON = "Add Topping";
	
	/**
	 * The JPanel to which contains everything in this view.
	 */
	private JPanel _mainPanel;
	
	/**
	 * The panel that displays the size options.
	 */
	private JPanel _sizeOptionsPanel;
	
	/** 
	 * The table that displays the topping selections.
	 */
	private ZScrollTable _toppingsOptionsTable;
	
	/**
	 * The panel that holds the toppings options
	 */
	private JPanel _toppingsOptionsPanel;
	
	/**
	 * The panel that hold the current number of toppings
	 * on each side of the pizza
	 */
	private JPanel _numToppingsPanel;
	
	/**
	 * The number of toppings on the left of the pizza
	 */
	private int numToppLeft = 0;
	
	/**
	 * The number of toppings on the right of the pizza
	 */
	private int numToppRight = 0;
	
	/**
	 * The panel that displays the quantity text field.
	 */
	private JPanel _quantityPanel;
	
	/**
	 * The textfield that displays the quantity of pizzas.
	 */
	private JTextField _quantityField;
	
	/**
	 * The label that prints instructions.
	 */
	private JLabel _instructionsLabel;

	/**
	 * The label that prints error data.
	 */
	private JLabel _errorLabel;	
	
	/**
	 * The size of the pizza
	 */
	private PizzaSize size = PizzaFoodItem.PizzaSize.PizzaSizeMedium;
	
	/**
	 * The small pizza button
	 */
	private JButton smallPizzaButton = new JButton(AddPizzaViewGUI.SMALL_BUTTON);
	
	/**
	 * The medium pizza button
	 */
	private JButton mediumPizzaButton = new JButton(AddPizzaViewGUI.MEDIUM_BUTTON);
	
	/**
	 * The large pizza button
	 */
	private JButton largePizzaButton = new JButton(AddPizzaViewGUI.LARGE_BUTTON);
	
	/**
	 * The selected size button
	 */
	private JButton _selectedSizeButton = mediumPizzaButton;
	
	/**
	 * The add to order button
	 */
	private JButton _addToOrderButton = new JButton( AddPizzaViewGUI.ADD_TO_ORDER_BUTTON_NAME);
	
	/**
	 * Checks to make sure the order is completely filled.
	 */
	private boolean validQuantity = false;
	
	/**
	 * Progress bar to indicate the number of toppings on the left side of the pizza.
	 */
	private JProgressBar _leftToppings;
	
	/**
	 * Progress bar to indicate the number of toppings on the left side of the pizza.
	 */
	private JProgressBar _rightToppings;
	
	
	/**
	 * A local list of the cells in the toppings options.
	 */
	private ArrayList<ToppingTableCell> toppingsOptionsCells = 
		new ArrayList<ToppingTableCell>();
	
	/**
	 * A boolean that indicates whether or not you are modifying a pizza
	 */
	private boolean modifying = false;
	
	/**
	 * If you are modifying a pizza, this is the original pizza.
	 */
	private PizzaFoodItem originalPizzaToModify;
	
	
	/**
	 * The default constructor.  Does Swing-related setup.
	 */
	public AddPizzaViewGUI() {
		
		// Initialize the main panel.
		_mainPanel = new JPanel();
		_mainPanel.setLayout( new BorderLayout() );
		_mainPanel.setName( "New Order: Add Pizza" );
		
		// Create a panel for the center.
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout( new GridBagLayout() );
		_mainPanel.add( centerPanel, BorderLayout.CENTER );
		
		// Create a label to show the instructions.
		_instructionsLabel = new JLabel();
		_instructionsLabel.setText("Select the pizza size, toppings, and topping locations. Then enter a quantity and add pizza(s) to order.");
		JPanel _instructionsPanel = new JPanel();
		_instructionsPanel.add( _instructionsLabel );
		_mainPanel.add( _instructionsPanel, BorderLayout.NORTH );

		// Create a label to hold the error output.
		_errorLabel = new JLabel();
		_errorLabel.setForeground( Color.RED );
		JPanel errorPanel = new JPanel();
		errorPanel.add( _errorLabel );
		_mainPanel.add( errorPanel, BorderLayout.SOUTH );
		
		JPanel eastPanel = new JPanel();
		JPanel westPanel = new JPanel();
		_mainPanel.add( eastPanel, BorderLayout.EAST );
		_mainPanel.add( westPanel, BorderLayout.WEST );
		
		//Create panels for the three areas of the center panel.
		createSizeOptionsPanel();
		GridBagConstraints sizeOptionsConstraints = new GridBagConstraints();
		sizeOptionsConstraints.gridheight = 5;
		sizeOptionsConstraints.gridwidth = 1;
		sizeOptionsConstraints.insets =  new Insets( 5, 5, 5, 5 );
		sizeOptionsConstraints.fill = GridBagConstraints.BOTH;
		sizeOptionsConstraints.ipady = 50;
		sizeOptionsConstraints.ipadx = 50;
		centerPanel.add( _sizeOptionsPanel, sizeOptionsConstraints );
		
		createToppingsOptionsPanel();
		GridBagConstraints toppingsOptionsConstraints = new GridBagConstraints();
		toppingsOptionsConstraints.gridheight = 4;
		toppingsOptionsConstraints.gridwidth = 5;
		//toppingsOptionsConstraints.weightx = .5;
		toppingsOptionsConstraints.gridx = 1;
		toppingsOptionsConstraints.gridy = 0;
		toppingsOptionsConstraints.insets =  new Insets( 5, 5, 5, 5 );
		//toppingsOptionsConstraints.ipadx = 50;
		toppingsOptionsConstraints.fill = GridBagConstraints.BOTH;
		centerPanel.add( _toppingsOptionsPanel, toppingsOptionsConstraints);
		
		
		createQuantityPanel();
		GridBagConstraints quantityConstraints = new GridBagConstraints();
		quantityConstraints.gridheight = 3;
		quantityConstraints.gridwidth = 6;
		quantityConstraints.gridx = 0;
		quantityConstraints.gridy = 4;
		quantityConstraints.ipady = 15;
		quantityConstraints.insets =  new Insets( 5, 5, 5, 5 );
		quantityConstraints.fill = GridBagConstraints.BOTH;
		centerPanel.add( _quantityPanel, quantityConstraints);
		
		
	}
	
	/**
	 * The constructor used to build the modify pizza screen. Loads the
	 * add pizza screen but populates with the information already 
	 * recorded about the pizza to be modified. Swing related setup.
	 */
	public AddPizzaViewGUI( PizzaFoodItem currentPizza ){
		this();
		modifying = true;
		originalPizzaToModify = currentPizza;

		//Populates size panel with the size previously selected
		PizzaFoodItem.PizzaSize currentSize = currentPizza.getSize();
		size = currentSize;
		switch(size){
		
			case PizzaSizeSmall:
				_selectedSizeButton.setBackground(null);
				smallPizzaButton.setBackground(Color.lightGray);
				_selectedSizeButton = smallPizzaButton;
				break;
				
						
			case PizzaSizeMedium:
				break;

			case PizzaSizeLarge:
				_selectedSizeButton.setBackground(null);
				largePizzaButton.setBackground(Color.lightGray);
				_selectedSizeButton = largePizzaButton;
				break;
		}
		
		//Populates toppings options panel with previously selected toppings
		toppingsOptionsCells = new ArrayList<ToppingTableCell>();
		ArrayList<Topping> currentToppings = currentPizza.getToppings();
		for(Topping t : currentToppings){
			ToppingTableCell newTopp = new ToppingTableCell(this);
			JComboBox toppingType = newTopp.getToppingOptions();
			String toppingName = t.getName();
			toppingType.setSelectedItem(toppingName);
			
			Topping.ToppingLocation currentSide = t.getLocation();
			ArrayList<JButton> sideOptionsButtons = newTopp.getSideButtons();
			
			switch(currentSide){
			
				case ToppingLocationWhole:
					newTopp.setSelectedButton(sideOptionsButtons.get(0));
					break;
					
				case ToppingLocationLeft:
					newTopp.setSelectedButton(sideOptionsButtons.get(1));
					break;
					
				case ToppingLocationRight:
					newTopp.setSelectedButton(sideOptionsButtons.get(2));
					break;
					
					
			}
			toppingsOptionsCells.add(newTopp);
		}
		_toppingsOptionsTable.updateTable();
		updateProgressBars();
		
		_addToOrderButton.setText("Done");
		
		//Grays out the quantity field and defaults it to 1
		_quantityField.setText("1");
		_quantityField.setEnabled(false);
		validQuantity = true;
		
		_instructionsLabel.setText("Make modifications to the size, toppings, and topping locations for the current pizza.");
	}
		
	
	/**
	 * Initializes the toppings options panel.
	 */
	private void createToppingsOptionsPanel(){
		_toppingsOptionsPanel = new JPanel();
		_toppingsOptionsPanel.setLayout( new GridBagLayout() );
		_toppingsOptionsPanel.setBorder( BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				"Toppings Options" ) );
		
		//Sets up the table of toppings options
		_toppingsOptionsTable = new ZScrollTable( this );
		GridBagConstraints toppingsOptionsConstraints = 
			new GridBagConstraints();
		toppingsOptionsConstraints.gridx = 0;
		toppingsOptionsConstraints.gridy = 0;
		toppingsOptionsConstraints.insets = new Insets(0,15,0,15);
		_toppingsOptionsTable.setPreferredSize( new Dimension( ToppingTableCell.CELL_WIDTH, 300 ) );
		_toppingsOptionsPanel.add( _toppingsOptionsTable, toppingsOptionsConstraints );
		
		//Creates and adds a toppings options button to the table
		ToppingTableCell toppingOption = new ToppingTableCell(this);
		
		toppingsOptionsCells.add( toppingOption );
		_toppingsOptionsTable.updateTable();
		
		
		
		//Adds the add more toppings button
		JButton _addMoreToppingsButton = 
			new JButton(AddPizzaViewGUI.ADD_MORE_TOPPINGS_BUTTON);
		_addMoreToppingsButton.addActionListener(this);
		_addMoreToppingsButton.setFont( PDSViewManager.DEFAULT_BUTTON_FONT );
		
		//Create a bottom toppings options panel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout( new FlowLayout());
		bottomPanel.add(_addMoreToppingsButton);
		GridBagConstraints bottomPanelConstraints = 
			new GridBagConstraints();
		bottomPanelConstraints.gridx = 0;
		bottomPanelConstraints.gridy = 1;
		_toppingsOptionsPanel.add(bottomPanel, bottomPanelConstraints);
		
		//Add the number of toppings panel
		createNumberOfToppingsPanel();
		GridBagConstraints progressPanelConstraints = 
			new GridBagConstraints();
		progressPanelConstraints.gridx = 1;
		progressPanelConstraints.gridy = 0;
		toppingsOptionsConstraints.insets = new Insets(0,0,0,20);
		
		_toppingsOptionsPanel.add(_numToppingsPanel, progressPanelConstraints);
	}
	
	private void createNumberOfToppingsPanel(){
		
		_numToppingsPanel = new JPanel();
		_numToppingsPanel.setLayout( new GridBagLayout());
		
		_leftToppings = new JProgressBar(JProgressBar.VERTICAL, 0, 10);
		_leftToppings.setFont(new Font( "SansSerif", Font.BOLD, 16 ));
		_leftToppings.setStringPainted(true);
		_leftToppings.setPreferredSize(new Dimension(25, 300));
		_leftToppings.setForeground(new Color(190,220,135));
		GridBagConstraints leftToppingsConstraints = new GridBagConstraints();
		leftToppingsConstraints.insets = new Insets(0,5,0,10);
		
		_rightToppings = new JProgressBar(JProgressBar.VERTICAL, 0, 10);
		_rightToppings.setFont(new Font( "SansSerif", Font.BOLD, 16 ));
		_rightToppings.setStringPainted(true);
		_rightToppings.setPreferredSize(new Dimension(25, 300));
		_rightToppings.setForeground(new Color(190,220,135));
		GridBagConstraints rightToppingsConstraints = new GridBagConstraints();
		rightToppingsConstraints.insets = new Insets(0,0,0,15);
		
		
		updateProgressBars();
		
		_numToppingsPanel.add(_leftToppings, leftToppingsConstraints);
		_numToppingsPanel.add(_rightToppings, rightToppingsConstraints);

	}
	
	/**
	 * Initializes the size options panel.
	 */
	private void createSizeOptionsPanel(){
		_sizeOptionsPanel = new JPanel();
		_sizeOptionsPanel.setBorder( BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				"Size Options" ) );
		_sizeOptionsPanel.setLayout( new GridBagLayout() );
		
		
		
		JPanel _sizeButtonPanel = 
			new JPanel( new GridLayout( 3, 1, 0, 10));
		
		//Sets up the buttons in size options
		
		smallPizzaButton.setPreferredSize( new Dimension( 175, 85 ));
		smallPizzaButton.addActionListener(this);
		smallPizzaButton.setFont(new Font( "SansSerif", Font.BOLD, 16 ));
		GridBagConstraints smallPizzaButtonConstraints  = new GridBagConstraints();
		smallPizzaButtonConstraints.gridy = 0;
		_sizeButtonPanel.add(smallPizzaButton, smallPizzaButtonConstraints);
		
		
		mediumPizzaButton.setPreferredSize( new Dimension( 175, 85 ));
		mediumPizzaButton.addActionListener(this);
		mediumPizzaButton.setBackground(Color.lightGray);
		mediumPizzaButton.setFont(new Font( "SansSerif", Font.BOLD, 16 ));
		GridBagConstraints mediumPizzaButtonConstraints  = new GridBagConstraints();
		mediumPizzaButtonConstraints.gridy = 1;
		_sizeButtonPanel.add(mediumPizzaButton, mediumPizzaButtonConstraints);
		
		
		largePizzaButton.setPreferredSize( new Dimension( 175, 85 ));
		largePizzaButton.addActionListener(this);
		largePizzaButton.setFont(new Font( "SansSerif", Font.BOLD, 16 ));
		GridBagConstraints largePizzaButtonConstraints  = new GridBagConstraints();
		largePizzaButtonConstraints.gridy = 2;
		_sizeButtonPanel.add(largePizzaButton, largePizzaButtonConstraints );
		
		
		GridBagConstraints sizeOptionsPanelConstraints = new GridBagConstraints();
		sizeOptionsPanelConstraints.fill = GridBagConstraints.BOTH;
		
		
		_sizeOptionsPanel.add(_sizeButtonPanel, sizeOptionsPanelConstraints);
	}
	
	/**
	 * Initializes the quantity panel.
	 */
	public void createQuantityPanel(){
		_quantityPanel = new JPanel();
		_quantityPanel.setLayout( new GridBagLayout() );
		_quantityPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		JLabel quantityLabel = new JLabel("Quantity: ");
		quantityLabel.setFont( PDSViewManager.DEFAULT_TF_LABEL_FONT );
		_quantityField = new JTextField( 3 );
		_quantityField.setText("1");
		_quantityField.setColumns(15);
		_quantityField.setFont( PDSViewManager.DEFAULT_TEXTFIELD_FONT );
		_quantityField.setPreferredSize( new Dimension( 70, PDSViewManager.DEFAULT_TEXTFIELD_HEIGHT ) );
		_quantityField.addKeyListener( this );
		_addToOrderButton.addActionListener(this);
		
		_addToOrderButton.setPreferredSize( new Dimension( 150, 50 ) );
		_addToOrderButton.setFont( PDSViewManager.DEFAULT_BUTTON_FONT );
		
		GridBagConstraints quantityLabelConstraints = new GridBagConstraints();
		quantityLabelConstraints.insets = new Insets(10,0,10,10);
		_quantityPanel.add(quantityLabel, quantityLabelConstraints);
		
		GridBagConstraints quantityFieldConstraints = new GridBagConstraints();
		quantityFieldConstraints.insets = new Insets(0,0,0,100);
		_quantityPanel.add(_quantityField, quantityFieldConstraints);
		
		GridBagConstraints addOrderButtonConstraints = new GridBagConstraints();
		//addOrderButtonConstraints.ipadx = 10;
		_quantityPanel.add(_addToOrderButton, addOrderButtonConstraints);
		
		validQuantity = true;
	}

	
	/**
	 * Called when a button is pressed
	 * 
	 * @param aEvent
	 */
	public void actionPerformed(ActionEvent aEvent) {
		
		// Check if the event was caused by a button.
		if( aEvent.getSource() instanceof JButton ) {

			JButton sourceButton = (JButton)aEvent.getSource();
			
			if( sourceButton.getText().equals( 
					AddPizzaViewGUI.SMALL_BUTTON ) ) {
				size = PizzaFoodItem.PizzaSize.PizzaSizeSmall;
				_selectedSizeButton.setBackground(null);
				smallPizzaButton.setBackground(Color.lightGray);
				_selectedSizeButton = smallPizzaButton;
			}
			else if( sourceButton.getText().equals( 
					AddPizzaViewGUI.MEDIUM_BUTTON ) ) {
				size = PizzaFoodItem.PizzaSize.PizzaSizeMedium;
				_selectedSizeButton.setBackground(null);
				mediumPizzaButton.setBackground(Color.lightGray);
				_selectedSizeButton = mediumPizzaButton;
			}
			else if( sourceButton.getText().equals( 
					AddPizzaViewGUI.LARGE_BUTTON ) ) {
				size = PizzaFoodItem.PizzaSize.PizzaSizeLarge;
				_selectedSizeButton.setBackground(null);
				largePizzaButton.setBackground(Color.lightGray);
				_selectedSizeButton = largePizzaButton;
			}
			else if( sourceButton.getText().equals( 
					AddPizzaViewGUI.ADD_MORE_TOPPINGS_BUTTON ) ) {
				ToppingTableCell toppingOption = new ToppingTableCell(this);
				toppingsOptionsCells.add( toppingOption );
				_toppingsOptionsTable.updateTable();
				_toppingsOptionsTable.scrollToBottom();
				updateProgressBars();
				
				
			}
			else if( sourceButton.getText().equals( 
					AddPizzaViewGUI.ADD_TO_ORDER_BUTTON_NAME) ||
					sourceButton.getText().equalsIgnoreCase(
							"Done")) {
					sendToController();
				
			}
			else if( sourceButton.getText().equals( 
					PDSViewManager.BACK_BUTTON_NAME ) ) {
				
				if(modifying){
					sendToController(originalPizzaToModify);
				}
				else{
					controller.respondToInput( AddPizzaView.BACK_KEY ,
						AddPizzaInChan.ICGoBack );
				}

			}
		}
			
		
		
	}
	
	/**
	 * Function that handles processing the order by sending all of the
	 * relevent information to the controller.
	 */
	public void sendToController(){
		
		String sizeKey = "";
		
		switch(size){
		case PizzaSizeSmall:
			sizeKey = AddPizzaView.SMALL_KEY;
			break;
			
		case PizzaSizeMedium:
			sizeKey = AddPizzaView.MEDIUM_KEY;
			break;
			
		case PizzaSizeLarge:
			sizeKey = AddPizzaView.LARGE_KEY;
			break;
		}
		
		controller.respondToInput(sizeKey, AddPizzaInChan.ICSizeSelection);
		
		for( ToppingTableCell topping : toppingsOptionsCells){
			String toppingLocation = topping.getToppingLocationKey();
			controller.respondToInput(toppingLocation, AddPizzaInChan.ICLocationSelection);
			
			int toppingChoice = topping.getToppingOptions().getSelectedIndex();
			String toppingSelection = Integer.toString(toppingChoice);
			controller.respondToInput(toppingSelection, AddPizzaInChan.ICToppingsSelection);
			
		}
		
		controller.respondToInput(AddPizzaView.CONTINUE_KEY, AddPizzaInChan.ICContinue);
		
		controller.respondToInput(_quantityField.getText(), AddPizzaInChan.ICQuantity);
		
		
	}
	
	/**
	 * Sends a specific pizza object to the controller
	 */
	public void sendToController(PizzaFoodItem pizza){
		String sizeKey = "";
		PizzaFoodItem.PizzaSize originalSize = pizza.getSize();
		
		switch(originalSize){
		case PizzaSizeSmall:
			sizeKey = AddPizzaView.SMALL_KEY;
			break;
			
		case PizzaSizeMedium:
			sizeKey = AddPizzaView.MEDIUM_KEY;
			break;
			
		case PizzaSizeLarge:
			sizeKey = AddPizzaView.LARGE_KEY;
			break;
		}
		controller.respondToInput(sizeKey, AddPizzaInChan.ICSizeSelection);
		
		((AddPizzaController) controller).setCurrentPizza(pizza);
		
		controller.respondToInput(CONTINUE_KEY, AddPizzaInChan.ICContinue);
		controller.respondToInput("1", AddPizzaInChan.ICQuantity);
		
		
	}


	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// Check the quantity field.
		String qString = _quantityField.getText();

		// Only allow the next character if it gives a positive integer.
		

		try {
			validQuantity = true;
			int qInt = Integer.parseInt( qString );
			if( qInt <= 0 ) {
				validQuantity = false;
				_addToOrderButton.setEnabled( false );
			}
		} catch( NumberFormatException exc ) {
			validQuantity = false;
			_addToOrderButton.setEnabled( false );
		}
		
		if( validQuantity ) {
			if (_selectedSizeButton != null){
				_addToOrderButton.setEnabled( true );
			}
		} else {
			_addToOrderButton.setEnabled( false );
		}


	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	////////// ZSCROLLTABLE DELEGATE METHODS
	
	
	@Override
	public int getNumberOfCells(ZScrollTable table) {
		return toppingsOptionsCells.size();
	}



	@Override
	public int getCellSpacing(ZScrollTable table) {
		return 1;
	}


	@Override
	public Color getTableBGColor(ZScrollTable table) {
		return new Color( 100, 100, 100);
	}

	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getEmptyTableMessage(gui.scrolltable.ZScrollTable)
	 */
	public String getEmptyTableMessage( ZScrollTable table ) {
		return "There are no toppings on the pizza.";
	}

	@Override
	public ZScrollTableCell getCell(ZScrollTable table, int index) {
		//Create a new ToppingTableCell
		ToppingTableCell cell = toppingsOptionsCells.get(index);
		
		// Color the cell based on the index.
		int shade = 220 + (index % 2) * 20;
		cell.setBackground( new Color( shade, shade, shade ) );
		cell.getToppingsLocationPanel().setBackground(new Color( shade, shade, shade ) );
		
		return cell;
	}

	@Override
	public int getCellWidth(ZScrollTable table) {
		// TODO Auto-generated method stub
		return ToppingTableCell.CELL_WIDTH;
	}

	@Override
	public int getCellHeight(ZScrollTable table, int index) {
		// TODO Auto-generated method stub
		return ToppingTableCell.CELL_HEIGHT;
	}



	@Override
	public boolean canDeleteCell(ZScrollTable table, int index) {
		return true;
	}


	@Override
	public void cellWasDeleted(ZScrollTable table, int index) {
		// TODO Auto-generated method stub
			toppingsOptionsCells.remove(index);
			_toppingsOptionsTable.updateTable();
			updateProgressBars();
				
	}


	@Override
	public JPanel getMainPanel() {
		// TODO Auto-generated method stub
		return _mainPanel;
	}


	@Override
	public void setVisible(boolean visible) {
		
		// Pass the change on to the main panel.
		_mainPanel.setVisible( visible );

		// Show the back button if this is going to be displayed.
		PDSViewManager.setBackButtonEnabled( visible );
		
	}


	@Override
	public void setChannelEnabled(InputChannel inChannel, boolean enabled) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void displayString(String message, OutputChannel outChannel) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void displayObject(Object object, OutputChannel outChannel) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public <T> void displayList(ArrayList<T> list, OutputChannel outChannel) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ZScrollTableCell getHeaderCell(ZScrollTable table) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void cellWasClicked(ZScrollTable table, int index) {
		
	}
	
	public int getNumberOfLeftToppings(){
		
		int numToppLeft = 0;
		for( ToppingTableCell topping : toppingsOptionsCells){
			String toppingLocationKey = topping.getToppingLocationKey();
			if( toppingLocationKey.equalsIgnoreCase( AddPizzaView.WHOLE_KEY ) ) {
				numToppLeft++;
			} else if( toppingLocationKey.equalsIgnoreCase( AddPizzaView.LEFT_KEY ) ) {
				numToppLeft++;
			}
			else{
				numToppLeft += 0;
			}
		}
		
		return numToppLeft;
		
	}
	
	public int getNumberOfRightToppings(){
		int numToppRight = 0;
		for( ToppingTableCell topping : toppingsOptionsCells){
			String toppingLocationKey = topping.getToppingLocationKey();
			if( toppingLocationKey.equalsIgnoreCase( AddPizzaView.WHOLE_KEY ) ) {
				numToppRight++;
			} else if( toppingLocationKey.equalsIgnoreCase( AddPizzaView.RIGHT_KEY ) ) {
				numToppRight++;
			}
			else{
				numToppRight += 0;
			}
		}
		
		return numToppRight;
		
	}
	
	public void updateProgressBars(){
			
		int leftToppings = getNumberOfLeftToppings();
		int rightToppings = getNumberOfRightToppings();
			
		if(leftToppings <= 10){
			_leftToppings.setValue(leftToppings);
			_leftToppings.setForeground(new Color(190,220,135));
			_leftToppings.setString("Left Toppings: " + leftToppings + " / 10");
		}
		else{
			_leftToppings.setForeground(new Color(220,120,120));
			_leftToppings.setString("Left Toppings: " + leftToppings + " / 10");
		}
			
		if(rightToppings <= 10){
			_rightToppings.setValue(rightToppings);
			_rightToppings.setForeground(new Color(190,220,135));
			_rightToppings.setString("Right Toppings: " + rightToppings + " / 10");
		}
		else{
			_rightToppings.setForeground(new Color(220,120,120));
			_rightToppings.setString("Right Toppings: " + rightToppings + " / 10");
		}	
		
		if (rightToppings > 10 || leftToppings > 10){
			_addToOrderButton.setEnabled(false);
		}
		else if (rightToppings <= 10 && leftToppings <= 10){
			if(validQuantity){
			_addToOrderButton.setEnabled(true);
			}
		}

	}
}
