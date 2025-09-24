package cz.dusanrychnovsky.chessendgames.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.SystemColor;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Dušan Rychnovský
 *
 */
public class StatusBar extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private final JLabel label;
	
	/**
	 * 
	 */
	public StatusBar()
	{
		setPreferredSize(new Dimension(424, 18));
		setBackground(SystemColor.control);
		
		setLayout(new BorderLayout());
		
		label = new JLabel();
		add(label, BorderLayout.CENTER);
	}
	
	/**
	 * 
	 * @param message
	 */
	public void setStatusMessage(String message) {
		label.setText(message);
	}
}
