package GUI.TextDisplay;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.JPanel;

import AMath.Calc;
import Defs.*;
import Descriptions.Naming;
import GUI.PopupAbstract;
import GUI.PopupFace;
import GUI.PopupShire;
import Game.AGPmain;
import Sentiens.Clan;
import Sentiens.Ideology;
import Shirage.Shire;


public class Papyrus extends JPanel {
	protected int reference, wid, hgt, numCS;
	//protected Clan clan;
	protected PopupAbstract parent;
	protected ClickableString[] clickstrings = {};
	public static final int BHGT = 15;
	public static final Color FGCOL = new Color(0,0,0);
	public static final Color BGCOL = new Color(239,225,180);
	public static final Font NORMFONT = new Font("Palatino Linotype", Font.BOLD, 10);

	public Papyrus(PopupAbstract P) {parent = P;   setLayout(null);}

	public static Papyrus[] basicS(PopupAbstract P) {
		Papyrus[] s = new Papyrus[2];
		s[0] = new NameScroll(P, P.INFO[0], 12);
		s[1] = new DiscScroll(P);
		return s;
	}
	public static Papyrus[] sancS(PopupAbstract P) {
		Papyrus[] s = new Papyrus[2];
		s[0] = new NameScroll(P, P.INFO[1], 12);
		s[1] = new SancScroll(P);
		return s;
	}
	public static Papyrus[] prestS(PopupAbstract P) {
		Papyrus[] s = new Papyrus[1 + P_.length()-1];
		s[0] = new NameScroll(P, P.INFO[2], 12);
		for(int i = 1; i < s.length; i++) {s[i] = new PrestScroll(P, i);}
		return s;
	}
	public static Papyrus[] behS(PopupAbstract P) {
		Papyrus[] s = new Papyrus[2];  //slow?
		s[0] = new NameScroll(P, P.INFO[3], 12);
		s[1] = new MemScroll(P);
		return s;
	}
	public static Papyrus[] questS(PopupAbstract P) {
		Papyrus[] s = new Papyrus[2];
		s[0] = new NameScroll(P,  P.INFO[4], 12);
		s[1] = new QuestScroll(P);
		return s;
	}
	public static Papyrus[] orderS(PopupAbstract P) {
		Papyrus[] s = new Papyrus[2];
		s[0] = new NameScroll(P,  P.INFO[5], 12);
		s[1] = new OrderRowScroll(P);
		((OrderRowScroll)s[1]).calcRealizedSize();
		return s;
	}
	public static Papyrus[] shiredescS(PopupAbstract P) {
		Papyrus[] s = new Papyrus[2];
		s[0] = new NameScroll(P,  P.INFO[0], 12);
		s[1] = new ShireDescScroll(P);
		return s;
	}
	public static Papyrus[] shirepopS(PopupAbstract P) {
		Papyrus[] s = new Papyrus[3];
		s[0] = new NameScroll(P,  P.INFO[1], 12);
		s[1] = new NameScroll(P, "category selection...", 12);
		s[2] = new ClanRowScroll(P);
		((ClanRowScroll)s[2]).calcRealizedSize();
		return s;
	}
	public static Papyrus[] shiremktS(PopupAbstract P) {
		Papyrus[] s = new Papyrus[3];
		s[0] = new NameScroll(P, P.INFO[3], 12);
		s[1] = new NameScroll(P, "category selection...", 12);
		s[2] = new MarketRowScroll(P);
		((MarketRowScroll)s[2]).calcRealizedSize();
		return s;
	}

	
	public void paint(Graphics g) {
		g.setFont(NORMFONT);
		int w = getWidth();
		int h = getHeight();
		g.setColor(BGCOL);
		g.fillRect(0, 0, w, h);
		g.setColor(FGCOL);
		wid = w;   hgt = h;
		numCS = 0;
	}
	
	protected Clan clan() {return (parent.initialized() ? ((PopupFace)parent).getClan() : null);}
	protected Shire shire() {return (parent.initialized() ? ((PopupShire)parent).getShire() : null);}
	
	public void setRef(int x) {reference = x;}
	
	public void calcRealizedSize() {
		wid = getWidth();
		hgt = parent.getViewportSize().height;
	}

	public int getRealizedWidth() {return Math.max(wid, parent.getViewportSize().width);}
	public int getRealizedHeight() {return Math.max(hgt, 0);}//parent.getViewportSize().height);}

	protected void refreshWid(Graphics g, String S) {
		wid = Math.max(g.getFontMetrics(NORMFONT).stringWidth(S), wid);
	}
	

	public void resetCS() {clickstrings = new ClickableString[] {};   this.removeAll();}
	protected boolean haveCSAlready(int X, int Y) {
		if (numCS > clickstrings.length) {return false;}   return true;
	}
	protected boolean haveCSAlreadyOLD(int X, int Y) {
		for(int i = clickstrings.length-1; i >= 0; i--) {
			System.out.print(clickstrings[i].getY()+" ");
			if(clickstrings[i].getX() == X && clickstrings[i].getY() == Y) {
				return true;
			}
		}
		return false;
	}
	private void addToClickStrings(ClickableString CS, int X, int Y, Graphics g) {
		//Calc.p(numCS + " x" + X + " y" + Y);
		ClickableString[] tmp = new ClickableString[clickstrings.length + 1];
		System.arraycopy(clickstrings, 0, tmp, 0, clickstrings.length);
		tmp[tmp.length - 1] = CS;
		clickstrings = tmp;
		add(CS);   CS.setBounds(X, Y - g.getFontMetrics(Papyrus.NORMFONT).getHeight(), 3, 3);
	}
	protected void addToClickStrings(Clan C, int X, int Y, Graphics g) {
		numCS++;   //contain in IF to avoid unnecessary object creation
		if (numCS > clickstrings.length) {
			addToClickStrings(new ClanString(C, this), X, Y, g);
		}
	}
	protected void addToClickStrings(Shire S, int X, int Y, Graphics g) {
		numCS++;   //contain in IF to avoid unnecessary object creation
		if (numCS > clickstrings.length) {
			addToClickStrings(new ShireString(S, this), X, Y, g);
		}
	}
	
	
	
	
	protected void writeP(Graphics g, String S, int x, int y) {g.drawString(S,x,y);}
	
}



