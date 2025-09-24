package GUI;
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;

import AMath.Calc;
import GUI.TextDisplay.NameScroll;
import GUI.TextDisplay.Papyrus;
import Game.AGPmain;
import Sentiens.Clan;



public class PopupFace extends PopupAbstract {
	private Clan curClan;
	private FaceAnimated portrait;
	private ScrollSlidePanel[] infoboxes = new ScrollSlidePanel[6];
	public static final int BASIC = 0;
	public static final int VALUES = 1;
	public static final int PRESTS = 2;
	public static final int BEHS = 3;
	public static final int QUESTS = 4;
	public static final int ORDER = 5;
	

	
	public PopupFace(GUImain P) {
		super(P);

		INFO[BASIC] = "BASIC INFO";
		INFO[VALUES] = "VALUES";
		INFO[PRESTS] = "SKILLS & PRESTIGE";
		INFO[BEHS] = "BEHAVIORAL TENDENCIES";
		INFO[QUESTS] = "QUEST";
		INFO[ORDER] = "ORDER";
		
		portrait = new FaceAnimated();
		add(portrait);
		//cb = new JComboBox(new String[] {INFO1, INFO2, INFO3, INFO4, INFO5, INFO6}); 
		infoboxes[0] = new ScrollSlidePanel(this, Papyrus.basicS(this));
		infoboxes[1]  = new ScrollSlidePanel(this, Papyrus.sancS(this));
		infoboxes[2]  = new ScrollSlidePanel(this, Papyrus.prestS(this));
		infoboxes[3]  = new ScrollSlidePanel(this, Papyrus.behS(this));
		infoboxes[4]  = new ScrollSlidePanel(this, Papyrus.questS(this));
		infoboxes[5]  = new ScrollSlidePanel(this, Papyrus.orderS(this));
		for (int i = 0; i < 6; i++) {info.add(infoboxes[i], INFO[i]); slider.addCon(INFO[i]);}
	}
	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x,y,w,h);
		portrait.setBounds(0,namebox.getHeight(), 2*w/3, 2*w/3);
		h = portrait.getY()+portrait.getHeight();
		slider.setBounds(0,h, w, 25);
		h = slider.getY()+slider.getHeight();
		sp.setBounds(0, h, w, getHeight()-h);
		slider.refresh();
	}

	public Clan getClan() {return curClan;}

	@Override
    public void load() {if (curClan != null) {loadClan(curClan);}}
    public void loadClan(Clan c) {
    	curClan = c;
    	namebox.setNomen(curClan.getNomen());
    	portrait.redefine(curClan);
    	//portrait.redefine(curClan, 0.45, 0.5);
    	for (ScrollSlidePanel s : infoboxes) {s.redefineClan();}
    	initialized = true;
    }
	
    @Override
	protected void hideUnhideStuff() {
		super.hideUnhideStuff();
		portrait.setVisible(vizible);
		infoboxes[0].setVisible(vizible);
	}

    public void mouseClicked(MouseEvent e) {
    	if (hideUnhide(e.getY())) {return;}
    	if (vizible) {
    		AGPmain.TheRealm.goOnce();
    		loadClan(AGPmain.TheRealm.getRandClan());
    		AGPmain.mainGUI.SM.load();
    	}
    }
}
