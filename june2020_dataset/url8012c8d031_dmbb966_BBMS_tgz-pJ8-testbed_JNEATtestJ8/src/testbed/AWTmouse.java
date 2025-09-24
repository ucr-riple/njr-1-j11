package testbed;

import java.awt.*;
import java.awt.event.*;


public class AWTmouse extends Frame {
	String keymsg = "This is a test.";
	String mousemsg = "Mouse message.";
	int mouseX = 30, mouseY = 30;
	
	public AWTmouse() {
		addKeyListener(new MyKeyAdapter(this));
		addMouseListener(new MyMouseAdapter(this));
		addWindowListener(new MyWindowAdapter());
	}
	
	public void paint(Graphics g) {
		g.drawString(keymsg,  10,  40);
		g.drawString(mousemsg,  mouseX,  mouseY);		
	}
	
	// Creates the window
	public static void main(String args[]) {
		AWTmouse appwin = new AWTmouse();
		
		appwin.setSize(640, 480);
		appwin.setTitle("AWT-Mouse Lisetner");
		appwin.setVisible(true);
		
		
	}
	
}

class MyKeyAdapter extends KeyAdapter {
	AWTmouse appWindow;
	
	public MyKeyAdapter(AWTmouse appWindow) {
		this.appWindow = appWindow;
	}
	
	public void keyTyped(KeyEvent ke) {
		appWindow.keymsg += ke.getKeyChar();
		appWindow.repaint();
	};	
}

class MyMouseAdapter extends MouseAdapter {
	AWTmouse appWindow;
	
	public MyMouseAdapter(AWTmouse appWindow) {
		this.appWindow = appWindow;
	}
	
	public void mousePressed(MouseEvent me) {
		appWindow.mouseX = me.getX();
		appWindow.mouseY = me.getY();
		appWindow.mousemsg = "Mouse down at " + appWindow.mouseX + ", " + appWindow.mouseY;
		appWindow.repaint();
	}	
}

class MyWindowAdapter extends WindowAdapter {
	public void windowClosing(WindowEvent we) {
		System.exit(0);
	}
}