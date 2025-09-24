package Shirage;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;

import AGraphics.Bezier;
import AGraphics.Imagery;
import AMath.Calc;
import GUI.MapDisplay;
import Game.AGPmain;

public class Plot extends AbstractPlot {
	protected static final Plot[] HOOD = new Plot[6];
	protected static final int CWGT = 3;
	protected static final int BORDER = 5;
	protected static final double rbm = 0.25;
	protected static final double waterthresh = 0.3;
	protected static final int adjFert = 2;
	protected static Color dColORIGINAL = new Color(100, 65, 40);
	protected static Color fColORIGINAL = new Color(80, 20, 170);
	protected static Color wColORIGINAL = new Color(40, 40, 190);
	protected static Color oCol1ORIGINAL = new Color(20,70,95);
	protected static Color oCol2ORIGINAL = new Color(50,70,95);
	protected static Color dCol = dColORIGINAL;
	protected static Color fCol = fColORIGINAL;
	protected static Color wCol = wColORIGINAL;
	protected static Color oCol1 = oCol1ORIGINAL;
	protected static Color oCol2 = oCol2ORIGINAL;
	protected static void resetColors() {
		dCol = dColORIGINAL;
		fCol = fColORIGINAL;
		wCol = wColORIGINAL;
		oCol1 = oCol1ORIGINAL;
		oCol2 = oCol2ORIGINAL;
	}
	public static void grayscaleColors() {
		dCol = Calc.toGrayscale(dColORIGINAL);
		fCol = Calc.toGrayscale(fColORIGINAL);
		wCol = Calc.toGrayscale(wColORIGINAL);
		oCol1 = Calc.toGrayscale(oCol1ORIGINAL);
		oCol2 = Calc.toGrayscale(oCol2ORIGINAL);
	}
	protected static MapDisplay Map() {return AGPmain.mainGUI.MD;}
	
	protected Shire linkedShire;
	protected double value, wval, hypw, wflow, myrbm;
	private boolean ocean;
	protected static final int BZLEN = 20;
	protected Bezier GB = new Bezier(new int[][] {{}}, BZLEN);
	protected Bezier edgeN = new Bezier(new int[3][2], BZLEN);
	protected Bezier edgeE = new Bezier(new int[3][2], BZLEN);
	protected Bezier edgeS = new Bezier(new int[3][2], BZLEN);
	protected Bezier edgeW = new Bezier(new int[3][2], BZLEN);
	protected GradientPaint GPW, GPM, GPF, GPMo, GPFo;
	protected double[] fractal,fractal0, fractal1,fractal2,fractal3,fractal4;
	protected Polygon hill = new Polygon();
	protected Ellipse2D ellipseOLD;
	protected boolean paintedOnce;
	
