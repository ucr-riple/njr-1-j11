package War;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.JPanel;

import AMath.Calc;
import GUI.ImageReader;
import Sentiens.Clan;

public class BattleConsole extends JPanel {
	protected static final int ORIGWID = 800;
	protected static final int ORIGHGT = 600;
	protected static final int VIEWWID = 800;
	protected static final int VIEWHGT = 600;
	private static final double NUMHORZ = 100; //number dudes that should fit horizontally (no overlap)
	private static final double NUMVERT = 20; //number dudes that should fit vertically (no overlap)
	protected static final Color CANVASCOLOR = new Color(120, 210, 50);
	private final BufferedImage ARMORLESSTORSORIGHTDOWN;
	private final BufferedImage ARMORLESSTORSOLEFTDOWN;
	private final BufferedImage ARMORLESSTORSORIGHTUP;
	private final BufferedImage ARMORLESSTORSOLEFTUP;
	
	protected BufferedImage offscreen = new BufferedImage(ORIGWID,ORIGHGT, BufferedImage.TYPE_INT_ARGB);
	protected Graphics2D g = offscreen.createGraphics();
	protected boolean loaded = false;
	protected Rectangle view = new Rectangle(0, 0, VIEWWID, VIEWHGT);
	protected Random rseed;
	protected int turn;
	protected Set<Warrior> dudes;
	
	private BattleConsole() {
		setOpaque(false);
		setVisible(false);
		int wid = 10;
		int hgt = 30;
		ARMORLESSTORSORIGHTDOWN = new BufferedImage(wid, hgt, BufferedImage.TYPE_INT_ARGB);
		ARMORLESSTORSOLEFTDOWN = new BufferedImage(wid, hgt, BufferedImage.TYPE_INT_ARGB);
		ARMORLESSTORSORIGHTUP = new BufferedImage(wid, hgt, BufferedImage.TYPE_INT_ARGB);
		ARMORLESSTORSOLEFTUP = new BufferedImage(wid, hgt, BufferedImage.TYPE_INT_ARGB);
	}
	

	public static Set<Warrior> toDudes(Set<Clan> clans) {return null;}
	
	public void setupBattleConsole(long dateInGame, Set<Clan> defenseArmy, Set<Clan> offenseArmy) {
		rseed = new Random(dateInGame);   //so battles can be repeatable
		dudes.clear();
		dudes.addAll(toDudes(defenseArmy));

		setOpaque(true);
		setVisible(true);
	}
	
	
	public void paintComponent(Graphics gx) {
		if (!loaded) {return;}
		super.paintComponent(gx);
		gx.drawImage(offscreen,0,0,getWidth(),getHeight(),this);
	}
	protected void paintConsole() {
		paintBG(g);
		
		
		offscreen = offscreen.getSubimage(view.x, view.y, view.width, view.height);
	}
	protected void paintBG(Graphics g) {
		g.setColor(getCanvasColor());
		g.fillRect(0, 0, ORIGWID, ORIGHGT);
	}
	
	
	
	
	
	
	
	
	
	protected Color getCanvasColor() {return CANVASCOLOR;}
	protected double rand() {return rseed.nextDouble();}
	protected int rand(int i) {return rseed.nextInt(i);}
	
}

