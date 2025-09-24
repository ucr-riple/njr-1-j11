package GUI;

import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.*;

import AMath.Calc;
import Defs.F_;
import Game.AGPmain;
import Sentiens.*;

public class SexDisplay {
	static final int CSIZE = 200;
	static final int WIDTH = CSIZE * 2;
	static final int HEIGHT = CSIZE * 2;

	final JFrame MainPane;
	final JPanel parentPanel, childPanel;
	final Face father, mother, spawn;
	final Calc.MousePanel panel = new Calc.MousePanel() {
		@Override
		public void mouseClicked(MouseEvent arg0) {load();}
	};
	
	public SexDisplay() {
		MainPane = new JFrame("Family");
		parentPanel = new JPanel();
		childPanel = new JPanel();
		father = new Face();
		mother = new Face();
		spawn = new Face();

		MainPane.setContentPane(panel);
		MainPane.getContentPane().setLayout(new GridLayout(2, 1));
		parentPanel.setLayout(new GridLayout(1, 2));
		MainPane.getContentPane().add(parentPanel);
		parentPanel.add(father);
		parentPanel.add(mother);
		MainPane.getContentPane().add(childPanel);
		childPanel.add(spawn);
		father.setPreferredSize(new Dimension(CSIZE, CSIZE));
		mother.setPreferredSize(new Dimension(CSIZE, CSIZE));
		spawn.setPreferredSize(new Dimension(CSIZE, CSIZE));
		childPanel.setPreferredSize(new Dimension(CSIZE/2, CSIZE));
		MainPane.setSize(WIDTH,HEIGHT);
		MainPane.setVisible(true);
		
		load();
	}
	
	public void load() {
		final Clan father = AGPmain.mainGUI.AC.getAvatar();
		final Clan mother = AGPmain.mainGUI.GM.getClan();
		if (father == null || mother == null) return;
		load(father, mother);
	}
	
	public void load(Clan f, Clan m) {
		father.redefine(f);
		mother.redefine(m);
		final Ideology spawnFb = new Ideology();
		f.createHeir(spawnFb, m.FB.copyFs());
		spawn.redefine(spawnFb);
	}
}
