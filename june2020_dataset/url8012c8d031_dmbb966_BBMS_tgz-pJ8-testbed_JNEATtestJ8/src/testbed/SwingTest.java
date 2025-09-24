package testbed;

import javax.swing.*;

public class SwingTest {
	
	SwingTest() {
		JFrame jfrm = new JFrame("A Simple Swing App");
		jfrm.setSize(275, 100);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel jlab = new JLabel(" Swing GUIs");
		jfrm.add(jlab);
		jfrm.setVisible(true);
	}
	
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SwingTest();
			}
		});
	}

}
