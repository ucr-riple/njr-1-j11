package testbed;

import gui.GUIMenu;

import java.awt.*;

import javax.swing.*;

public class GUIMain {
	
	public static GUIConsole GC = new GUIConsole();
	
	GUIMain(){		
		
		JFrame jf = new JFrame("Bare Bones Military Simulator");
		
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(1024, 800);
		jf.setResizable(false);		
		jf.setJMenuBar(new GUIMenu());
		jf.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// Add the main display		
		c.gridx = 0;
		c.gridy = 0;
		//c.gridwidth = GridBagConstraints.REMAINDER;
		//c.fill = GridBagConstraints.HORIZONTAL;		
		//c.weightx = 1;
		//c.weighty = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		jf.add(new GUIMainDisp(), c);
		
		c = new GridBagConstraints();
		c.gridx = 4;
		c.gridy = 0;
		c.weightx = 0.2;
		c.gridheight = 10;
		//c.weighty = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		jf.add(new GUIInfoDisp(), c);
		
		// Add the GUI Console
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;		
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 30;
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LAST_LINE_START;
		jf.add(GC, c);
		
		jf.setVisible(true);		
		GC.GCOutput("Hello world!");
	}
	
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GUIMain();
			}
		});
	}
	
	/**
	 * Prints output to the GUI Console output.  Automatically appends newline.
	 */
	public static void GCO(String str) {
		GC.GCOutput(str);
	}
}

