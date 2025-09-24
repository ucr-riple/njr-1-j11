package GUI.TextDisplay;

import java.awt.Graphics;

import GUI.PopupAbstract;
import Ideology.Value;
import Sentiens.*;


public class SancScroll extends Papyrus {	
	Value[] sncs = new Value[Ideology.FSM[0].length];
	double[] pcts = new double[Ideology.FSM[0].length];
	int c;
	public SancScroll(PopupAbstract P) {
		super(P);
	}
	public void paint(Graphics g) {
		Clan clan = clan();
		if(clan==null){return;}
		super.paint(g);
		c = clan.FB.getSancPcts(sncs, pcts);
		for (int i = 0; i < c; i++) {
			g.drawString(pcts[i]+"%   " + sncs[i].description(clan), 2, 15+15*i);
		}
	}

}