	public Plot(double v) {
		value = v; wflow = 0; ocean = true;
		fractal = Calc.fractal(.8, 2*(BZLEN+1));
		fractal0 = Calc.fractal(.8, 2*(BZLEN+1));
		fractal1 = Calc.fractal(.8, 2*(BZLEN+1));
		fractal2 = Calc.fractal(.8, 2*(BZLEN+1));
		fractal3 = Calc.fractal(.8, 2*(BZLEN+1));
		fractal4 = Calc.fractal(.8, 2*(BZLEN+1));
	}
	@Override
	public void setXY(int X, int Y) {
		super.setXY(X, Y);
		myrbm = rbm * AGPmain.rand.nextDouble() * 2;
//		if (x < BORDER || x >= AGPmain.mainGUI.MD.getTCols() - BORDER)
//			{value = value * Math.min(x, AGPmain.mainGUI.MD.getTCols() - 1 - x) / BORDER;
//			myrbm = myrbm * Math.min(x, AGPmain.mainGUI.MD.getTCols() - 1 - x) / BORDER;}
//		if (y < BORDER || y >= AGPmain.mainGUI.MD.getTRows() - BORDER)
//			{value = value * Math.min(y, AGPmain.mainGUI.MD.getTRows() - 1 - y) / BORDER;
//			myrbm = myrbm * Math.min(y, AGPmain.mainGUI.MD.getTRows() - 1 - y) / BORDER;}
		setGradients();
	}
	public void setGradients() {
		int H = H();   int offY = Map().getTmpY();
		GPW = new GradientPaint(0, y*H - H/2 - offY, oCol1, 0, y*H - H/2 - offY + 2, oCol2, true);
		GPM = new GradientPaint(0, y*H - H*2/3 - offY, Color.white, 0, y*H + H - offY, dCol);
		GPMo = new GradientPaint(GPM.getPoint1(), Color.black, GPM.getPoint2(), GPM.getColor2());
		GPF = new GradientPaint(0, y*H - offY, Color.gray, 0, y*H + H*3/2 - offY, fCol);
		GPFo = new GradientPaint(GPF.getPoint1(), Color.black, GPF.getPoint2(), GPF.getColor2());
	}
	/** for use with plots not shires */
	public void refreshHood() {
		HOOD[0] = getNW();
		HOOD[1] = getNE();
		HOOD[2] = getSW();
		HOOD[3] = getSE();
		HOOD[4] = getW();
		HOOD[5] = getE();
	}
	/** for use with shires */
	public void refreshHood2() {
		HOOD[0] = getNW2();
		HOOD[1] = getNE2();
		HOOD[2] = getSW2();
		HOOD[3] = getSE2();
		HOOD[4] = getW2();
		HOOD[5] = getE2();
	}
	public Plot[] myHood() {return HOOD;}
	public double getHoodAvgVal() {
		int count = 0;   double sum = CWGT * getValue();
		refreshHood();
		for (int i = myHood().length-1; i >= 0; i--) {
			if(!myHood()[i].isNull()) {count++; sum += myHood()[i].getValue();}
		}
		return sum/(count+CWGT);
	}
	public void flow() {
		//if(selected){printDetail();}
		refreshHood();
		double flow, loss = 0;
		double min = this.getEV(); int k = -1;
		for (int i = 0; i < myHood().length; i++) {
			if(myHood()[i].getEV() < min) {min = myHood()[i].getEV(); k = i;}
		}
		if (k>-1) {
			flow = Math.min(getValue() - min, wval) - loss;
			flow = (flow > 0 ? flow : 0);
			flow += (Math.min(getEV() - min, wval) - loss - flow) / 2;
			if (flow > 0) {
				loss += flow;
				chgHypW(-flow);
				wflow = flow;
				myHood()[k].chgHypW(flow);
			}
		}
	}

	public Plot getNW() {return AGPmain.mainGUI.MD.getPlotXY(x-1+(y%2), y-1);}
	public Plot getNE() {return AGPmain.mainGUI.MD.getPlotXY(x+(y%2), y-1);}
	public Plot getSW() {return AGPmain.mainGUI.MD.getPlotXY(x-1+(y%2), y+1);}
	public Plot getSE() {return AGPmain.mainGUI.MD.getPlotXY(x+(y%2), y+1);}
	public Plot getW() {return AGPmain.mainGUI.MD.getPlotXY(x-1, y);}
	public Plot getE() {return AGPmain.mainGUI.MD.getPlotXY(x+1, y);}
	// squared
	public Plot getNW2() {return AGPmain.mainGUI.MD.getPlotXY(x-1, y-2);}
	public Plot getNE2() {return AGPmain.mainGUI.MD.getPlotXY(x+1, y-2);}
	public Plot getSW2() {return AGPmain.mainGUI.MD.getPlotXY(x-1, y+2);}
	public Plot getSE2() {return AGPmain.mainGUI.MD.getPlotXY(x+1, y+2);}
	public Plot getW2() {return AGPmain.mainGUI.MD.getPlotXY(x-2, y);}
	public Plot getE2() {return AGPmain.mainGUI.MD.getPlotXY(x+2, y);}

