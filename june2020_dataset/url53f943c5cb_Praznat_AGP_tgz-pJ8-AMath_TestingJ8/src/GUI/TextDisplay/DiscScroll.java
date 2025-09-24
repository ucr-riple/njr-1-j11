package GUI.TextDisplay;

import java.awt.Graphics;

import AMath.Calc;
import Defs.*;
import Descriptions.*;
import GUI.PopupAbstract;
import Sentiens.Clan;
import Shirage.Shire;

public class DiscScroll extends Papyrus {
	
	public DiscScroll(PopupAbstract P) {
		super(P);
	}
	public void paint(Graphics g) {
		if(clan()==null){return;}
		Clan clan = clan();
		super.paint(g);
		int r = 0;
		write(g, clan.getNomen(), r++); //convert all g.drawStrings to write
		g.drawString(" the " + clan.getJob().getDesc(clan), 2, 15+15*r++);
		addToClickStrings(clan.myShire(), 20, 15+15*r, g);
		g.drawString(" of ", 2, 15+15*r++);
		Shire govShire = clan.getGovernedShire();
		if (govShire != null) {g.drawString(" governor of " + govShire.getName(), 2, 15+15*r++);}
		if(clan.getBoss() != clan) {
			g.drawString(" follower of " + clan.getBoss().getNomen(), 2, 15+15*r++);
		}
		final Clan suitor = clan.getSuitor();
		if (suitor != null) g.drawString(" lover of " + suitor.getNomen(), 2, 15+15*r++);
		if (GlobalParameters.SHOW_CREED) g.drawString(" believer in the " + clan.FB.getDeusName(), 2, 15+15*r++);

		g.drawString("Age: " + clan.getAge(), 2, 15+15*r++);
		g.drawString("Progeny: " + clan.getNumOffspring(), 2, 15+15*r++);
		g.drawString("Wisdom: " + clan.getKnowledgeAttribution(), 2, 15+15*r++);
		g.drawString("Holiness: " + clan.getHoliness(), 2, 15+15*r++);
		g.drawString("Grandeur: " + clan.getSplendor(), 2, 15+15*r++);
		r++;r++;
		write(g, "Avg Profit:" + Calc.roundy(clan.getAvgIncome()), r++);
		write(g, "NAV:" + clan.getNetAssetValue(null), r++);
		write(g, "ASSETS:", r++);
		for (int i = 0; i < Misc.numAssets; i++) {
			int n = clan.getAssets(i);
			if (n != 0) {write(g, n + " " + Naming.goodName(i, (n != 1), false), r++);}
		}
		if (GlobalParameters.SHOW_WEAPONS) {
			r++;   write(g, "WEAPON:", r++);
			write(g, XWeapon.weaponName(clan.getXWeapon()), r++);
		}
		//permanent possessions:

		paintComponents(g);
	}
	private void write(Graphics g, String S, int r) {
		g.drawString(S, 2, BHGT+BHGT*r++);
	}
	
}