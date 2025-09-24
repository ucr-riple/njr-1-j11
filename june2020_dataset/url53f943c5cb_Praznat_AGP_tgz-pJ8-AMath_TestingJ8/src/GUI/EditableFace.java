package GUI;

import java.awt.event.*;
import java.awt.image.Raster;

public class EditableFace extends Face implements MouseListener, MouseMotionListener {
	private boolean dragging;
	private int selectedparte;
	
	public EditableFace() {
		super();
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	private void chooseParte() {
		double mindist = 100000; int k = -1;
		for (int i = 0; i < partez.length; i++) {
			double[] stuff = partez[i].nearestPoint(msplc);
			if(stuff[1] < mindist) {mindist = stuff[1]; k = i;}
		}
		selectedparte = k;
	}

	public void setMsplc(MouseEvent e) {
		if (offscreen == null) {return;}
		final Raster r = offscreen.getRaster();
		msplc[0] = e.getX()*r.getWidth()/this.getWidth() + x;
		msplc[1] = e.getY()*r.getHeight()/this.getHeight() + y;
		System.out.println(msplc[0] +", "+ msplc[1]);
	}
	
	public void mouseMoved(MouseEvent e) {
		if(!dragging){
			setMsplc(e);
			paintFace();
		}
	}

    public void mouseDragged(MouseEvent e) {
    	if (dragging) {
    		setMsplc(e);
	    	partez[selectedparte].dragged();
	    	paintFace();
    	}
    }
    
    public void mouseExited(MouseEvent e) {
    	//redefine();
		//msplc[0] = 0; msplc[1] = 0;
		//repaint();
    }
    
    public void mousePressed(MouseEvent e) {
    	if (!dragging) {
	    	oldmsplc[0] = msplc[0];   oldmsplc[1] = msplc[1];
	    	chooseParte();
	    	partez[selectedparte].clicked();
    	}
    	dragging = true;
    	partez[NASO].megafix();
    	setMsplc(e);
    	paintFace();
    }

	public void mouseReleased(MouseEvent e) {
		dragging = false;
		for (Parte part : partez) part.temp = null;
		paintFace();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }
}
