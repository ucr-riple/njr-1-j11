package managerreport;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

/**
 * A table view cell that displays a customer's information.
 * 
 * @author  Isioma Nnodum	iun4534@rit.edu
 */
@SuppressWarnings("serial")
public class ManagerReportLabel extends JLabel {

	/**
	 * The width of a cell.
	 */
	public static final int MAIN_CELL_WIDTH = 500;
        public static final int DATA_CELL_WIDTH =  100;
	
	/**
	 * The height of a cell.
	 */
	public static final int MAIN_CELL_HEIGHT = 10;
    
	/**
	 * The height of a data cell.
	 */
	public static final int DATA_CELL_HEIGHT = MAIN_CELL_HEIGHT;
	
	/**
	 * Default constructor.
	 */
	public ManagerReportLabel() {}  
        
	/**
	 * Creates a new cell from the given customer 
	 * 
	 * @param customer The customer whose data should appear here.
	 */
	public static ManagerReportLabel dataLabel( String text ) {

                ManagerReportLabel mglabl = new ManagerReportLabel();
                
		mglabl.setSize( DATA_CELL_WIDTH, DATA_CELL_HEIGHT );
		
		// Create a JLabel for the customer's name.
		mglabl.setHorizontalAlignment( JLabel.LEADING );
		mglabl.setFont( new Font("SansSerif", Font.PLAIN, 16 ) );
		mglabl.setText(text);
                mglabl.setForeground( new Color( 50, 50, 50 ) );
                return mglabl;

	}
        
	public static ManagerReportLabel dataLabel() {

        ManagerReportLabel mglabl = new ManagerReportLabel();
                
		mglabl.setPreferredSize(new Dimension(DATA_CELL_WIDTH, DATA_CELL_HEIGHT));
		
		// Create a JLabel for the customer's name.
		mglabl.setFont( new Font("SansSerif", Font.PLAIN, 14 ) );
		mglabl.setForeground( new Color( 50, 50, 50 ) );
                
		return mglabl;

	}
        
	public static ManagerReportLabel mainLabel( String text ) {

		ManagerReportLabel mglabl = new ManagerReportLabel();
                
		mglabl.setSize( new Dimension(MAIN_CELL_WIDTH, MAIN_CELL_HEIGHT) );
		mglabl.setHorizontalAlignment( JLabel.TRAILING );

		// Create a JLabel for the customer's name.
		mglabl.setFont( new Font("SansSerif", Font.BOLD, 14 ) );
		mglabl.setText(text);
                
		return mglabl;
	
	}

}
