package GUI.TextDisplay;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import AMath.Calc;

public class ClickableString extends JComponent implements MouseListener, MouseMotionListener {
	protected String content;
	protected Papyrus parent;
	public ClickableString(String S, Papyrus P) {
		content = S;   parent = P;
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	public void paint(Graphics g) {
		int h = g.getFontMetrics(Papyrus.NORMFONT).getHeight();
		Dimension D = new Dimension (g.getFontMetrics(Papyrus.NORMFONT).stringWidth(content), (int) ((double) h * 1.2));
		this.setSize(D);
		g.setColor(Color.red);
		g.setFont(Papyrus.NORMFONT);
		g.drawString(content, 0, h);
	}
    public void mousePressed(MouseEvent e) {
    	
    }
	public void mouseMoved(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}
