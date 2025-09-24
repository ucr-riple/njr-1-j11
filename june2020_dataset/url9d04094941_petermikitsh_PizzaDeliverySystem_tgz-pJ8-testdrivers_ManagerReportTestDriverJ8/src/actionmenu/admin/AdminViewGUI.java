package actionmenu.admin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.PDSViewManager;
import viewcontroller.GeneralViewGUI;

public class AdminViewGUI extends AdminView implements GeneralViewGUI {
	
	/**
	 * The name of the edit menu button
	 */
	private static final String EDIT_MENU_BUTTON_NAME = "<html><center>Edit Menu</center></html>";
	
	/**
	 * The name of the view past orders button.
	 */
	private static final String VIEW_PAST_ORDERS_BUTTON_NAME = "<html><center>View Past Orders</center></html>";

	/**
	 * The name of the view manager report button.
	 */
	private static final String VIEW_MANAGER_REPORT_BUTTON_NAME = "<html><center>View Report</center></html>";
	
	/**
	 * The name of the kitchen manager button.
	 */
	private static final String KITCHEN_MANAGER_BUTTON_NAME = "<html><center>Manage Kitchen</center></html>";
	
	/**
	 * The name of the edit users button
	 */
	private static final String EDIT_USERS_BUTTON_NAME = "<html><center>Manage Users</center></html>";
	
	/**
	 * The size of a button.
	 */
	private static final Dimension BUTTON_SIZE = new Dimension( 200, 150 ); 

	/**
	 * The JPanel to which contains everything in this view.
	 */
	private JPanel _mainPanel;

	/** 
	 * The button that goes to the edit menu component.
	 */
	private JButton _editMenuButton;

	/** 
	 * The button that goes to the view past order component.
	 */
	private JButton _viewPastOrdersButton;

	/** 
	 * The button that goes to the manager report component.
	 */
	private JButton _viewManagerReportButton;

	/** 
	 * The button that goes to the kitchen management component.
	 */
	private JButton _kitchenManagerButton;
	
	/**
	 * The button that goes to the edit users component.
	 */
	private JButton _editUsersButton;
	
	public AdminViewGUI() {
		
		// Initialize the main panel
		_mainPanel = new JPanel();
		_mainPanel.setLayout(new BorderLayout());
		_mainPanel.setName( "Administrative Options" );
		
		// Create a panel for the center.
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		_mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		// create the buttons
		Font buttonFont = new Font( "SansSerif", Font.BOLD, 18 );
		
		_editMenuButton = new JButton(EDIT_MENU_BUTTON_NAME);
		_editMenuButton.setPreferredSize( BUTTON_SIZE );
		_editMenuButton.addActionListener(this);
		_editMenuButton.setFont( buttonFont );
		
		_kitchenManagerButton = new JButton(KITCHEN_MANAGER_BUTTON_NAME);
		_kitchenManagerButton.setPreferredSize( BUTTON_SIZE );
		_kitchenManagerButton.addActionListener(this);
		_kitchenManagerButton.setFont( buttonFont );
		
		_viewManagerReportButton= new JButton(VIEW_MANAGER_REPORT_BUTTON_NAME);
		_viewManagerReportButton.setPreferredSize( BUTTON_SIZE );
		_viewManagerReportButton.addActionListener(this);
		_viewManagerReportButton.setFont( buttonFont );
		
		_viewPastOrdersButton = new JButton(VIEW_PAST_ORDERS_BUTTON_NAME);
		_viewPastOrdersButton.setPreferredSize( BUTTON_SIZE );
		_viewPastOrdersButton.addActionListener(this);
		_viewPastOrdersButton.setFont( buttonFont );
		
		_editUsersButton = new JButton(EDIT_USERS_BUTTON_NAME);
		_editUsersButton.setPreferredSize( BUTTON_SIZE );
		_editUsersButton.addActionListener(this);
		_editUsersButton.setFont( buttonFont );
		
		//Now add the buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout( new GridBagLayout() );
		GridBagConstraints buttonConst = new GridBagConstraints();

		JPanel topPanel = new JPanel();
		topPanel.setLayout( new GridBagLayout() );
		GridBagConstraints topConst = new GridBagConstraints();
		topConst.insets = new Insets( 5, 5, 5, 5 );
		topPanel.add( _editMenuButton, topConst );
		topConst.gridx = 1;
		topPanel.add( _kitchenManagerButton, topConst );
		topConst.gridx = 2;
		topPanel.add( _editUsersButton, topConst );
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout( new GridBagLayout() );
		GridBagConstraints bottomConst = new GridBagConstraints();
		bottomConst.insets = topConst.insets;
		bottomConst.fill = GridBagConstraints.BOTH;
		bottomPanel.add( _viewManagerReportButton, bottomConst );
		bottomConst.gridx = 1;
		bottomPanel.add( _viewPastOrdersButton, bottomConst );
		
		buttonPanel.add( topPanel, buttonConst );
		buttonConst.gridy = 1;
		buttonPanel.add( bottomPanel, buttonConst);
		
		centerPanel.add(buttonPanel, new GridBagConstraints() );
		
	}
	
	/**
	 * @see viewcontroller.GeneralView#displayString(java.lang.String, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayString(String message, OutputChannel outChannel) {

		// We shouldn't ever have to display any strings here.
		
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
		
	}
	
	/**
	 * @see viewcontroller.GeneralView#setChannelEnabled( InputChannel inChannel, boolean enabled )
	 */
	public void setChannelEnabled(InputChannel inChannel, boolean enabled) {

		switch( (AdminInChan)inChannel ) {
		
		case ICMenuOption:
			
			// Enable or disable the buttons.
			_viewManagerReportButton.setEnabled( enabled );
			_viewPastOrdersButton.setEnabled( enabled );
			_editUsersButton.setEnabled( enabled );
			_editMenuButton.setEnabled( enabled );
			_kitchenManagerButton.setEnabled( enabled );
			
			break;
			
		}
		
	}
	
	public void actionPerformed(ActionEvent aEvent) {
		
		if( aEvent.getSource() instanceof JButton ) {
		
			JButton sourceButton = (JButton)aEvent.getSource();
			
			if ( sourceButton.equals(_editMenuButton)) {
				controller.respondToInput( AdminView.EDIT_MENU_KEY, AdminInChan.ICMenuOption);
			} else if ( sourceButton.equals(_editUsersButton)) {
				controller.respondToInput( AdminView.EDIT_USER_KEY, AdminInChan.ICMenuOption);
			} else if ( sourceButton.equals(_kitchenManagerButton)) {
				controller.respondToInput( AdminView.MANAGE_KEY, AdminInChan.ICMenuOption);
			} else if ( sourceButton.equals(_viewManagerReportButton)) {
				controller.respondToInput( AdminView.REPORTS_KEY, AdminInChan.ICMenuOption);
			} else if ( sourceButton.equals(_viewPastOrdersButton)) {
				controller.respondToInput( AdminView.PAST_ORDERS_KEY, AdminInChan.ICMenuOption);
			} else if (sourceButton.getText().equals(PDSViewManager.BACK_BUTTON_NAME)) {
				controller.respondToInput( AdminView.BACK_KEY, AdminInChan.ICMenuOption);
			}
 		
		}
		
	}

}
