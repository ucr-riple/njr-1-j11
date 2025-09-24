package testbed;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SwingEvent {
	
	JLabel jlab;
	
	SwingEvent() {
		JFrame jfrm = new JFrame("Event Example");
		jfrm.setLayout(new FlowLayout());
		jfrm.setSize(220, 90);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton jbtnAlpha = new JButton("Alpha");
		JButton jbtnBeta = new JButton("Beta");
		
		jbtnAlpha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				jlab.setText("You pressed Alpha.");				
			}
		});
		
		jbtnBeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				jlab.setText("You pressed Beta.");
			}
		});
		
		jfrm.add(jbtnAlpha);
		jfrm.add(jbtnBeta);
		
		jlab = new JLabel("Press a button.");
		jfrm.add(jlab);
		jfrm.setVisible(true);
	}
	
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SwingEvent();
			}
		});
	}

}
