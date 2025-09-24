package cz.dusanrychnovsky.chessendgames.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

public class SplashScreen extends JFrame
{
	private static final long serialVersionUID = 1L;

	public SplashScreen()
	{
		setUndecorated(true);
		setLayout(new BorderLayout());
		
		JLabel label = new JLabel("Loading application data. Please wait ...", SwingConstants.CENTER); 
		add(label, BorderLayout.CENTER);
		
		setPreferredSize(new Dimension(300, 100));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void close() 
	{
		setVisible(false);
		dispose();
	}
}
