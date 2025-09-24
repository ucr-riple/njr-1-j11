package GUI.TextDisplay;

import java.awt.Graphics;
import java.util.ArrayList;

import GUI.*;
import Game.*;
import Sentiens.Clan;


public class ClanRowScroll extends TableRowScroll {
	
	private ArrayList<Clan> pop;
	
	public ClanRowScroll(PopupAbstract P) {
		super(P);
		numCols = ((PopupShire) parent).selectedVGLength(PopupShire.POPULATION);
	}
	

	public void paint(Graphics g) {
		super.paint(g);
		int x = 0;
		for (int col = 0; col < numCols; col++) {
			if (contents[0][col].equals("Name")) {
				g.drawString("Name", x + 2, BHGT+BHGT*0);
				for (int r = 1; r < contents.length; r++) {
					if (r-1 >= pop.size()) 
						System.out.println("illegal clanrowscroll stuff");
					else addToClickStrings(pop.get(r-1), x + 2, BHGT+BHGT*r, g);
				}
			}
			x += widths[col];
		}
		if (g != null) {paintComponents(g);}
	}

	
	public void calcRealizedSize() {
		pop = (ArrayList<Clan>)((PopupShire)parent).getShire().getCensus();
		if (pop == null) {System.out.println("NO DAMN POP!");}
		if (vars() == null) {System.out.println("NO DAMN VARS!");}
		setupTableLabels(PopupShire.POPULATION, pop.size(), vars());
		String S;
		for (int r = 1; r < contents.length; r++) {
			String[] R = contents[r];
			Clan clan = pop.get(r-1);
			for (int col = 0; col < R.length; col++) {
				int v = ((PopupShire) parent).getVG(PopupShire.POPULATION, col);
				S = var(v).getVarString(clan) + " ";
				R[col] = S;
				adjustWidth(col, S);
			}
		}
		calcSize();
	}

	protected static VarGetter var(int i) {return AGPmain.TheRealm.getPopVarGetter(i);}
	protected static VarGetter[] vars() {return AGPmain.TheRealm.getPopVarGetters();}
	
}

