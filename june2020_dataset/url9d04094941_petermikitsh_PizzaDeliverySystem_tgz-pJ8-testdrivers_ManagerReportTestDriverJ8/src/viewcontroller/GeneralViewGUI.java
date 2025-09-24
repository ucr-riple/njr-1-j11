package viewcontroller;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

/**
 * Specifies the methods that must be implemented by all GUI views.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public interface GeneralViewGUI extends ActionListener {
	
	/**
	 * Returns the JPanel into which this view will draw all of its data.
	 * 
	 * @return This view's main panel.
	 */
	public JPanel getMainPanel();
	
	/**
	 * Sets the main panel visible or invisble.
	 * 
	 * @param visible Whether or not to set the panel visible.
	 */
	public void setVisible( boolean visible );

}
