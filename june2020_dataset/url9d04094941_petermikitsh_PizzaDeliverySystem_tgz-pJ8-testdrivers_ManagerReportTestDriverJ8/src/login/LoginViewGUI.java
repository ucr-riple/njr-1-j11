package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.PDSViewManager;
import viewcontroller.GeneralViewGUI;

/**
 * The GUI view for the login module.
 * 
 * @author  Casey Klimkowsky	cek3403@g.rit.edu
 */
public class LoginViewGUI extends LoginView 
	implements GeneralViewGUI, KeyListener {

	/**
	 * The name of the "log in" button.
	 */
	private static final String LOG_IN_BUTTON_NAME = "Log In";
	
	/**
	 * The JPanel to which contains everything in this view.
	 */
	private JPanel _mainPanel;
	
	/**
	 * The label that prints error data.
	 */
	private JLabel _errorLabel;
	
	/**
	 * The panel that gathers login information.
	 */
	private JPanel _loginInputPanel;
	
	/**
	 * The text field that displays a login ID.
	 */
	private JTextField _loginIdField;
	
	/**
	 * The text field that displays a password.
	 */
	private JPasswordField _passwordField;
	
	/** 
	 * The button that is used for form submission.
	 */
	private JButton _logInButton;
	
	/**
	 * The default constructor. Does Swing-related setup.
	 */
	public LoginViewGUI() {
		
		// Initialize the main panel.
		_mainPanel = new JPanel();
		_mainPanel.setLayout( new BorderLayout() );
		_mainPanel.setName( "Log In" );
		
		// Create a label to hold the error output.
		_errorLabel = new JLabel();
		_errorLabel.setForeground( Color.RED );
		JPanel errorPanel = new JPanel();
		errorPanel.add( _errorLabel );
		_mainPanel.add( errorPanel, BorderLayout.SOUTH );
		
		// Initialize the login input panel.
		initializeLoginInputContainer();
		
		
	}
	
	/**
	 * Creates the login panel.
	 */
	private void initializeLoginInputContainer() {
		
		// Create the container, which has the input panel in its center.
		JPanel loginInputContainer = new JPanel();
		loginInputContainer.setLayout( new GridBagLayout() );
		GridBagConstraints constraints = new GridBagConstraints();
	
		
		// Add the image with the caribou.
		try {
			BufferedImage caribouImage = ImageIO.read( this.getClass().getResource("/resources/caribou.png")  );
			JLabel caribouLabel = new JLabel( new ImageIcon( caribouImage ) );
			loginInputContainer.add( caribouLabel, constraints );
		} catch( IOException exc ) {
			// Do nothing.
		}
		
		
		_loginInputPanel = new JPanel();
		_loginInputPanel.setLayout( new GridLayout( 0, 1, 0, 5 ) );
		
		// Create the text fields.
		_loginIdField = new JTextField( 10 );
		_passwordField = new JPasswordField( 10 );
		_loginIdField.addKeyListener( this );
		_passwordField.addKeyListener( this );
		_loginIdField.setFont( PDSViewManager.DEFAULT_TEXTFIELD_FONT );
		_passwordField.setFont( PDSViewManager.DEFAULT_TEXTFIELD_FONT );
		_loginIdField.setPreferredSize( new Dimension( 70, PDSViewManager.DEFAULT_TEXTFIELD_HEIGHT ) );
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout( new GridLayout( 2, 1, 0, 7 ) );
		JLabel loginIdLabel = new JLabel( "Login ID:  ", JLabel.RIGHT );
		JLabel passwordLabel = new JLabel( "Password:  ", JLabel.RIGHT );
		loginIdLabel.setFont( PDSViewManager.DEFAULT_TF_LABEL_FONT );
		passwordLabel.setFont( PDSViewManager.DEFAULT_TF_LABEL_FONT );
		textPanel.add(loginIdLabel);
		textPanel.add(passwordLabel);
		
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout( new GridLayout( 2, 1, 0, 7 ) );
		fieldPanel.add( _loginIdField );
		fieldPanel.add( _passwordField );
		
		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new BorderLayout());
		loginPanel.add(textPanel, BorderLayout.WEST);
		loginPanel.add(fieldPanel, BorderLayout.CENTER);
		
		// Add the fields.
		_loginInputPanel.add( loginPanel );
		
		// Add the "log in" button.
		_logInButton = new JButton( LoginViewGUI.LOG_IN_BUTTON_NAME );
		_logInButton.setFont( PDSViewManager.DEFAULT_BUTTON_FONT );
		_logInButton.addActionListener( this );
		JPanel logInButtonPanel = new JPanel();
		logInButtonPanel.add( _logInButton );
		_loginInputPanel.add( logInButtonPanel );
		
		constraints.gridy = 1;
		loginInputContainer.add( _loginInputPanel, constraints );
		
		_mainPanel.add(loginInputContainer);
		
	}
	
	/**
	 * @see viewcontroller.GeneralView#displayString(java.lang.String, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayString(String message, OutputChannel outChannel) {
		
		switch ((UserOutChan) outChannel) {
		
		case OCError:
			_errorLabel.setText( message );
			break;
		
		}
		
		_mainPanel.updateUI();
		
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
	public void setVisible(boolean visible) {
		
		// Pass the change on to the main panel.
		_mainPanel.setVisible(visible);
		
		// Show the back button if this is going to be displayed.
		PDSViewManager.setBackButtonEnabled(false);
		
		// Clear the error input.
		if( visible ) {
			_errorLabel.setText( " " );
			
			 // Clear the user in the view manager display.
			PDSViewManager.setUserNameString( "" );
		}
		else {
			// Clear the login input.
			_loginIdField.setText( "" );
			_passwordField.setText( "" );
		}
		
	}
	
	/**
	 * @see viewcontroller.GeneralView#setChannelEnabled(viewcontroller.GeneralView.InputChannel, boolean)
	 */
	public void setChannelEnabled(InputChannel inChannel, boolean enabled) {
		
		switch ((UserInChan) inChannel) {
//		
//			default:
//				_mainPanel.add(loginInputContainer);
//				break;
//		
		}
		
	}
	
	/**
	 * Called when a button is pressed.
	 * 
	 * @param aEvent 
	 */
	public void actionPerformed(ActionEvent e) {
		
		// Check if the event was caused by a button.
		if (e.getSource() instanceof JButton) {

			JButton sourceButton = (JButton) e.getSource();

			// Check the button for its name.
			if (sourceButton.getText().equals( 
					LoginViewGUI.LOG_IN_BUTTON_NAME)) {
				
				controller.respondToInput( _loginIdField.getText(),
						UserInChan.ICStringInput );
				
				// Gets the input from the password text field.
				String password = "";
				for (int i = 0; i < _passwordField.getPassword().length; i++)
					password += _passwordField.getPassword()[i];
				
				controller.respondToInput( password,
						UserInChan.ICStringInput );
				
			}
			
		}
		
	}

	
	////// KEYLISTENER METHODS

	
	@Override
	public void keyPressed(KeyEvent e) {
		
		// Check for the enter key.
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_ENTER) {
			
			// Pretend that an action was performed.
			this.actionPerformed( new ActionEvent( _logInButton, 0, null ) );
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
