package GUI.TextDisplay;

import java.awt.Graphics;

import GUI.*;
import Game.Library;
import Questing.Knowledge.KnowledgeBlock;
import Sentiens.Clan;
import Shirage.Shire;


public class ShireDescScroll extends Papyrus {	
	public ShireDescScroll(PopupAbstract P) {
		super(P);
	}
	public void paint(Graphics g) {
		final Shire shire = ((PopupShire)parent).getShire();
		if(shire==null){return;}
		final Library library = shire.getLibrary();
		super.paint(g);
		int r = 0;

		Clan gov = shire.getGovernor();
		if (gov != null) {
			g.drawString("Governor: " + gov, 2, 15+15*r++);
			g.drawString("of : " + gov.myOrder(), 2, 15+15*r++);
			r++;
		}
		g.drawString("Knowledge Library", 2, 15+15*r++);
		for (int i = 0; i < library.getCapacity(); i++) {
			@SuppressWarnings("rawtypes")
			final KnowledgeBlock kb = library.getKnowledge(i);
			if (kb == null) {
				if (i == 0) g.drawString("(empty)", 2, 15+15*r++);
				break;
			}
			g.drawString(kb.toString(), 2, 15+15*r++);
		}
	}

}
