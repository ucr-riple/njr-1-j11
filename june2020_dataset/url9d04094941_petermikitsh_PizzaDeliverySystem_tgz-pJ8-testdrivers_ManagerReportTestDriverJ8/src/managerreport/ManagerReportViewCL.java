
package managerreport;

import java.util.ArrayList;
import java.util.Scanner;

import model.ManagerReport;

/**
 * The command line view for the ManagerReport module.
 * 
 * @author isioma Nnodum iun4534@rit.edu
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public class ManagerReportViewCL extends ManagerReportView {
	
	/**
	 * This view's scanner.
	 */
	private Scanner scan;
	
	/**
	 * The default constructor.
	 */
	public ManagerReportViewCL() {
		
		scan = new Scanner( System.in );
		
	}

	/**
	 * @see viewcontroller.GeneralView#displayString(java.lang.String, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayString(String message, OutputChannel outChannel) {
		
		// Pretty much everything is just written to the terminal, but
		// we'll insert some tags to help with tracing.
		switch( (ManagerReportOutputChannel)outChannel ) {
		
		case OCInstructions:
			System.out.println( "Instructions: " + message );
			break;
			
		case OCConfirm:
			System.out.println( "Confirm: " + message );
			break;
			
		case OCError:
			System.err.println( "Error: " + message );
			break;
		
		}
		
		System.out.println();
		
	}


	/**
	 * @see viewcontroller.GeneralView#displayObject(java.lang.Object, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayObject(Object object, OutputChannel outChannel) {
		
		if( outChannel == ManagerReportOutputChannel.OCDisplayManagerReport) {
			
			// The object is a customer.
			ManagerReport managerReport = (ManagerReport) object;
			System.out.println("Total Orders: " +managerReport.getNumberOfOrders());
			System.out.println("Average Cost: "+managerReport.calculateAvgCostPerOrder());
			System.out.println("Maximum Preparation Wait time: "+managerReport.calculateMaxTimeWaitingForPreparation());
			System.out.println("Average Preparation Wait Time: "+managerReport.calculateAvgTimeWaitingForPreparation());
			System.out.println("Maximum Cook Wait Time: "+managerReport.calculateMaxTimeWaitingToCook());
			System.out.println("Average Cook Wait Time: "+managerReport.calculateAvgTimeWaitingToCook());
			System.out.println("Maximum Delivery Pickup Time: "+managerReport.calculateMaxTimeWaitingToBeRetrievedForDelivery());
			System.out.println("Average Delivery Pickup Time: "+managerReport.calculateAvgTimeWaitingToBeRetrievedForDelivery());
			System.out.println("Maximum Order to Delivery Time: "+managerReport.maxTotalTime());
			System.out.println("Average Order to Delivery Time: "+managerReport.avgTotalTime());
			System.out.println();
			
		}
		
	}
	
	/**
	 * @see viewcontroller.GeneralView#displayList(java.util.ArrayList, viewcontroller.GeneralView.OutputChannel)
	 */
	public <T> void displayList(ArrayList<T> list, OutputChannel outChannel) {
		
		if( outChannel == ManagerReportOutputChannel.OCList ) {
			
			for( int i = 0; i < list.size(); i++ ) {
				
				System.out.println(list.get(i));
				
			}
				
			System.out.println();
			
		}
		
	}
	
	/**
	 * Tells the view to get text input from the user, and iterates this
	 *  until it is told to stop.
	 */
	public void getUserInput() {
		
		// Display the list of available commands.
		// displayCommandList();
		
		while( this.controller.isActive() ) {

			// Read in a nonempty string.
			String input = null;
			while( input == null || input.length() == 0 ) {
				System.out.print( "> " );
				input = scan.nextLine();
			}	

			// Check the input and send it to the controller through the 
			// appropriate channel.
			ManagerReportInputChannel channel = ManagerReportInputChannel.ICStringInput;

			// If the first character is '*', the input is a menu option.
			if( input.charAt(0) == '*' ) {

				channel = ManagerReportInputChannel.ICMenuOption;
				input = input.substring( 1 );
				controller.respondToInput( input, channel );
				
			}

			// Send the input to the controller.
			
			
		}
		
	}
	
	
	/**
	 * @see viewcontroller.GeneralView#setChannelEnabled(viewcontroller.GeneralView.InputChannel, boolean)
	 */

	public void setChannelEnabled(InputChannel inChannel, boolean enabled) {

		switch ((ManagerReportInputChannel)inChannel ) {
		
		case ICMenuOption:
			System.out.println( "  *" + ManagerReportView.REFRESH_KEY + 
					" :  Refresh report." );
			System.out.println( "  *" + ManagerReportView.BACK_KEY + 
					" :  Return to the main prompt." );
			
			
			System.out.println();
			break;
		}
		
		
	}


}
