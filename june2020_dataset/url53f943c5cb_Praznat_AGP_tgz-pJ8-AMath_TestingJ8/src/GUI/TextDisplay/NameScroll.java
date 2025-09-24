package GUI.TextDisplay;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import GUI.PopupAbstract;

public class NameScroll extends Papyrus {
	String name = "";   int sizo;
	public NameScroll(PopupAbstract P, int z) {super(P); sizo = z;}
	public NameScroll(PopupAbstract P, String S, int z) {
		super(P);
		sizo = z;
		setNomen(S);
	}
	public void setNomen(String s) {name = s; repaint();}
	public void paint(Graphics g) {
		super.paint(g);
		g.setFont(new Font("Palatino Linotype", Font.BOLD, sizo));
		g.drawString(name, 2, getHeight()-4);
	}

	public void calcRealizedSize() {
		wid = getWidth();
		hgt = BHGT;
	}
}