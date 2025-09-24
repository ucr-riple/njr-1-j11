package GUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import AMath.Calc;
import GUI.TextDisplay.NameScroll;
import GUI.TextDisplay.Papyrus;

public abstract class PopupAbstract extends APanel {

	private static final int TOPBOXHGT = 22;

	
	protected NameScroll namebox;
	protected int curTab;
	protected AconSelector slider;  //the five icons
	protected JPanel info;  //holds the switchable cards
	protected JScrollPane sp;  //the scrolling thing in info
//	protected ASlidePanel content;
	protected boolean initialized;
	protected boolean vizible;
	public String INFO[] = new String[6];


	public PopupAbstract(GUImain P) {
		super(P);
		vizible = true;
		setOpaque(false);
		info = new JPanel(new CardLayout());
		slider = new AconSelector(this);
		add(slider);
		sp = new JScrollPane(info, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(sp);
		setBorder(BorderFactory.createLineBorder(Papyrus.BGCOL));
		setLayout(null);
	}

	@Override
	protected void addMouseListeners() {
		namebox = new NameScroll(this, 12);
		add(namebox);
		namebox.addMouseMotionListener(this);
		namebox.addMouseListener(this);
	}


	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x,y,w,h);
		namebox.setBounds(0,0,w,TOPBOXHGT);
	}

	public void scrollToPoint(Point P) {
		ASlidePanel ASP = (ASlidePanel)info.getComponent(curTab);
		P.setLocation(Math.max(Math.min(P.getX(),ASP.maxWidth() - sp.getViewport().getWidth()),0),
				Math.max(Math.min(P.getY(),ASP.maxHeight() - sp.getViewport().getHeight()),0));
		sp.getViewport().setViewPosition(P);
	}

	public Dimension getViewportSize() {return sp.getViewport().getExtentSize();} //getViewSize() ??

	public void refreshAll() {
		info.validate();
		scrollToPoint(new Point(0,0));
		repaint();
	} 
	public void setState(String S, int i) {
		curTab = i;
		CardLayout cl = (CardLayout)(info.getLayout());
		cl.show(info, S);
		refreshAll();
	} 
	public boolean initialized() {return initialized;}
	
	protected boolean hideUnhide(int y) {
		if (y < TOPBOXHGT) {
			setState(INFO[0], 0);
			vizible = !vizible;
			hideUnhideStuff();
			return true;
		}   return false;
	}
	protected void hideUnhideStuff() {
		sp.setVisible(vizible);
		slider.setVisible(vizible);
		setBorder(vizible ? BorderFactory.createLineBorder(Papyrus.BGCOL) : null);
	}

	@Override
	protected void dragged(MouseEvent e) {
		if(vizible || e.getY() < TOPBOXHGT) {parent.movePopup(this, e.getXOnScreen() - tmpX, e.getYOnScreen() - tmpY);}
	}

	@Override
	protected void pressed(MouseEvent e) {
		if(vizible || e.getY() < TOPBOXHGT) {tmpX = e.getXOnScreen() - getX();   tmpY = e.getYOnScreen() - getY();}
	}

	public abstract void load();
	public void setState() {
		int prevTab = curTab;
		int i = (prevTab+1) % info.getComponentCount();
		setState(INFO[i], i);
		load();
		i = (i+1) % info.getComponentCount();
		setState(INFO[i], i);
    	setState(INFO[prevTab], prevTab);
	}
	

}
