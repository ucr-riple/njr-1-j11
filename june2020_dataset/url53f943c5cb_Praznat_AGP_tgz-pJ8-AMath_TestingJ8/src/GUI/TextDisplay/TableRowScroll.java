package GUI.TextDisplay;

import java.awt.*;

import AMath.Calc;
import GUI.*;
import Game.*;

public class TableRowScroll extends Papyrus {

	protected String[][] contents;
	protected int[] widths;
	protected int numCols;
	
	public TableRowScroll(PopupAbstract P) {
		super(P);
		numCols = 0;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		int x = 0;
		if (contents == null) {return;}
		g.drawLine(0, BHGT+2, wid, BHGT+2);
		for (int col = 0; col < numCols; col++) {
			if (!contents[0][col].equals("Name")) {
				for (int r = 0; r < contents.length; r++) {
					g.drawString(contents[r][col], x + 2, BHGT+BHGT*r);
				}
			}
			x += widths[col];
		}
	}

	protected static VarGetter var(int i) {return AGPmain.TheRealm.getPopVarGetter(i);}
	

	protected void setupTableLabels(int tab, int size, VarGetter[] vars) {
		contents = new String[size + 1][numCols];
		widths = new int[numCols];
		String S;   parent.getGraphics().setFont(NORMFONT);
		for (int col = 0; col < numCols; col++) {
			if (vars == null) {Calc.p("NO DAMN VARS!!!"); break;}
			S = vars[((PopupShire) parent).getVG(tab, col)].getVGName();
			contents[0][col] = S;
			adjustWidth(col, S);
		}
	}
	protected void setupTableLabels(int tab, int size, String[] labels) {
		contents = new String[size + 1][numCols];
		widths = new int[numCols];
		String S;   parent.getGraphics().setFont(NORMFONT);
		for (int col = 0; col < numCols; col++) {
			S = labels[col];
			contents[0][col] = S;
			adjustWidth(col, S);
		}
	}
	protected void adjustWidth(int col, String str) {
		widths[col] = Math.max(parent.getGraphics().getFontMetrics().stringWidth(str), widths[col]);
	}
	protected void calcSize() {wid = Calc.sum(widths);   hgt = (contents.length + 1) * BHGT;}
	
}
