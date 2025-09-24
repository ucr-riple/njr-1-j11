package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import AGraphics.Imagery;
import AMath.Calc;
import GUI.TextDisplay.Papyrus;
import Game.AGPmain;

public class AconSelector extends JPanel implements MouseListener {
	private Rectangle[] Acons = {};
	private String[] Alabels = {};
	private PopupAbstract parent;
	
	public AconSelector(PopupAbstract P) {
		super();
		parent = P;
		addMouseListener(this);
	}
	
	public void addCon(String S) {
		Rectangle[] tmpR = new Rectangle[Acons.length+1];
		for (int i = 0; i < Acons.length; i++) {tmpR[i] = Acons[i];}
		tmpR[tmpR.length-1] = new Rectangle();
		Acons = tmpR;
		String[] tmp = new String[Alabels.length+1];
		for (int i = 0; i < Alabels.length; i++) {tmp[i] = Alabels[i];}
		tmp[tmp.length-1] = S;
		Alabels = tmp;
		refresh();
	}
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < Acons.length; i++) {
			g2d.setColor(Papyrus.BGCOL);
			g2d.fill(Acons[i]);
			g.setColor(Color.black);
			drawIcon(g, (int)Acons[i].getX(), (int)Acons[i].getY(), (int)(Acons[i].getWidth()/2), Alabels[i]);
		}
	}
	public void refresh() {
		int h = getHeight();  int w = (getWidth() - Acons.length * h) / (Acons.length + 1);
		for (int i = 0; i < Acons.length; i++) {
			Acons[i].setBounds(w + (w+h)*i, 0, h, h);
		}
	}
	
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {
    	for (int i = 0; i < Acons.length; i++) {
    		if (Acons[i].contains(e.getX(), e.getY())) {
    			parent.setState(Alabels[i], i);   return;
    		}
    	}
    }
    private void drawIcon(Graphics g, int x, int y, int radius, String type) {
    	if (parent instanceof PopupFace) {
    		if (type == parent.INFO[0]) {Imagery.drawInfoSign(g, x, y, radius); return;}
    		if (type == parent.INFO[1]) {Imagery.drawEye(g, x, y, radius); return;}
    		if (type == parent.INFO[2]) {Imagery.drawStar(g, x, y, radius); return;}
    		if (type == parent.INFO[3]) {Imagery.drawMemSign(g, x, y, radius); return;}
    		if (type == parent.INFO[4]) {Imagery.drawFlag(g, x, y, radius); return;}
    		if (type == parent.INFO[5]) {Imagery.drawPyramid(g, x, y, radius); return;}
    	}
    	else {
    		if (type == parent.INFO[0]) {Imagery.drawCTreeFromTopRight(g, x, y, radius*2); return;}
    		if (type == parent.INFO[1]) {Imagery.drawStickFigure(g, x, y, radius); return;}
    		if (type == parent.INFO[3]) {Imagery.drawMilletSign(g, x, y, radius); return;}
    	}
    }
	public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}