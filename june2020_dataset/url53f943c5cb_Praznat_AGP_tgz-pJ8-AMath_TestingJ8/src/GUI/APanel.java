package GUI;

import java.awt.event.MouseEvent;

import AMath.Calc.MousePanel;

public class APanel extends MousePanel {
	
	protected GUImain parent;
	protected int tmpX, tmpY;
	
	public APanel(GUImain P) {
		super();
		parent = P;
	}
	
	protected void dragged(MouseEvent e) {
		parent.movePopup(this, e.getXOnScreen() - tmpX, e.getYOnScreen() - tmpY);
	}

	protected void pressed(MouseEvent e) {
		tmpX = e.getXOnScreen() - getX();   tmpY = e.getYOnScreen() - getY();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		dragged(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		pressed(e);
	}

}