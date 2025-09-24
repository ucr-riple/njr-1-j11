package GUI;
import javax.swing.*;

import AMath.Calc;
import Avatar.AvatarConsole;
import Sentiens.Clan;

public class GUImain extends JFrame {
	private static final int WWID = 1000;
	private static final int WHGT = 800;
	private static final int BUFF = 20;
	private JPanel MainPanel;
	public MapDisplay MD;
	public PopupFace GM;
	public PopupShire SM;
	public AvatarConsole AC;
	public FaceEditor Editor;
	public SexDisplay sexDisplay;
	private static final double MW = 0.25; // % of screen for ScrollPanels
	private static final double MH = 0.85; // % of screen for ScrollPanels
	public GUImain(String title) {
		super(title);
		MainPanel = new JPanel();
		this.getLayeredPane().add(MainPanel, new Integer(0));
	    this.setSize(WWID, WHGT);
	    this.setVisible(true);
	}

	public void repaintEverything() {
		GM.load();   SM.load();
		//validate();
	}
	
	public void initializeMD() {
		MD = new MapDisplay();   MD.introduce();
		this.getLayeredPane().add(MD, new Integer(0));
		MD.setBounds(0, 0, getContentPane().getWidth(), getContentPane().getHeight());
		MD.createMap(100, 1000); //5000
		MD.setVisible(true);
	}

	public void initializeGM() {
		GM = new PopupFace(this);
		this.getLayeredPane().add(GM, new Integer(2));
		int MWID = getContentPane().getWidth();
		int MHGT = getContentPane().getHeight();
		GM.setBounds((int)((1-MW)*MWID/7), (int)((1-MH)*MHGT/2), (int)(MW*MWID),  (int)(MH*MHGT));
		GM.setVisible(true);
	}
	public void initializeSM() {
		SM = new PopupShire(this);
		this.getLayeredPane().add(SM, new Integer(1));
		int MWID = getContentPane().getWidth();
		int MHGT = getContentPane().getHeight();
		SM.setBounds((int)((1-MW)*MWID*6/7), (int)((1-MH)*MHGT/2), (int)(MW*MWID),  (int)(MH*MHGT));
		SM.setVisible(true);
	}
	public void initializeAC(Clan avatar) {
		AC = AvatarConsole.create(this);
		AC.setAvatar(avatar);
		this.getLayeredPane().add(AC, new Integer(3));
		AC.setBounds(390,50,AC.getDesWid(),AC.getDesHgt());
		AC.setVisible(true);
	}
	public void initializeFaceEditor() {
		Editor = new FaceEditor();
	}
	public void initializeSexDisplay() {
		sexDisplay = new SexDisplay();
	}
	public void movePopup(APanel P, int x, int y) {
		P.setLocation(Calc.bound(x, 0, (int)(getContentPane().getWidth() - BUFF)), //*(1-MW))),
					  Calc.bound(y, 0, (int)(getContentPane().getHeight() - BUFF))); //*(1-MH))));
	}
}
