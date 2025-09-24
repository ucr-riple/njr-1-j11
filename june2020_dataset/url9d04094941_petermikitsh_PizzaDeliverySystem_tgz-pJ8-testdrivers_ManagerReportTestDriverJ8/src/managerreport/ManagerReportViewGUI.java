package managerreport;

import gui.scrolltable.ZScrollTable;
import gui.scrolltable.ZScrollTableCell;
import gui.scrolltable.ZScrollTableDelegate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import main.PDSViewManager;
import model.ManagerReport;
import viewcontroller.GeneralViewGUI;
import actionmenu.admin.AdminView;

/**
 * The GUI view for the ManagerReport module.
 * 
 * @author  Andrew Zemke	drew.zemke@gmail.com
 */
public class ManagerReportViewGUI extends ManagerReportView 
	implements GeneralViewGUI, ZScrollTableDelegate {
	
	public static final int CENTER_PANEL_WIDTH = 600;
    public static final int CENTER_PANEL_HEIGHT = 100;
    public static final int SIDE_PANEL_WIDTH = 100;
    
    private static final String TOTAL_ORDERS_TEXT = "Total orders";
    private static final String AVERAGE_COST_TEXT = "Average cost (without tax)";
    private static final String AVERAGE_PREP_TEXT = "Average time waiting for preparation";
    private static final String MAXIMUM_PREP_TEXT = "Maximum time waiting for preparation";
    private static final String AVERAGE_COOK_TEXT = "Average time waiting to cook";
    private static final String MAXIMUM_COOK_TEXT = "Maximum time waiting to cook";
    private static final String AVERAGE_DELPICK_TEXT = "Average time waiting to be picked up";
    private static final String MAXIMUM_DELPICK_TEXT = "Maximum time waiting to be picked up";
    private static final String AVERAGE_ORDDEL_TEXT = "Average time from order call-in to delivery";
    private static final String MAXIMUM_ORDDEL_TEXT = "Maximum time from order call-in to delivery";
   
    private static final String DELIMITER = ": ";
    private JPanel _mainPanel;
   
    private ManagerReportLabel _totalOrdersLabelVal;
    private ManagerReportLabel _averageCostLabelVal;
    private ManagerReportLabel _averagePrepLabelVal;
    private ManagerReportLabel _maximumPrepLabelVal;
    private ManagerReportLabel _averageCookLabelVal;
    private ManagerReportLabel _maximumCookLabelVal;
    private ManagerReportLabel _averageDelPickLabelVal;
    private ManagerReportLabel _maximumDelPickLabelVal;
    private ManagerReportLabel _averageOrdDelLabelVal;
    private ManagerReportLabel _maximumOrdDelLabelVal;
    
    /**
     * The list of labels.
     */
    private ArrayList<JLabel> _labelList;
    
    /**
     * The list of values.
     */
    private ArrayList<JLabel> _valueList;
    
    /**
     * The table that displays values.
     */
    private ZScrollTable _table;

    /**
	 * A Timer that updates the time display.
	 */
	private static Timer _refreshTimer;
    
    public ManagerReportViewGUI() {

        _mainPanel = new JPanel();
        _mainPanel.setLayout(new BorderLayout());
        _mainPanel.setName("Manager Report");

        // Create the labels.
        _labelList = new ArrayList<JLabel>();
        _labelList.add( ManagerReportLabel.mainLabel(TOTAL_ORDERS_TEXT + DELIMITER + " ") );
        _labelList.add( ManagerReportLabel.mainLabel(AVERAGE_COST_TEXT + DELIMITER + " ") );
        _labelList.add( ManagerReportLabel.mainLabel(AVERAGE_PREP_TEXT + DELIMITER + " ") );
        _labelList.add( ManagerReportLabel.mainLabel(MAXIMUM_PREP_TEXT + DELIMITER + " ") );
        _labelList.add( ManagerReportLabel.mainLabel(AVERAGE_COOK_TEXT + DELIMITER + " ") );
        _labelList.add( ManagerReportLabel.mainLabel(MAXIMUM_COOK_TEXT + DELIMITER + " ") );
        _labelList.add( ManagerReportLabel.mainLabel(AVERAGE_DELPICK_TEXT + DELIMITER + " ") );
        _labelList.add( ManagerReportLabel.mainLabel(MAXIMUM_DELPICK_TEXT + DELIMITER + " ") );
        _labelList.add( ManagerReportLabel.mainLabel(AVERAGE_ORDDEL_TEXT + DELIMITER + " ") );
        _labelList.add( ManagerReportLabel.mainLabel(MAXIMUM_ORDDEL_TEXT + DELIMITER + " ") );
        
        // Create the values.
        _totalOrdersLabelVal = ManagerReportLabel.dataLabel();
        _averageCostLabelVal = ManagerReportLabel.dataLabel();
        _averagePrepLabelVal = ManagerReportLabel.dataLabel();
        _maximumPrepLabelVal = ManagerReportLabel.dataLabel();
        _averageCookLabelVal = ManagerReportLabel.dataLabel();
        _maximumCookLabelVal = ManagerReportLabel.dataLabel();
        _averageDelPickLabelVal = ManagerReportLabel.dataLabel();
        _maximumDelPickLabelVal = ManagerReportLabel.dataLabel();
        _averageOrdDelLabelVal = ManagerReportLabel.dataLabel();
        _maximumOrdDelLabelVal = ManagerReportLabel.dataLabel();
        
        _valueList = new ArrayList<JLabel>();
        _valueList.add( _totalOrdersLabelVal = ManagerReportLabel.dataLabel() );
        _valueList.add( _averageCostLabelVal = ManagerReportLabel.dataLabel() );
        _valueList.add( _averagePrepLabelVal = ManagerReportLabel.dataLabel() );
        _valueList.add( _maximumPrepLabelVal = ManagerReportLabel.dataLabel() );
        _valueList.add( _averageCookLabelVal = ManagerReportLabel.dataLabel() );
        _valueList.add( _maximumCookLabelVal = ManagerReportLabel.dataLabel() );
        _valueList.add( _averageDelPickLabelVal = ManagerReportLabel.dataLabel() );
        _valueList.add( _maximumDelPickLabelVal = ManagerReportLabel.dataLabel() );
        _valueList.add( _averageOrdDelLabelVal = ManagerReportLabel.dataLabel() );
        _valueList.add( _maximumOrdDelLabelVal = ManagerReportLabel.dataLabel() );

        // Create a panel for the center.
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout( new GridBagLayout() );
        
        // Create a table.
        _table = new ZScrollTable( this );
        _table.setPreferredSize( new Dimension( this.getCellWidth( _table ),
        								this.getCellHeight( _table, 0) * 10 + 12 ) );
        centerPanel.add( _table, new GridBagConstraints() );
        
        _mainPanel.add( centerPanel, BorderLayout.CENTER );

    }
  
	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#getNumberOfCells(gui.scrolltable.ZScrollTable)
	 */
	public int getNumberOfCells(ZScrollTable table) {
		return 10;
	}
	
	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#getCellSpacing(gui.scrolltable.ZScrollTable)
	 */
	public int getCellSpacing(ZScrollTable table) {
		return 1;
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#getTableBGColor(gui.scrolltable.ZScrollTable)
	 */
	public Color getTableBGColor(ZScrollTable table) {
		return new Color( 100, 100, 100 );
	}
	
	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getHeaderCell(gui.scrolltable.ZScrollTable)
	 */
	public ZScrollTableCell getHeaderCell( ZScrollTable table ) {
		return null;
	}
	
	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getEmptyTableMessage(gui.scrolltable.ZScrollTable)
	 */
	public String getEmptyTableMessage( ZScrollTable table ) {
		
		// This should never be called.
		return "";
		
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#getCell(gui.scrolltable.ZScrollTable, int)
	 */
	public ZScrollTableCell getCell(ZScrollTable table, int index) {
		
		// Create a new cell and add the information from the arrays.
		ZScrollTableCell cell = new ZScrollTableCell();
		cell.setPreferredSize( new Dimension( this.getCellWidth( null ), this.getCellHeight( null, index ) ) );
		
		cell.setLayout( new GridLayout( 1, 2 ) );
		
		JLabel labelLabel = _labelList.get( index );
		labelLabel.setHorizontalAlignment( JLabel.TRAILING);
		cell.add( labelLabel );
		
		JLabel valueLabel = _valueList.get( index );
		valueLabel.setHorizontalAlignment( JLabel.LEADING );
		cell.add( valueLabel );
		
		// Color the cell based on the index.
		int shade = 220 + (index % 2) * 20;
		cell.setBackground( new Color( shade, shade, shade ) );

		return cell;
		
	}

	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getCellWidth(gui.scrolltable.ZScrollTable)
	 */
	public int getCellWidth(ZScrollTable table) {
		return 700;
	}
	
	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getCellHeight(gui.scrolltable.ZScrollTable, int)
	 */
	public int getCellHeight(ZScrollTable table, int index) {
		return 40;
	}


	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#cellWasClicked(gui.scrolltable.ZScrollTable, int)
	 */
	public void cellWasClicked(ZScrollTable table, int index) {
		// Do nothing.
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#canDeleteCell(gui.scrolltable.ZScrollTable, int)
	 */
	public boolean canDeleteCell(ZScrollTable table, int index) {
		return false;
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#cellWasDeleted(gui.scrolltable.ZScrollTable, int)
	 */
	public void cellWasDeleted(ZScrollTable table, int index) {
		
		// Do nothing.
		
	}

	/**
	 * Gets the values to set for the value labels.
	 * 
	 * @param report the Manager Report to get the values from
	 */
    private void setReportText(ManagerReport report) {

        _totalOrdersLabelVal.setText(report.printNumberOfOrders());
        _averageCostLabelVal.setText(report.printAvgCostPerOrder());
        _averagePrepLabelVal.setText(report.printAvgTimeWaitingForPreparation());
        _maximumPrepLabelVal.setText(report.printMaxTimeWaitingForPreparation());
        _averageCookLabelVal.setText(report.printAvgCookingWaitTime());
        _maximumCookLabelVal.setText(report.printMaxCookingWaitTime());
        _averageDelPickLabelVal.setText(report.printAvgTimeWaitingToBeRetrievedForDelivery());
        _maximumDelPickLabelVal.setText(report.printMaxTimeWaitingToBeRetrievedForDelivery());
        _averageOrdDelLabelVal.setText(report.printAvgTotalTime());
        _maximumOrdDelLabelVal.setText(report.printMaxTotalTime());

    }

    /**
     * @see viewcontroller.GeneralView#displayString(java.lang.String,
     *      viewcontroller.GeneralView.OutputChannel)
     */
    public void displayString(String message, OutputChannel outChannel) {

        // We shouldn't ever have to display any strings here.

        _mainPanel.updateUI();

    }

    /**
     * @see viewcontroller.GeneralView#displayObject(java.lang.Object,
     *      viewcontroller.GeneralView.OutputChannel)
     */
    public void displayObject(Object object, OutputChannel outChannel) {

        switch ((ManagerReportOutputChannel) outChannel) {
            case OCDisplayManagerReport:
                setReportText((ManagerReport) object);
                _table.updateTable();
                break;
                
        }
        
    }

    /**
     * @see viewcontroller.GeneralView#displayList(java.util.ArrayList,
     *      viewcontroller.GeneralView.OutputChannel)
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

    public void setVisible(boolean visible) {

        // Pass the change on to the main panel.
        _mainPanel.setVisible(visible);

        // Hide the back button if this is going to be displayed.
        PDSViewManager.setBackButtonEnabled(visible);

        // Enable or disable the timer.
		if( visible ) {
			
			_refreshTimer = new Timer( 500, this );
			_refreshTimer.start();
			
		} else if( _refreshTimer != null ) {

			_refreshTimer.stop();
			_refreshTimer = null;

		}
        
    }

    /**
	 * @see viewcontroller.GeneralView#setChannelEnabled( InputChannel inChannel, boolean enabled )
	 */
    public void setChannelEnabled(InputChannel inChannel, boolean enabled) {

        switch ((ManagerReportInputChannel) inChannel) {

            case ICMenuOption:


        }

    }

    public void actionPerformed(ActionEvent aEvent) {

        if (aEvent.getSource() instanceof JButton) {

            JButton sourceButton = (JButton) aEvent.getSource();

            if (sourceButton.getText().equals(
                    PDSViewManager.BACK_BUTTON_NAME)) {
                controller.respondToInput(AdminView.BACK_KEY,
                        ManagerReportInputChannel.ICMenuOption);
            }

        }
        else if ( aEvent.getSource() instanceof Timer ) {
		
		// Send the refresh message.
		controller.respondToInput( ManagerReportView.REFRESH_KEY,
									ManagerReportInputChannel.ICRefresh );
		
        }
    
    }
    
}
