package GUI;
import java.awt.*;

import AMath.Calc;
import Game.*;

import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import Shirage.*;
import javax.swing.*;

public class MapDisplay extends JPanel implements MouseListener, MouseMotionListener {

	private int TotRows, TotCols;
	private final int N = 4000;
	private final int Ns = 2000;
	private final int WSize = 18;
	private final int HSize = 13;
	private final double R2C = 1;
	private Plot[] plots;
	private Plot ExPlot;
	private double wamt = .3;
	private double samt = 0;
	private final double wfp = 1; //rainfall rate
	private final double wtr = 0.2; //evaporation rate
	private final double str = 0.1; //erosion rate
	private int[][] PlotOrder;
	private BufferedImage offscreen, highlightOffscreen;
	public Shire highlightedShire;
	public String highlightedStat = "";

	private static String pdes = "";
	private int maxW, maxH, tmpX = 0, tmpY = 0;
	
	public final boolean SIMPLEDRAW = false;
	private double maxHighlight = 0;
	
	public MapDisplay() {
		ExPlot = new XPlot(0);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paint(g);
	}
	public void createMap(int T, int r) {
		r = (SIMPLEDRAW ? 1 : r);
		pdes = "CREATING MAP...";
		for (int i = 0; i<T; i++) {
			updateW();
			if(i % r == 0) {if (SIMPLEDRAW) paintBGCanvas(); repaint();}
		} PlotOrder = orderPlotsByVal(); paintBGCanvas();  repaint(); pdes = "";
	}
	private void paintBGCanvas() {
		Graphics2D g = offscreen.createGraphics();
		g.setColor(new Color(150,120,110));
		g.fillRect(0, 0, offscreen.getWidth(), offscreen.getHeight());
		
		for (int r = 0; r < TotRows; r++) {
    		for (int i = TotCols-1; i >= 0; i--) {
    			int c = PlotOrder[r][i];
    			getPlotXY(c, r).drawPlot(g);
    		}
		}
		for (int r = 0; r < TotRows; r++) {
    		for (int i = TotCols-1; i >= 0; i--) {
    			int c = PlotOrder[r][i];
    			getPlotXY(c, r).drawPlot(g);
    		}
		}
	}
	public void grayEverything() {
		Plot.grayscaleColors();
		for (Plot p : plots) {p.setGradients();}
		paintBGCanvas();
		repaint();
	}
	
	public void paint(Graphics gx) {
		Graphics2D g = highlightOffscreen.createGraphics();
		if (highlightedShire != null) {
//			topLayer = new BufferedImage(TotW(),TotH(), BufferedImage.TYPE_INT_ARGB);
			g.drawImage(offscreen, 0, 0, this);
			highlightedShire.getLinkedPlot().drawShireHighlighted(g, Color.red);
			//if (prevHighlightedShire != highlightedShire) {prevHighlightedShire.getLinkedPlot().unDrawShireHighlighted(g);}
		}
		if (AGPmain.mainGUI.SM != null) {
			if (AGPmain.mainGUI.SM.getShire() == null) return;
			AGPmain.mainGUI.SM.getShire().getLinkedPlot().drawShireHighlighted(g, Color.white);
		}
		if (!highlightedStat.isEmpty()) {
			for (Shire s : AGPmain.TheRealm.getShires()) {
				s.getLinkedPlot().drawShireSpotHighlight(g, maxHighlight);
			}
		}

		gx.drawImage(offscreen.getSubimage(tmpX,tmpY,getWidth(),getHeight()),  0,0,getWidth(),getHeight(),this);
		gx.drawImage(highlightOffscreen.getSubimage(tmpX,tmpY,getWidth(),getHeight()),  0,0,getWidth(),getHeight(),this);
		
		gx.setColor(Color.red);
		gx.drawString(pdes, 30, 30);
	}
	
	public void validate() {
		super.validate();
		repaint();
	}
	
	public void introduce() {
		validate();
		plots = new Plot[N+1];
		TotRows = (int) (Math.sqrt((double) N * R2C) + 0.9999);
		TotCols = N / TotRows;
		
		for (int i = 0; i < N; i++) {
			plots[i] = new Plot(0); //Math.random() < 0.2 ? 1 : 0);
			plots[i].setXY(i%TotCols, i/TotCols);
		}
		plots[N] = ExPlot;


		Plot[] landplots = new Plot[Ns];
		int k = 1;   int[] order = Calc.normalOrder(Ns);
		landplots[0] = getPlotXY(TotCols/2, TotRows/2);
		Plot nextP;   int[] ord6 = Calc.normalOrder(6);
		while (k < Ns) {
			Calc.shuffle(order, k);
			for(int i = 0; i < k; i++) {
				nextP = spawnPlot(landplots[order[i]], ord6);
				if (!nextP.isNull()) {
					landplots[k++] = nextP; break;
				}
			}
		}
		for (int i = 0; i < N; i++) {if (plots[i].isOcean()) {plots[i] = ExPlot;}}
		
		pickRandomMountains(0.3, 1.4);
		doAltitudeSmoothing();
		wamt = wamt * N;
		PlotOrder = orderPlotsByVal();
		maxW = TotW();
		maxH = TotH();
		offscreen = new BufferedImage(TotW(),TotH(), BufferedImage.TYPE_INT_ARGB);
		highlightOffscreen = new BufferedImage(TotW(),TotH(), BufferedImage.TYPE_INT_ARGB);
	}

