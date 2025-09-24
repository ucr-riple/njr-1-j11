package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import ninja.SystemTime;
import ninja.Time;
import viewcontroller.GeneralViewGUI;

/**
 * This class represents the main window frame for the PDS system.  It
 *  displays the name of the currently display component at the top of the 
 *  window along with the current time.  It also provides methods for 
 *  manipulating the contents that are displayed in the main portion of
 *  the window.
 *  
 *  This class is a singleton.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
@SuppressWarnings("serial")
public class PDSViewManager extends JFrame implements ActionListener {
	
	/**
	 * The system-wide default font for text fields.
	 */
	public static final Font DEFAULT_TEXTFIELD_FONT = new Font( "SansSerif", Font.PLAIN, 16 );
	
	/**
	 * The system-wide height for textfields.
	 */
	public static final int DEFAULT_TEXTFIELD_HEIGHT = 28;
	
	/**
	 * The system-wide default font for text fields labels.
	 */
	public static final Font DEFAULT_TF_LABEL_FONT = new Font( "SansSerif", Font.BOLD, 15 );
	
	/**
	 * The system-wide default font for text fields labels.
	 */
	public static final Font DEFAULT_BUTTON_FONT = new Font( "SansSerif", Font.BOLD, 14 );
	
	/**
	 * The title displayed in the main window.
	 */
	public static final String MAIN_WINDOW_NAME = "Caribou Management Systems";
	
	/**
	 * The back button name.
	 */
	public static final String BACK_BUTTON_NAME = "Back";
	
	/**
	 * The log out button name.
	 */
	public static final String LOG_OUT_BUTTON_NAME = "Log Out";
	
	/**
	 * The single PDSMainWindow manager.
	 */
	private static PDSViewManager _mainWindow;
	
	/**
	 * The label of the main window that displays the current component's title.
	 */
	private static JLabel _componentTitleLabel;
	
	/**
	 * The label of the main window that displays the current time.
	 */
	private static JLabel _timeLabel;
	
	/**
	 * The label of the main window that displays the currently logged in user.
	 */
	private static JLabel _userLabel;
	
	/**
	 * A Timer that updates the time display.
	 */
	private static Timer _timer;
	
	/**
	 * A button that goes backwards.
	 */
	private static JButton _backButton;
	
	/**
	 * A button that logs out the current user.
	 */
	private static JButton _logOutButton;
	
	/**
	 * The stack of views displayed in this window.
	 */
	private static Stack<GeneralViewGUI> _viewStack;
	
	/**
	 * Creates a new PDSMainWindow object.  Sets up the JFrame.
	 */
	private PDSViewManager() {

		// Set the dimensions of this window.
		this.setSize( 800, 620);
		this.setLocation( 300, 150 );
		this.setResizable( false );

		// Set the default close operation and title of the window.
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setTitle( PDSViewManager.MAIN_WINDOW_NAME );

	}
	
	/**
	 * Initializes the main window.
	 */
	private static void initializeMainWindow() {
		
		// Construct the static PDSMainWindow() object.
		_mainWindow = new PDSViewManager();
		
		// Create a new stack.
		_viewStack = new Stack<GeneralViewGUI>();
		
		// Setup the mainwindow with a panel on top for the title, 
		// back button, and time.
		_mainWindow.getContentPane().setLayout( new BorderLayout() );
		
		// Create a panel for the top.
		JPanel topPanel = new JPanel();
		topPanel.setLayout( new GridLayout(1,2) );
		topPanel.setBorder( LineBorder.createGrayLineBorder() );
		
		// Set the title label.
		_componentTitleLabel = new JLabel();
		_componentTitleLabel.setFont( new Font( "SansSerif", Font.PLAIN, 30 ) );
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout( new FlowLayout( FlowLayout.LEADING ) );
		titlePanel.add( _componentTitleLabel );
		topPanel.add( titlePanel );

		// Add the back button, which is initially hidden.
		JPanel backPanel = new JPanel();
		backPanel.setLayout( new FlowLayout( FlowLayout.TRAILING ) );
		_backButton = new JButton( PDSViewManager.BACK_BUTTON_NAME );
		_backButton.setPreferredSize( new Dimension( 65, 33 ) );
		backPanel.add( _backButton );
		_backButton.setVisible( false );
		topPanel.add( backPanel );
		
		// Create a panel for the bottom.
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout( new GridLayout(1,2) );
		bottomPanel.setBorder( LineBorder.createGrayLineBorder() );

		// Add the log out button, which is initially hidden.
		JPanel logOutPanel = new JPanel();
		logOutPanel.setLayout( new FlowLayout( FlowLayout.LEADING ) );
		_logOutButton = new JButton( PDSViewManager.LOG_OUT_BUTTON_NAME);
		logOutPanel.add( _logOutButton );
		_logOutButton.setVisible( false );
		bottomPanel.add( logOutPanel );
		
		// Create a panel for the user and time.
		JPanel timeUserContainer = new JPanel();
		timeUserContainer.setLayout( new FlowLayout( FlowLayout.TRAILING ) );
		JPanel timeUserPanel = new JPanel();
		timeUserPanel.setLayout( new GridLayout( 2,1 ) );
		timeUserContainer.add( timeUserPanel );
		
		// Create a label for the time and the user.
		_timeLabel = new JLabel( "", JLabel.RIGHT );
		_userLabel = new JLabel( "", JLabel.RIGHT );
		timeUserPanel.add( _userLabel );
		timeUserPanel.add( _timeLabel );
		
		bottomPanel.add( timeUserContainer );

		// Add the panels to the main window.
		_mainWindow.getContentPane().add( topPanel, BorderLayout.NORTH );
		_mainWindow.getContentPane().add( bottomPanel, BorderLayout.SOUTH );
		
	}
	
	/**
	 * Hides or shows this window.
	 * 
	 * @param visible Whether or not to display the window.
	 */
	public static void setWindowVisible( boolean visible ) {
		
		// Initialize the main window if it is null.
		if( _mainWindow == null ) {
			PDSViewManager.initializeMainWindow();
		}
		
		_mainWindow.setVisible( visible );
		
		// Enable or disable the timer.
		if( visible ) {
			
			_timer = new Timer( 100, _mainWindow );
			_timer.start();
			
		} else if( _timer != null ) {

			_timer.stop();
			_timer = null;

		}
	
	}
	
	/**
	 * Enables or disables the back button on the main window.
	 * 
	 * @param enabled Whether the back button should be enabled or disabled.
	 */
	public static void setBackButtonEnabled( boolean enabled ){
		
		// Hide or show the back button.
		_backButton.setVisible( enabled );
		
	}
	
	/**
	 * Enables or disables the log out button on the main window.
	 * 
	 * @param enabled Whether the log out button should be enabled or disabled.
	 */
	public static void setLogOutButtonEnabled( boolean enabled ) {
		
		// Hide or show the log out button.
		_logOutButton.setVisible( enabled );
		
	}
	
	/**
	 * Adds a view to the stack.
	 * 
	 * @param newView  The new view to display.
	 */
	public static void pushView( GeneralViewGUI newView ) {
		
		// Remove the current view from display, but don't pop it.
		removeTopViewFromDisplay();
		
		// Push the new view and display it.
		_viewStack.push( newView );
		displayTopView();
		
	}
	
	/**
	 * Removes the topmost view from the stack.
	 */
	public static void popView() {

		// Remove the topmost view from display, and pop it.
		removeTopViewFromDisplay();
		_viewStack.pop();
		
		// Now display the top view.
		displayTopView();

	}
	
	/**
	 * Displays the topmost view.
	 */
	private static void displayTopView() {

		if( _viewStack.size() > 0 ) { 
				
			GeneralViewGUI topView = _viewStack.peek();
			
			// Add the top view to the panel.
			topView.getMainPanel().setBorder( LineBorder.createGrayLineBorder() );
			_mainWindow.getContentPane().add( topView.getMainPanel(), BorderLayout.CENTER );
			topView.setVisible( true );
	
			// Change the title label's text.
			_componentTitleLabel.setText( "  " + topView.getMainPanel().getName() );
	
			// Add the new view as an action listener.
			_backButton.addActionListener( topView );
			_logOutButton.addActionListener( topView );
			
		}

	}

	/**
	 * Removes the topmost view of the stack from display.
	 */
	private static void removeTopViewFromDisplay() {
		
		// Remove the current view.
		if( _viewStack.size() > 0 ) {

			GeneralViewGUI oldView = _viewStack.peek();
			oldView.setVisible( false );
			_mainWindow.getContentPane().remove( oldView.getMainPanel() );

			// Remove it from the list of action listeners for the button.
			_backButton.removeActionListener( oldView );
			_logOutButton.removeActionListener( oldView );

		}

	}
	
	/**
	 * Sets the text of the user display string.
	 */
	public static void setUserNameString( String name ) {
		_userLabel.setText( name );
	}
	
	/**
	 * Adds a window listener to the main window.
	 */
	public static void addNewWindowListener( WindowListener wl ) {
		_mainWindow.addWindowListener( wl );
	}
	
	/**
	 * Called at each timer tick.
	 */
	public void actionPerformed(ActionEvent arg0) {
		
		// Update the time label with the current time.
		_timeLabel.setText( Time.formatTime( SystemTime.getTime() ) );
		
	}
	
}
