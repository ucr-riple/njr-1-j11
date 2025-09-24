package GUI;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import GUI.TextDisplay.Papyrus;

public class ASlidePanel extends JPanel implements MouseListener, MouseMotionListener {
	protected Point VPos;
	protected int tmpX, tmpY, maxH, maxW;
	protected PopupAbstract parent;
	protected Papyrus[] scroll;
	
	public ASlidePanel(PopupAbstract P) {
		super();
		parent = P;
		VPos = new Point(0,0);
		this.setBackground(Papyrus.BGCOL);
		setLayout(null);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	public void setScrolls() {
		this.removeAll();
		for(int i = 0; i < scroll.length; i++) {
			add(scroll[i]);
		}
		maxW = 0;   maxH = 0;
		for(int i = 0; i < scroll.length; i++) {
			Papyrus curScroll = scroll[i];
			curScroll.calcRealizedSize();
			int w = curScroll.getRealizedWidth();   int h = curScroll.getRealizedHeight();
			curScroll.setBounds(0, maxH, w, h);
			curScroll.resetCS();
			maxW = Math.max(w, maxW);
			maxH += h;
		}
		this.setPreferredSize(new Dimension(maxW, maxH)); //good!
		parent().refreshAll();
	}
	
	public int maxWidth() {return maxW;}
	public int maxHeight() {return maxH;}

	protected PopupAbstract parent() {return (PopupAbstract)parent;}
	
	public void mouseMoved(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {
    	VPos.setLocation(tmpX - e.getXOnScreen(), tmpY - e.getYOnScreen());
    	parent.scrollToPoint(VPos);
    	repaint();
    }
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {
    	tmpX = (int)VPos.getX() + e.getXOnScreen();
    	tmpY = (int)VPos.getY() + e.getYOnScreen();
    }
	public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}