	public int W() {return WSize;}
	public int H() {return HSize;}
	public int getTRows() {return TotRows;}
	public int getTCols() {return TotCols;}
	public int TotW() {return WSize*TotCols;}
	public int TotH() {return HSize*TotRows;}
	private int hsX() {return this.getWidth() / 2;}
	private int hsY() {return this.getHeight() / 2;}
	
	
	private Plot spawnPlot(Plot P, int[] ord){
		if(AGPmain.rand.nextDouble() < 0.0001) {Calc.shuffle(ord);}
		P.refreshHood();
		Plot N;
		for (int i = P.myHood().length - 1; i >= 0; i--) {
			N = P.myHood()[ord[i]];
			if(N.isOcean()) {
				N.makeLand(); return N;
			}
		}
		return ExPlot;
	}
	
	public Plot getPlotXY(int x, int y) {
		if(x<0 || y<0 || x>=TotCols || y>TotRows || (y*TotCols + x >= plots.length)) {return ExPlot;}
		else {return plots[y*TotCols + x];}
	}
	private void pickRandomMountains(double pct, double amt) {
		int times = (int) (pct * (double)plots.length);
		for (int i = 0; i < times; i++) {
			plots[AGPmain.rand.nextInt(plots.length)].chgValue(amt);
			int j = 0;
			while (AGPmain.rand.nextDouble() < pct && j < 5) {plots[AGPmain.rand.nextInt(plots.length)].chgValue(amt); j++;}
		}
	}
	private void doAltitudeSmoothing() {
		smoothPlots();//smoothPlots();//smoothPlots();smoothPlots();smoothPlots();smoothPlots();
	}
	private void smoothPlots() {
		double[] tmp = new double[plots.length];
		for(int i = 0; i < tmp.length; i++) {tmp[i] = plots[i].getHoodAvgVal();}
		for(int i = 0; i < tmp.length; i++) {plots[i].setValue(tmp[i]);}
	}
	private void WRise() {
		for (int i = 0; i < plots.length; i++) {
			wamt += plots[i].evaporate(wtr);
		}
	}
	private void WFall() {
		double wpp = wamt / plots.length;
		for (int i = 0; i < plots.length; i++) {
			if(AGPmain.rand.nextDouble() < wfp) {
				plots[i].chgW(wpp);
				wamt -= wpp;
			}
		}
	}
	private void SCycle() {
		double er;
		for (int i = 0; i < N; i++) {
			if(!plots[i].isNull()) {
				er = Math.min(plots[i].getWFlow() * str, plots[i].getValue() - plots[i].getRBM());
				plots[i].chgValue(-er);
				samt += er;
			}
		}
		double spp = samt / Ns;
		for (int i = 0; i < N; i++) {
			if(!plots[i].isNull()) {
				plots[i].chgValue(spp);
				samt -= spp;
			}
		}
	}
	
	private void updateW() {
		WFall();
		for(int i = 0; i < plots.length; i++) {plots[i].flow();}
		for(int i = 0; i < plots.length; i++) {plots[i].emptyHyp();}
		SCycle();
		WRise();
	}

//	private double totalW() {
//		double sum = 0;
//		for(int i = 0; i < plots.length; i++) {
//			sum += plots[i].getWVal();
//		}
//		return sum + wamt;
//	}
//	
	
	
	private int[][] orderPlotsByVal() {
    	int[][] Order = new int[TotRows][TotCols];
    	double[] vals = new double[TotCols];
    	for (int r = 0; r < TotRows; r++) {
    		for (int c = 0; c < TotCols; c++) {
    			vals[c] = getPlotXY(c, r).getValue();
    		}   Order[r] = order(vals);
    	}   return Order;
    }
    
	private int[] order(double[] vals) {
    	//returns order from lowest to highest
    	int[] O = new int[vals.length];
    	for(int i = 0; i < O.length; i++) {O[i] = i;}
		boolean switched = true;   double tmp;
		while (switched) {   switched = false;
			for (int i = 0; i < vals.length-1; i++) {
				if (vals[i] < vals[i+1]) {
					tmp = vals[i+1]; vals[i+1] = vals[i]; vals[i] = tmp;
					tmp = O[i+1]; O[i+1] = O[i]; O[i] = (int)tmp;
					switched = true;
		} } }
		return O;
    }

	public int getTmpX() {return 0;} //return tmpX;}
	public int getTmpY() {return 0;} //return tmpY;}
	
	public void setHighlightStat(String stat) {
		highlightedStat = stat;
		maxHighlight = 0;
		for (Shire s : AGPmain.TheRealm.getShires()) {
			double d = s.getGraphingInfo(highlightedStat).getValue();
			maxHighlight = Math.max(maxHighlight, d);
			s.getLinkedPlot().saveShireStat(d);
		}
	}
	
    public void mousePressed(MouseEvent e) {
    	tmpX = Math.min(Math.max(tmpX + e.getX() - hsX(), 0), maxW - getWidth());
    	tmpY = Math.min(Math.max(tmpY + e.getY() - hsY(), 0), maxH - getHeight());
    	for (int i = 0; i < plots.length; i++) {plots[i].setGradients();}
    	repaint();
    }
	public void mouseReleased(MouseEvent e) {AGPmain.mainGUI.SM.loadShire(highlightedShire);}
    public void mouseEntered(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {
    	Plot hoveredPlot = getPlotXY(TotCols * (tmpX + e.getX()) / offscreen.getWidth(), TotRows * (tmpY + e.getY()) / offscreen.getHeight());
    	highlightedShire = hoveredPlot.getLinkedShire();
    	repaint();
    }
    public void mouseDragged(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    
}