	public double getValue() {return value;}
	public void setValue(double v) {value = v;}
	public void chgValue(double v) {value += v;}
	public double getWVal() {return wval;}
	public void chgW(double w) {wval += w;}
	public void chgHypW(double w) {hypw += w;}
	public void emptyHyp() {
		wval += hypw;   hypw = 0;
	}
	public double getEV() {return getValue() + getWVal();}
	public double getWFlow() {return wflow;}
	public double getRBM() {return myrbm;}
	public double evaporate(double rate) {
		double ev = Math.min(getWVal() , rate);
		chgW(-ev);   return ev;
	}
	public void drawPlotSimple(Graphics g) {
		int C = Calc.bound((int) (255. * getValue()), 0, 255);
		g.setColor(new Color(C,C,C));
		g.fillRect(x(), y(), W(), H());
	}
	public void drawPlot(Graphics g) {
		if (Map().SIMPLEDRAW) {drawPlotSimple(g); return;}
		g.setColor(fertile() ? fertColor() : dryColor());
		int W = W();   int H = H();
		Graphics2D g2 = (Graphics2D) g;
		if(!paintedOnce) {paintEdges(g);     paintedOnce=true; return;}
		double V = getValue();   int a = W/3;
		if(isWater()) {V /= 3;}
		if (V > 0.3) {
			int x1 = (int)(x()-a*V);   int x2 = (int)(x1 + W+2*a*V);
			int y1 = (int)(y() + H + (double)H*V/4);   int y2 = (int)(y1 - (double)2*H*V);
			GB.setPoints(new int[][] {{x1,y1},{(x1+x2)/2,y2},{x2,y1}});   GB.map();   GB.fractalize(fractal, 1*H*V/2);
			hill = new Polygon(GB.getX(), GB.getY(), GB.length());
			Bezier down = new Bezier(new int[][] {{}}, BZLEN);
			down.setPoints(new int[][] {{x1,y1-H/10},{(x1+x2)/2,y1+H/8},{x2,y1-H/10}});   down.map();
			down.fractalize(fractal0, .35, x() + W/2, y() + H/2);
			g2.setPaint((fertile() ? GPF : GPM));   g2.fill(hill);   g2.fill(new Polygon(down.getX(), down.getY(), down.length()));
			g2.setPaint((fertile() ? GPFo : GPMo));   g2.drawPolyline(GB.getX(), GB.getY(), GB.length());
		}   paintedOnce=false;
		if(fertile() && !isWater()) {drawVegetation(g);}
	}
	public void drawVegetation(Graphics g) {
		final int x = x(), y = y(), W = W(), H = H();
		final Color BROWN = new Color(80,40,0);
		for (int i = 0; i < AGPmain.rand.nextInt(42); i++) {
			int p = AGPmain.rand.nextInt(7),
				l = AGPmain.rand.nextInt(9);
			Imagery.drawSTree(g, x + p*W/6, y + (10+l)*H/15, H/4, BROWN);
		}
//		Imagery.drawSTree(g, x + 2*W/6, y + H, H/4, BROWN);
//		Imagery.drawSTree(g, x + 4*W/6, y + H, H/4, BROWN);
//		Imagery.drawBush(g, x + 1*W/6, y + 3*H/4, H/10);
//		Imagery.drawBush(g, x + 3*W/6, y + 3*H/4, H/10);
//		Imagery.drawBush(g, x + 5*W/6, y + 3*H/4, H/10);	
	}
	private Color dryColor() {
		double dryPct = Calc.bound(Math.sqrt((1.0 - getWVal()) / waterthresh), 0, 1);
		return Calc.AtoBColor(dCol, fCol, dryPct);
	}
	private Color fertColor() {
		double fertPct = Calc.bound(Math.sqrt(getWVal() / waterthresh), 0, 1);
		return Calc.AtoBColor(fCol, dCol, fertPct);
	}
	public boolean fertile() {
		refreshHood();   int n = 0;
		if(isWater()) {return true;}
		for(int p = myHood().length-1; p >= 0; p--) {
			if(myHood()[p].isWater()) {n++;}
		}   return n >= adjFert;
	}
	public boolean isWater() {return getWVal() > waterthresh;}
	public boolean isEdge(int type) {
		switch (type) {
		case 0: return !isWater(); //water
		case 1: return isWater() || !fertile(); //grass
		case 2: return fertile() || isNull(); //rock
		default: return true;
		}
	}

	private int x() {return (y%2==1?W()/2:0) + x*W() - Map().getTmpX();}
	private int y() {return y*H() - Map().getTmpY();}
	private static int W() {return AGPmain.mainGUI.MD.W();}
	private static int H() {return AGPmain.mainGUI.MD.H();}
	
