package managerreport;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import managerreport.ManagerReportView.ManagerReportInputChannel;
import managerreport.ManagerReportView.ManagerReportOutputChannel;
import model.ManagerReport;
import viewcontroller.GeneralController;
import viewcontroller.GeneralView.InputChannel;
import currentorders.CurrentOrdersView;
import currentorders.CurrentOrdersView.CurrentOrdersOutChan;

/**
 * The logic controller for the Manager Report module.
 * 
 * @author 	Isioma Nnodum	iun4534@rit.edu
 */
public class ManagerReportController extends GeneralController {
	
	/**
	 * The Manager Report currently being acted on.
	 */
	private ManagerReport _currentManagerReport;
	
	/**
	 * The states of this controller.
	 */
	private enum ManagerReportControllerState implements ControllerState {
			
		CSDisplayingManagerReport;
	}
	
	/**
	 * Default constructor.
	 */
	public ManagerReportController() {
		_currentManagerReport = new ManagerReport();
	}
	
	/**
	 * Moves the controller into the DisplayingManager report state.
	 */
	public void enterInitialState() {

		this.active = true;
		
		gotoDisplayingManagerReport();
		
		if ( PizzaDeliverySystem.RUN_WITH_GUI ) {
			PDSViewManager.pushView( (ManagerReportViewGUI)view );
			gotoDisplayingManagerReport();
		} else {
			((ManagerReportViewCL)view).getUserInput();
		}
		
	}

	/**
	 * @see viewcontroller.GeneralController#respondToInput(java.lang.String, viewcontroller.GeneralView.InputChannel)
	 */
	public void respondToInput(String message, InputChannel channel) {
		
		switch((ManagerReportControllerState) currentState) {
		
		case CSDisplayingManagerReport:
			handleDisplayingManagerReport(message, (ManagerReportInputChannel)channel);
			break;
		}
		
	}
		
	/**
	 * Moves the controller to the DisplayingManagerReport state.
	 */
	private void gotoDisplayingManagerReport() {
		
		this.currentState = ManagerReportControllerState.CSDisplayingManagerReport;
		
		//display the manager report
		view.displayObject(_currentManagerReport, ManagerReportOutputChannel.OCDisplayManagerReport);
		
		view.setChannelEnabled(ManagerReportInputChannel.ICMenuOption, true);
		view.setChannelEnabled(ManagerReportInputChannel.ICRefresh, true);
		
	}
	
	/**
	 * Handles input in the DisplayingManagerReport state.
	 * @param message	The input message.
	 * @param channel	The channel on which the input was received.
	 */
	private void handleDisplayingManagerReport(String message, 
									ManagerReportInputChannel channel) {
		
		if ( ManagerReportInputChannel.ICMenuOption == channel) {
			
			if (ManagerReportView.BACK_KEY.equals(message)) {
				this.active = false;
				if( PizzaDeliverySystem.RUN_WITH_GUI) {
					PDSViewManager.popView();
				}
			} else if (ManagerReportView.REFRESH_KEY.equals(message)) {
				this.gotoDisplayingManagerReport();
			} else {
				handleInputError("The input was invalid. Please try again.");
			}
			
		}
			
		else if ( ManagerReportInputChannel.ICRefresh == channel ) {
			
			if( message.equalsIgnoreCase( CurrentOrdersView.REFRESH_KEY ) ) {
				// Reload this state.
				gotoDisplayingManagerReport();

			}
			
		}
		
		else {
			handleInputError("The input was invalid. Please try again.");
		}
	}
	
	/**
	 * Sends an error to the view.
	 */
	public void handleInputError(String message) {
		
		view.displayString( message, CurrentOrdersOutChan.OCError );

	}
	
}
