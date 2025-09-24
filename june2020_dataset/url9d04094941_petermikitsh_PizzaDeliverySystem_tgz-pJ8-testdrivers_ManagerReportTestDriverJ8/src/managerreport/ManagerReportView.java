package managerreport;

import viewcontroller.GeneralView;

/**
 * The abstract view for the ManagerReport module.
 * This will have two subclasses, one for each type of view: command-line, and
 *  GUI.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public abstract class ManagerReportView extends GeneralView {
	
	/**
	 * The menu selection to refresh the manager report.
	 */
	public static final String REFRESH_KEY = "REF";
	
	/**
	 * The menu selection key for going back.
	 */
	public static final String BACK_KEY = "BCK";
	
	/**
	 * The input channels for this view.
	 */
	public enum ManagerReportInputChannel implements InputChannel {
		
		ICListSelection,
		ICMenuOption,
		ICStringInput,
		ICRefresh;
		
	}
	
	/**
	 * The output channels for this view.
	 */
	public enum ManagerReportOutputChannel implements OutputChannel {
		
		OCList,
		OCInstructions,
		OCConfirm,
		OCError,
		OCDisplayManagerReport;
		
	}

}
