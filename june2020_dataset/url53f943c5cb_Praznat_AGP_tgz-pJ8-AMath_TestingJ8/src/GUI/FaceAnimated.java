package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Timer;

import Sentiens.Clan;

public class FaceAnimated extends Face implements ActionListener, MouseListener, MouseMotionListener {
	private static final int SPEED = 130;
	private Timer timer = new Timer(SPEED, this);
	private int framesLeft = 0;
	public FaceAnimated() {super(); addMouseListener(this); addMouseMotionListener(this);}
	
	public void animate(int frames) {
		framesLeft = frames;
		timer.start();
	}
	@Override
	public void redefine(Clan gob) {super.redefine(gob); framesLeft = 0; timer.stop();};
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (framesLeft <= 0) {timer.stop();}
		redefine();
		framesLeft--;
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {}
	@Override
	public void mouseMoved(MouseEvent arg0) {}
	@Override
	public void mouseClicked(MouseEvent e) {animate(20);}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
}
