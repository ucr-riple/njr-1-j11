package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import AMath.Calc;
import Sentiens.Ideology;

public class FaceCrapper {
	static final int CSIZE = 200;
	static final int WIDTH = CSIZE * 6;
	static final int HEIGHT = CSIZE * 3;

	final JFrame MainPane;
	final CPanel parentPanel;
	final Map<Face, Point> faces = new HashMap<Face, Point>();
	final Calc.MousePanel panel = new Calc.MousePanel() {
		@Override
		public void mouseClicked(MouseEvent arg0) {load();}
	};
	
	public static void main(String[] args) {
		new FaceCrapper();
	}
	
	public FaceCrapper() {
		MainPane = new JFrame("GOBLINS");
		parentPanel = new CPanel();
		for (int i = 0; i < 220; i++) {
			Face goblin = new Face();
			goblin.loadAttributes(new Ideology());
			faces.put(goblin, new Point((int)(Math.random() * WIDTH),
					(int)(Math.random() * HEIGHT)));
//			goblin.setPreferredSize(new Dimension(CSIZE, CSIZE));
		}
		load();
		
		MainPane.setContentPane(panel);
		MainPane.getContentPane().setLayout(new GridLayout(1, 1));
//		parentPanel.setLayout(new GridLayout(1, 2));
		MainPane.getContentPane().add(parentPanel);
		MainPane.setSize(WIDTH,HEIGHT);
		MainPane.setVisible(true);
		
	}
	
	public void load() {
		for (Face goblin : faces.keySet()) {
			goblin.redefine();
		}
	}

	@SuppressWarnings("serial")
	private class CPanel extends JPanel {
		@Override
		public void paintComponent(Graphics gx) {
			super.paintComponent(gx);
			int b = 1;
			for (Map.Entry<Face, Point> entry : faces.entrySet()) {
				Face face = entry.getKey();
				Point point = entry.getValue();
				BufferedImage img = face.getFace();
				gx.drawImage(img,point.x,point.y,(int)(CSIZE*b),(int)(CSIZE*b),face);
			}
		}
	}
}
