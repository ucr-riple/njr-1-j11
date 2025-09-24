package GUI.TextDisplay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import AMath.Calc;
import Defs.Misc;
import GUI.PopupAbstract;
import GUI.PopupShire;
import Game.*;
import Shirage.Shire;


public class MarketRowScroll extends TableRowScroll {
	
	
	public MarketRowScroll(PopupAbstract P) {
		super(P);
		numCols = ((PopupShire) parent).selectedVGLength(PopupShire.MARKETS);
	}

	
	public void calcRealizedSize() {
		int skip = 1;
		setupTableLabels(PopupShire.MARKETS, Misc.numGoods - 1 - skip, vars());
		String S;   Shire thisShire = ((PopupShire) parent).getShire();
		for (int r = 1; r < contents.length; r++) {
			String[] R = contents[r];
			for (int col = 0; col < R.length; col++) {
				int v = ((PopupShire) parent).getVG(PopupShire.MARKETS, col);
				S = var(v).getVarString(null, thisShire, r + skip) + " ";
				R[col] = S;
				adjustWidth(col, S);
			}
		}
		calcSize();
	}

	protected static VarGetter var(int i) {return AGPmain.TheRealm.getMktVarGetter(i);}
	protected static VarGetter[] vars() {return AGPmain.TheRealm.getMktVarGetters();}
	
}

