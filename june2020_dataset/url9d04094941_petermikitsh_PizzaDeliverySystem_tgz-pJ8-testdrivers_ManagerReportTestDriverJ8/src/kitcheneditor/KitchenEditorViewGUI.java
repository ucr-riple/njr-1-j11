package kitcheneditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.PDSViewManager;
import ninja.SystemTime.SystemTimeScale;
import viewcontroller.GeneralViewGUI;

public class KitchenEditorViewGUI extends KitchenEditorView 
	implements GeneralViewGUI, ChangeListener, KeyListener {
	
	/**
	 * The name of the edit menu button
	 */
	private static final String UPDATE_BUTTON_NAME = "Update";

	/**
	 * The font used for textfields.
	 */
	private static final Font TEXTFIELD_FONT = PDSViewManager.DEFAULT_TEXTFIELD_FONT;

	/**
	 * The font used for labels.
	 */
	private static final Font LABEL_FONT = PDSViewManager.DEFAULT_TF_LABEL_FONT;

	/**
	 * The JPanel to which contains everything in this view.
	 */
	private JPanel _mainPanel;
	
	/**
	 * The slider for adjusting system time.
	 */
	private JSlider _timeScaleSlider;
	
	/**
	 * The label that indicates the conversion rate of the time scale.
	 */
	private JLabel _timeScaleConversionLabel;

	/**
	 * The text field for number of cooks.
	 */
	private JTextField _numCooksField;
	
	/**
	 * The text field for number of ovens.
	 */
	private JTextField _numOvensField;
	
	/**
	 * The text field for number of drivers.
	 */
	private JTextField _numDriversField;
	
	/**
	 * The text field for the tax.
	 */
	private JTextField _taxField;
	
	/**
	 * The label that prints instructions.
	 */
	private JLabel _instructionsLabel;

	/**
	 * The label that prints error data.
	 */
	private JLabel _errorLabel;	
	
	/**
	 * The button that updates changes.
	 */
	private JButton _updateButton;
	
	/**
	 * A Timer that updates the enabled / disabled fields.
	 */
	private static Timer _timer;
	
	/**
	 * Default constructor.
	 */
	public KitchenEditorViewGUI() {
		
		// Initialize the main panel
		_mainPanel = new JPanel();
		_mainPanel.setLayout( new BorderLayout() );
		_mainPanel.setName( "Kitchen Options" );
		
		// Create a label to hold the instructions.
		_instructionsLabel = new JLabel();
		JPanel instructionsPanel = new JPanel();
		instructionsPanel.add( _instructionsLabel );
		_mainPanel.add( instructionsPanel , BorderLayout.NORTH );

		// Create a label to hold the error output.
		_errorLabel = new JLabel();
		_errorLabel.setForeground( Color.RED );
		JPanel errorPanel = new JPanel();
		errorPanel.add( _errorLabel );
		_mainPanel.add( errorPanel, BorderLayout.SOUTH );
		
		// Create a panel for the center.
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout( new GridBagLayout() );
		GridBagConstraints constraints = new GridBagConstraints();
		_mainPanel.add( centerPanel, BorderLayout.CENTER );
		
		// Add a panel with the slider.
		constraints.ipady = 50;
		centerPanel.add( getTimeScaleSliderPanel(), constraints );
		
		 // Add a panel with the kitchen text fields.
		constraints.gridy = 1;
		constraints.ipady = 0;
		centerPanel.add( getKitchenFieldsPanel(), constraints );
		
		// Add a panel with the tax field.
		JPanel taxContainer = new JPanel();
		taxContainer.setLayout( new GridBagLayout() );
		JPanel taxPanel = new JPanel();
		taxPanel.setLayout( new GridLayout( 1, 2 ) );

		JLabel taxLabel = new JLabel( "Tax (%): ", JLabel.TRAILING );
		taxLabel.setFont( LABEL_FONT );
		taxPanel.add( taxLabel );
		
		JPanel taxFieldPanel = new JPanel();
		taxFieldPanel.setLayout( new FlowLayout( FlowLayout.LEADING ) );
		_taxField = new JTextField( 3 );
		_taxField.setFont( TEXTFIELD_FONT );
		_taxField.addKeyListener( this );
		taxFieldPanel.add( _taxField );
		taxPanel.add( taxFieldPanel );
		
		taxContainer.add( taxPanel, new GridBagConstraints() );
		constraints.gridy = 2;
		constraints.ipady = 50;
		centerPanel.add( taxContainer, constraints );
		
		// Add an update button.
		JPanel updatePanel = new JPanel();
		updatePanel.setLayout( new GridBagLayout() );
		_updateButton = new JButton( UPDATE_BUTTON_NAME );
		_updateButton.setFont( PDSViewManager.DEFAULT_BUTTON_FONT );
		_updateButton.addActionListener( this );
		updatePanel.add( _updateButton );
		
		constraints.gridy = 3;
		constraints.ipady = 0;
		centerPanel.add( updatePanel, constraints );
		
	}
	
	/**
	 * Creates the panel with the time scale slider.
	 */
	private JPanel getTimeScaleSliderPanel() {

		// Create a panel to hold everything.
		JPanel tsSliderContainer = new JPanel();
		tsSliderContainer.setLayout( new GridBagLayout() );
		
		JPanel tsSliderPanel = new JPanel();
		tsSliderPanel.setLayout( new GridBagLayout() );
		GridBagConstraints tsConstraints = new GridBagConstraints();
		
		// Add a title label.
		JLabel titleLabel = new JLabel( "Simulation Speed" );
		titleLabel.setFont( LABEL_FONT );
		tsSliderPanel.add( titleLabel, tsConstraints );
		
		// Create the slider, set tick stuff.
		_timeScaleSlider = new JSlider( 0, SystemTimeScale.allTimeScales.length - 1, 0 );
		_timeScaleSlider.setPaintTicks( true );
		_timeScaleSlider.setMajorTickSpacing( 3 );
		_timeScaleSlider.setMinorTickSpacing( 1 );
		_timeScaleSlider.setSnapToTicks( true );
		
		// Create a map for the labels of the slider.
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		Font labelFont = new Font( "SansSerf", Font.PLAIN, 12 );
		JLabel slowerLabel = new JLabel("Slower");
		JLabel fasterLabel = new JLabel("Faster");
		slowerLabel.setFont( labelFont );
		fasterLabel.setFont( labelFont );
		labelTable.put( _timeScaleSlider.getMinimum(), slowerLabel );
		labelTable.put( _timeScaleSlider.getMaximum(), fasterLabel );
		_timeScaleSlider.setLabelTable( labelTable );
		_timeScaleSlider.setPaintLabels(true);
		
		// Make this class a listener of the slider.
		_timeScaleSlider.addChangeListener( this );
		
		tsConstraints.gridy = 1;
		tsConstraints.ipady = 5;
		tsSliderPanel.add( _timeScaleSlider, tsConstraints );

		// Add a label that displays the conversion rate.
		_timeScaleConversionLabel = new JLabel();
		_timeScaleConversionLabel.setFont( new Font( "SansSerif", Font.PLAIN, 13 ) );
		tsConstraints.gridy = 2;
		tsConstraints.ipady = 0;
		tsSliderPanel.add( _timeScaleConversionLabel, tsConstraints );
		updateConversionLabel();
		
		tsSliderContainer.add( tsSliderPanel, new GridBagConstraints() );
		return tsSliderContainer;
		
	}
	
	/**
	 * Creates the panel of kitchen fields.
	 */
	private JPanel getKitchenFieldsPanel() {
		
		JPanel kitchenFieldsPanel = new JPanel();
		kitchenFieldsPanel.setLayout( new GridLayout( 3, 2, 2, 5 ) );
		
		// Add each label / textfield pair.
		JLabel numCooksLabel = new JLabel( "Number of Cooks: ", JLabel.TRAILING );
		numCooksLabel.setFont( LABEL_FONT );
		kitchenFieldsPanel.add( numCooksLabel );
		
		JPanel numCooksFieldPanel = new JPanel();
		numCooksFieldPanel.setLayout( new FlowLayout( FlowLayout.LEADING ) );
		_numCooksField = new JTextField( 2 );
		_numCooksField.setFont( TEXTFIELD_FONT );
		_numCooksField.addKeyListener( this );
		numCooksFieldPanel.add( _numCooksField );
		kitchenFieldsPanel.add( numCooksFieldPanel );
		
		JLabel numOvensLabel = new JLabel( "Number of Ovens: ", JLabel.TRAILING );
		numOvensLabel.setFont( LABEL_FONT );
		kitchenFieldsPanel.add( numOvensLabel );
		
		JPanel numOvensFieldPanel = new JPanel();
		numOvensFieldPanel.setLayout( new FlowLayout( FlowLayout.LEADING ) );
		_numOvensField = new JTextField( 2 );
		_numOvensField.setFont( TEXTFIELD_FONT );
		_numOvensField.addKeyListener( this );
		numOvensFieldPanel.add( _numOvensField );
		kitchenFieldsPanel.add( numOvensFieldPanel );
		
		JLabel numDriversLabel = new JLabel( "Number of Drivers: ", JLabel.TRAILING );
		numDriversLabel.setFont( LABEL_FONT );
		kitchenFieldsPanel.add( numDriversLabel );
		
		JPanel numDriversFieldPanel = new JPanel();
		numDriversFieldPanel.setLayout( new FlowLayout( FlowLayout.LEADING ) );
		_numDriversField = new JTextField( 2 );
		_numDriversField.setFont( TEXTFIELD_FONT );
		_numDriversField.addKeyListener( this );
		numDriversFieldPanel.add( _numDriversField );
		kitchenFieldsPanel.add( numDriversFieldPanel );
		
		return kitchenFieldsPanel;
		
	}
	
	/**
	 * @see viewcontroller.GeneralView#displayString(java.lang.String, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayString(String message, OutputChannel outChannel) {

		// Check the output channel.
		switch( (KitchenEditorOutChan)outChannel ) {
		
		case OCTimeScale:
			// Search through the array to find a time scale object 
			// that matches this one.
			for( int index = 0; index < SystemTimeScale.allTimeScales.length; index++ ) {
				if( SystemTimeScale.allTimeScales[index].toString().equals( message ) ) {
					_timeScaleSlider.setValue( index );
					break;
				}
			}

			break;
		
		case OCNumCooks:
			_numCooksField.setText( message );
			break;
			
		case OCNumOvens:
			_numOvensField.setText( message );
			break;
			
		case OCNumDrivers:
			_numDriversField.setText( message );
			break;
			
		case OCTax:
			_taxField.setText( message );
			break;
			
		case OCError:
			_errorLabel.setText( message );
			break;
			
		case OCInstructions:
			_instructionsLabel.setText( message );
			break;
		
		}
		
		_mainPanel.updateUI();

	}

	/**
	 * @see viewcontroller.GeneralView#displayObject(java.lang.Object, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayObject(Object object, OutputChannel outChannel) {

		// We don't need to do anything here.
	}

	/**
	 * @see viewcontroller.GeneralView#displayList(java.util.ArrayList, viewcontroller.GeneralView.OutputChannel)
	 */
	public <T> void displayList(ArrayList<T> list, OutputChannel outChannel) {

		// We don't need to do anything here.

	}

	/**
	 * @return This GUI's content panel.
	 */
	public JPanel getMainPanel() {
		return _mainPanel;
	}
	
	/**
	 * Set the panel visible or invisible.
	 */
	public void setVisible( boolean visible ) {
		
		// Pass the change on to the main panel.
		_mainPanel.setVisible( visible );
		
		// Hide the back button if this is going to be displayed.
		PDSViewManager.setBackButtonEnabled( visible );

		// Enable or disable the timer.
		if( visible ) {

			_timer = new Timer( 1000, this );
			_timer.start();

		} else if( _timer != null ) {

			_timer.stop();
			_timer = null;

		}
		
	}
	
	/**
	 * @see viewcontroller.GeneralView#setChannelEnabled( InputChannel inChannel, boolean enabled )
	 */
	public void setChannelEnabled(InputChannel inChannel, boolean enabled) {

		// Clear the error output whenever the input methods are changed.
		_errorLabel.setText( " " );

		switch( (KitchenEditorInChan)inChannel ) {
		
		case ICNumCooks:
			_numCooksField.setEditable( enabled );
			break;
			
		case ICNumOvens:
			_numOvensField.setEditable( enabled );
			break;
			
		case ICNumDrivers:
			_numDriversField.setEditable( enabled );
			break;
			
		case ICTax:
			_taxField.setEditable( enabled );
			break;
			
		}
		
	}
	
	public void actionPerformed(ActionEvent aEvent) {
		
		if( aEvent.getSource() instanceof JButton ) {
		
			JButton sourceButton = (JButton)aEvent.getSource();
			
			if( sourceButton.getText().equals( UPDATE_BUTTON_NAME ) ) {
				
				// Send data to the controller.
				int sliderValue = _timeScaleSlider.getValue();
				controller.respondToInput( SystemTimeScale.allTimeScales[sliderValue].toString(), 
						KitchenEditorInChan.ICTimeScale );
				
				controller.respondToInput( _numCooksField.getText(),
						KitchenEditorInChan.ICNumCooks );
				controller.respondToInput( _numOvensField.getText(),
						KitchenEditorInChan.ICNumOvens );
				controller.respondToInput( _numDriversField.getText(),
						KitchenEditorInChan.ICNumDrivers );
				controller.respondToInput( _taxField.getText(),
						KitchenEditorInChan.ICTax );

			} else if (sourceButton.getText().equals(PDSViewManager.BACK_BUTTON_NAME)) {
				controller.respondToInput( KitchenEditorView.BACK_KEY, 
											KitchenEditorInChan.ICBack );
			}
 		
		} else if ( aEvent.getSource() instanceof Timer ) {
			
			// Send the refresh message.
			controller.respondToInput( KitchenEditorView.REFRESH_KEY,
					KitchenEditorInChan.ICRefresh );
			
		}
		
	}

	/**
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent arg0) {
		
		// This method is called when the slider is changed.
		updateConversionLabel();
		
	}
	
	/**
	 * Updates the value of the slider conversion label.
	 */
	private void updateConversionLabel() {
		
		// Update the value of the label.
		int sliderValue = _timeScaleSlider.getValue();
		String convTime = SystemTimeScale.allTimeScales[sliderValue].getSimulationTimeString();
		_timeScaleConversionLabel.setText( "1 second of real time = "
				+ convTime + " of simulation time" );
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent arg0) {
		
		String numCooksStr = _numCooksField.getText();
		String numOvensStr = _numOvensField.getText();
		String numDriversStr = _numDriversField.getText();
		String taxStr = _taxField.getText();
		
		boolean validInput = true;
		
		try {
			
			int numCooks = Integer.parseInt( numCooksStr );
			int numOvens = Integer.parseInt( numOvensStr );
			int numDrivers = Integer.parseInt( numDriversStr );
			
			if ( numCooks <= 0 || numCooks >= 100 ) {
				validInput = false;
			}
			
			if ( numOvens <= 0 || numOvens >= 100 ) {
				validInput = false;
			}
			
			if ( numDrivers <= 0 || numDrivers >= 100 ) {
				validInput = false;
			}
			
		} catch ( NumberFormatException e ) {
			validInput = false;
		}
		
		try {
			double tax = Double.parseDouble( taxStr );
			if ( (tax < 0 || tax > 99.9) ) {
				validInput = false;
			}
		} catch( NumberFormatException e ) {
			validInput = false;
		}
		
		if( taxStr.contains(".") && validInput ) {
			
			int index = taxStr.indexOf(".");
			
			// ##.# validation
			if( (taxStr.substring(0, index).length() == 2 &&
					taxStr.substring(index + 1, 
							taxStr.length()).length() == 1) ) {
				validInput = true;
			}
			
			else if( index == 1 && taxStr.length() == 3 ) {
				
				// #.0 or #.00 validation
				if( taxStr.charAt(0) >= 0 && 
						taxStr.charAt(0) <= 9 ) {
					if( taxStr.substring(index + 1, 
							taxStr.length()).equals("0") ||
							taxStr.substring(index + 1, 
									taxStr.length()).equals("00") ) {
						validInput = true;
					}
					else {
						validInput = false;
					}
				}
				
				// ##.0 validation
				else if( taxStr.charAt(0) >= 10 && 
						taxStr.charAt(0) <= 99 ) {
					if( taxStr.substring(index + 1, 
							taxStr.length()).equals("0")) {
						validInput = true;
					}
					else {
						validInput = false;
					}
				}
			}
			
			else {
				validInput = false;
			}
			
		}
		
		// # or ## validation; rejects "00"
		else if( validInput && (taxStr.length() <= 2 &&
				(! taxStr.equals("00")))) {
			validInput = true;
		}
		
		else {
			validInput = false;
		}
		
		if( validInput ) {
			_updateButton.setEnabled( true );
		}
		else {
			_updateButton.setEnabled( false );
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