	private void paintEdges(Graphics g) {
		int type = (isWater() ? 0 : (fertile() ? 1 : 2)); // 0water, 1grass, 2rock
		boolean off;   int offX = (int) (25 * W() / 100);   int offY = (int) (25 * H() / 100);   int H = H()+1;
		int[] xy = new int[2];   boolean east = getE().isEdge(type);    boolean west = getW().isEdge(type);
		int nonoffY = -H()/4;  //????
		off = (west && getNW().isEdge(type));
		xy[0] = x() + (off?offX:0);  xy[1] = y() + (off?offY:nonoffY);
		edgeN.setPoint(xy, 0);   edgeW.setPoint(xy, 0);
		off = (east && getNE().isEdge(type));
		xy[0] = x() + W() - (off?offX:0);  xy[1] = y() + (off?offY:nonoffY);
		edgeN.setPoint(xy, 2);   edgeE.setPoint(xy, 0);
		xy[0] = x() + W() / 2;  xy[1] = y();
		edgeN.setPoint(xy, 1);
		off = (west && getSW().isEdge(type));
		xy[0] = x() + (off?offX:0);  xy[1] = y() + H - (off?offY:nonoffY);
		edgeS.setPoint(xy, 0);   edgeW.setPoint(xy, 2);
		off = (east && getSE().isEdge(type));
		xy[0] = x() + W() - (off?offX:0);  xy[1] = y() + H - (off?offY:nonoffY);
		edgeS.setPoint(xy, 2);   edgeE.setPoint(xy, 2);
		xy[0] = x() + W() / 2;  xy[1] = y() + H;
		edgeS.setPoint(xy, 1);
		xy[0] = x();  xy[1] = y() + H/2;   edgeW.setPoint(xy, 1);
		xy[0] = x() + W();   edgeE.setPoint(xy, 1);
		edgeN.map();edgeE.map();edgeS.map();edgeW.map();
		

		g.setColor(type==0 ? wCol : (type==1 ? fertColor() : dryColor()));   int[] tmp;
		int[] X = new int[4];   int[] Y = new int[4]; //NW,NE,SE,SW
		tmp = edgeN.getPoint(0);  X[0] = tmp[0];  Y[0] = tmp[1];
		tmp = edgeN.getPoint(2);  X[1] = tmp[0];  Y[1] = tmp[1];
		tmp = edgeS.getPoint(2);  X[2] = tmp[0];  Y[2] = tmp[1];
		tmp = edgeS.getPoint(0);  X[3] = tmp[0];  Y[3] = tmp[1];
		double M = .5;
		edgeN.fractalize(fractal1, M, x() + W() / 2, y() + H/2);
		edgeE.fractalize(fractal2, M, x() + W() / 2, y() + H/2);
		edgeS.fractalize(fractal3, M, x() + W() / 2, y() + H/2);
		edgeW.fractalize(fractal4, M, x() + W() / 2, y() + H/2);
		g.fillPolygon(edgeN.getX(), edgeN.getY(), edgeN.length());
		g.fillPolygon(edgeE.getX(), edgeE.getY(), edgeE.length());
		g.fillPolygon(edgeS.getX(), edgeS.getY(), edgeS.length());
		g.fillPolygon(edgeW.getX(), edgeW.getY(), edgeW.length());
		g.fillPolygon(X, Y, 4);
		if(isWater()) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setPaint(GPW);
			Polygon PNE = Bezier.BCombo(new Bezier[] {edgeN, edgeE});
			Polygon PSW = Bezier.BCombo(new Bezier[] {edgeW, edgeS});
			g2d.fill(PNE);   g2d.fill(PSW);   g2d.fillPolygon(X, Y, 4);
		}
	}
	
	public boolean isNull() {return false;}
	public void printDetail() {
		System.out.println("r"+y+" c"+x+" val"+getValue()+" wval"+getWVal());
		//Calc.printArray(new int[][] {ord});
	}
	public void printDetail2() {
		System.out.println(value + "	" + wval);
	}
	public void makeLand() {ocean = false;}
	public boolean isOcean() {return ocean;}
	
	private static final int[] getShireDef = {1, 5, 3}; // NE, E, SE
	public void linkHoodToShire(Shire shire) {
		refreshHood();
		for (int neighbor : getShireDef) {HOOD[neighbor].linkToShire(shire);}
		linkToShire(shire);
	}
	public void drawShireHighlighted(Graphics g, Color color) {
		refreshHood();
		for (int neighbor : getShireDef) {HOOD[neighbor].drawHighlighted(g, color);}
		drawHighlighted(g, color);
		g.drawString(linkedShire.getID()+" "+linkedShire.getName(), x(), y() - H());
	}
	private double savedHighlight = 0;
	public void saveShireStat(double d) {
		savedHighlight = d;
	}
	public void drawShireSpotHighlight(Graphics g, double maxD) {
		if (savedHighlight > maxD || savedHighlight < 0) {
			System.out.println("fucked up color at " + this.getLinkedShire());
			System.out.println("saved highlight = " + savedHighlight);
			maxD = savedHighlight;
		}
		Color c = new Color(0f, 0f, 1f, (float)(savedHighlight / maxD));
		g.setColor(c);
		int w = W();
		g.fillOval(x()+1+w/2, y()+1-H()/2, w, w);
	}
	private void drawHighlighted(Graphics g, Color color) {
		g.setColor(color);
		g.drawRect(x()+1, y()+1, W()-2, H()-2);
	}
	private void linkToShire(Shire shire) {
		linkedShire = shire;
	}
	public Shire getLinkedShire() {return linkedShire;}
	
	@Override
	public String toString() {
		return "Plot of " + linkedShire;
	}
}

