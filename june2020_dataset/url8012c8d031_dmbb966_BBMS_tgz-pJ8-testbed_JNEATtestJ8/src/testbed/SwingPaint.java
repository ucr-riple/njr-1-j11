package testbed;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

// Extends JPanel - overrides paintComponent() method.
class PaintPanel extends JPanel {
	
	Insets ins;		// Holds the panel's insets
	Random rand;	
	
	PaintPanel() {
		// Creates the border around the panel
		setBorder(BorderFactory.createLineBorder(Color.RED, 5));
		
		rand = new Random();
	}
	
	// Overrides the paintComponent() method
	protected void paintComponent (Graphics g) {
		// Call the superclass method first.  Always. 
		// This makes sure the component is properly painted first.
		super.paintComponent(g);
		
		int x, y, x2, y2;
		
		// Get the height and width of the component
		int height = getHeight();
		int width = getWidth();
		ins = getInsets();
		
		// Draw ten lines with randomly generated endpoints
		for(int i = 0; i < 10; i++) {
			x = rand.nextInt(width - ins.left);
			y = rand.nextInt(height - ins.bottom);
			x2 = rand.nextInt(width - ins.left);
			y2 = rand.nextInt(height - ins.bottom);
			
			g.drawLine(x, y, x2, y2);
		}
	}
}

public class SwingPaint {
	JLabel jlab;
	PaintPanel pp;
	
	SwingPaint() {
		JFrame jfrm = new JFrame("Paint Demo");
		jfrm.setSize(640, 480);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pp = new PaintPanel();
		
		// Add the panel to the content pane, automatically sizing it to fit the center region
		// since the default border layout is used.
		jfrm.add(pp);
		jfrm.setVisible(true);
	}
	
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SwingPaint();
			}
		});
	}

}
