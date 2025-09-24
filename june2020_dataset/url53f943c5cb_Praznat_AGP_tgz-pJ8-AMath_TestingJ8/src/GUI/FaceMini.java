package GUI;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import AMath.Calc;
import Defs.*;
import Game.AGPmain;
import Sentiens.Clan;
import Sentiens.Ideology;


class FaceMini extends JPanel {
	protected static final int NASO = 0;
	protected static final int BESO = 1;
	protected static final int MIRO = 2;
	protected static final int JITA = 3;
	protected static final int CARA = 4;

	BasicStroke NBS1 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0,
			new float[] {4.0f, 6.0f}, 0.0f);
	BasicStroke NBS2 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0,
			new float[] {2.0f, 8.0f}, 9.0f);
	BasicStroke NBS3 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0,
			new float[] {1.0f, 9.0f}, 9.0f);
	
	BasicStroke LBS1 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0,
			new float[] {5.0f,1.0f,1.0f,1.0f,1.0f,1.0f}, 9.0f);
	BasicStroke LBS2 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0,
			new float[] {1.0f,1.0f,1.0f,7.0f}, 9.0f);

	protected boolean loaded = false;
	protected byte[] rands;
	protected int r;
	protected Hairstyle headhair;
	protected Hairstyle chinhair;
	protected boolean female;
	protected Parte[] partez;
	protected Nariz naso;
	protected Boca beso;
	protected Ojo mirador;
	protected Oreja jitas;

	protected GKata[] allkatas;
	protected int sharpN = 50;
	protected Color scol = new Color(155,155,155);
	protected Color dcol;
	protected Color hcol = new Color(155,155,155);
	protected Color hcol2;
	protected Color ecol = scol;
	protected int ringlen = 14;
	protected int ringwid = 6;
	protected int x,y,w,h;
	
	
	protected int NOSERX, NOSERY, NOSELX, NOSELY, NOSELW, NOSEMW, NOSERW;
	protected int MOUTHBX, MOUTHBY, MOUTHP, MOUTHC, MOUTHLH, MOUTHLW, MOUTHJH, MOUTHJW;
	protected int EYERP, EYELP, EYERW, EYELW, EYEHGT, EYESPRD;
	protected int EARH, EARW, EART, EARD;
	protected int SKINR, SKING, SKINB;
	protected int HAIRL, HAIRC, HAIRW, HAIRR, HAIRG, HAIRB, HAIRX, HAIRS;
	
	
	public FaceMini() {
		rands = new byte[10001];
		AGPmain.rand.nextBytes(rands);
		setOpaque(false);
	}
	
	
	public void paintComponent(Graphics gx) {
		super.paintComponent(gx);

		if (!loaded) {return;}

		drawFace(gx,0,0,3*getWidth(),3*getHeight(),this);
		double adj = 1.3-((double)headhair.thickness/11);
		hcol = new Color((int)(adj*(double)hcol.getRed()), (int)(adj*(double)hcol.getGreen()), (int)(adj*(double)hcol.getBlue()));
		System.out.println(adj);
		headhair.thickness = (headhair.thickness + 8) / 2;
		drawFace(gx,0,0,getWidth(),getHeight(),this);
		
	}
	public void drawFace(Graphics gx, int x, int y, int w, int h, JPanel JP) {
		BufferedImage offscreen = new BufferedImage(900,900, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = offscreen.createGraphics();
		
		partez[CARA].paintPart(g);
		int[] c1 = female? Calc.between(partez[MIRO].getLeftEdge(),partez[MIRO].getRightEdge(),3,5)
						 :Calc.between(partez[MIRO].getLeftEdge(),partez[MIRO].getRightEdge(),2,5);
		headhair.paintHairs(g, c1);
		partez[BESO].paintPart(g);
		partez[MIRO].paintPart(g);
		partez[NASO].paintPart(g);
		partez[JITA].paintPart(g);
		partez[NASO].megafix();
		offscreen = ImageReader.blurImage(offscreen, 1, 1);
		gx.drawImage(offscreen,x,y,w,h,JP);
	}
	public int fix(int in, int lo, int hi) {
		return (int) Math.round(((double)in * (hi - lo) / 15) + lo);
	}
	public void loadAttributes(Ideology I) {
		NOSERX = fix(I.getFac(F_.NOSERX), 3, 20);
		NOSERY = fix(I.getFac(F_.NOSERY), -5, 10);
		NOSELX = fix(I.getFac(F_.NOSELX), 3, 20);
		NOSELY = fix(I.getFac(F_.NOSELY), -5, 20);
		NOSELW = fix(I.getFac(F_.NOSELW), 5, 25);
		NOSEMW = fix(I.getFac(F_.NOSEMW), -7, 8);
		NOSERW = fix(I.getFac(F_.NOSERW), -7, 8);
		MOUTHBX = fix(I.getFac(F_.MOUTHBX), 0, 20);
		MOUTHBY = fix(I.getFac(F_.MOUTHBY), 4, 20);
		MOUTHP = fix(I.getFac(F_.MOUTHP), 0, 15);
		MOUTHC = fix(I.getFac(F_.MOUTHC), -50, 120);
		MOUTHLH = fix(I.getFac(F_.MOUTHLH), 2, 10);
		MOUTHLW = fix(I.getFac(F_.MOUTHLW), 10, 60);
		MOUTHJH = fix(I.getFac(F_.MOUTHJH), 0, 10);
		MOUTHJW = fix(I.getFac(F_.MOUTHJW), 10, 40);
		EYERP = fix(I.getFac(F_.EYERP), 0, 50);
		EYELP = fix(I.getFac(F_.EYELP), 0, 50);
		EYERW = fix(I.getFac(F_.EYERW), 10, 30);
		EYELW = fix(I.getFac(F_.EYELW), 10, 30);
		EYEHGT = fix(I.getFac(F_.EYEHGT), 0, 10);
		EYESPRD = fix(I.getFac(F_.EYESPRD), 0, 10);
		EARH = fix(I.getFac(F_.EARH), 0, 50);
		EARW = fix(I.getFac(F_.EARW), 10, 50);
		EART = fix(I.getFac(F_.EART), 0, 15);
		EARD = fix(I.getFac(F_.EARD), 20, 80);
		SKINR = fix(I.getFac(F_.SKINR), 0, 99);
		SKING = fix(I.getFac(F_.SKING), 50, 239);
		SKINB = fix(I.getFac(F_.SKINB), 50, 239);
		HAIRL = fix(I.getFac(F_.HAIRL), 0, 30);
		HAIRC = fix(I.getFac(F_.HAIRC), 0, 89);
		HAIRW = fix(I.getFac(F_.HAIRW), 0, 10);
		HAIRR = fix(I.getFac(F_.HAIRR), 0, 255);
		HAIRG = fix(I.getFac(F_.HAIRG), 0, 255);
		HAIRB = fix(I.getFac(F_.HAIRB), 0, 255);
		HAIRX = fix(I.getFac(F_.HAIRX), 20, 99);
		HAIRS = fix(I.getFac(F_.HAIRS), 0, 9);
	}
	

	public int func(int x, double a, int exp, int w) {
		return (int) Math.round(a * (Math.pow(w,exp)-Math.pow(x,exp)));
	}
	public double distance(int[] pt1, int[] pt2) {
		return Math.sqrt(Math.pow(pt2[0] - pt1[0], 2) + Math.pow(pt2[1] - pt1[1], 2));
	}

	public GKata[] intertwineGB (GKata[] GB1, GKata[] GB2) {
		GKata[] GBout = new GKata[GB1.length + GB2.length];
		int L = Math.min(GB1.length, GB2.length);
		for(int i = 0; i < L; i++) {
			GBout[2*i] = GB1[i]; GBout[2*i + 1] = GB2[i];
		}
		return GBout;
	}
	public void pRing(Graphics g, int cx, int cy, boolean flip, int len, int wid) {
		int a = flip ? 90 : 270;
		int l = flip ? 0 : len;
		int sgn = flip ? 1 : -1;
		for (int i = 0; i < (wid+1)/2; i++) {
			g.setColor(new Color(255-i*40, 255-i*15, 255));
			pArc(g, cx, cy+l-sgn*i, a, 90, len+2*i);
		}
		g.setColor(Color.BLACK);
		pArc(g, cx, cy+l, a, 90, len);
		pArc(g, cx, cy+l-sgn*(wid+1)/2, a, 90, len+(wid));
	}
	
	public void pHair(Graphics g, int cx, int cy, int d, int curve, int len, int wid, boolean cw) {
		int ex = cx + ((int) Math.round(dCos(d) * len));
		int ey = cy - ((int) Math.round(dSin(d) * len));
		int extra = 0;
		if(!cw) {ex = cx; ey = cy; extra = 180 + curve;} //2*curve
		g.setColor(hcol);
		for (int i = 0; i < (wid+1)/2; i++) {
			pArc(g, ex, ey, d+extra, curve-i, len);
			pArc(g, ex, ey, d+extra, curve+i, len);
		}
		//g.setColor(hcol2);
		for (int i = (wid+1)/2; i < wid; i++) {
			pArc(g, ex, ey, d+extra, curve-i, len);
			pArc(g, ex, ey, d+extra, curve+i, len);
		}
		g.setColor(Color.black);
		pArc(g, ex, ey, d+extra, curve-wid, len);
		pArc(g, ex, ey, d+extra, curve+wid, len);
	}

	public void pArc(Graphics g, int cx, int cy, int d, int curve, int len) {
		int theta = curve + d - 90;
		int end = 2 * d - theta;
		int R = (int) Math.round(len / (2 * dCos(90 - curve)));
		int w = (int) Math.round((1 - dCos(180-end)) * R);
		int h = (int) Math.round((1 - dSin(180-end)) * R);
		//System.out.println("X " + (cx - w) + "   Y " + (cy - h) + "   R " + R + "   theta " + theta + "   end " + end + "   h " + h);
		g.drawArc(cx - w, cy - h, 2*R, 2*R, 180+theta, end-theta-180);
	}
	
	public void paintNotches(Graphics g, GKata GK) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(NBS1);
		g2.drawPolyline(GK.getX(), Calc.offsetArray(GK.getY(),1), GK.length());
		g2.setStroke(NBS2);
		g2.drawPolyline(GK.getX(), Calc.offsetArray(GK.getY(),2), GK.length());
		g2.drawPolyline(GK.getX(), Calc.offsetArray(GK.getY(),3), GK.length());
		g2.setStroke(NBS3);
		g2.drawPolyline(GK.getX(), Calc.offsetArray(GK.getY(),4), GK.length());
		g2.setStroke(new BasicStroke());
	}
	public void paintNotchesL(Graphics g, GKata GK, boolean up) {
		Graphics2D g2 = (Graphics2D) g;
		int sgn = (up? -1 : 1);
		g2.setStroke(LBS1);
		g2.drawPolyline(GK.getX(), Calc.offsetArray(GK.getY(),sgn*1), GK.length());
		g2.setStroke(LBS2);
		g2.drawPolyline(GK.getX(), Calc.offsetArray(GK.getY(),sgn*2), GK.length());
		if (!up) {g2.drawPolyline(GK.getX(), Calc.offsetArray(GK.getY(),sgn*3), GK.length());}
		g2.setStroke(new BasicStroke());
	}
	
	public void sortByCol(int[][] Data, int c, int sgn) {
		boolean switched = true;
		while (switched) {
			switched = false;
			for (int i = 0; i < Data.length-1; i++) {
				if (sgn * Data[i][c] > sgn * Data[i+1][c]) {
					int[] tmp = Data[i+1];
					Data[i+1] = Data[i];
					Data[i] = tmp;
					switched = true;
				}
			}
		}
	}
	public int randInt(int x) {
		return AGPmain.rand.nextInt(x);
		//return (int) (x*Math.random());
	}
	public double dSin(int d) {return Math.sin(Math.toRadians(d));}
	public double dCos(int d) {return Math.cos(Math.toRadians(d));}
	
	
	class GKata {
		int[][] XY;

		public int[][] getXY() {return XY;}
		public int[] getX() {return XY[0];}
		public int[] getY() {return XY[1];}
		public int getX(int p) {return XY[0][p];}
		public int getY(int p) {return XY[1][p];}
		public int[] getStart() {return new int[] {XY[0][0], XY[1][0]};}
		public int[] getEnd() {return new int[] {XY[0][XY[0].length-1], XY[1][XY[0].length-1]};}
		public int[] startPoint() {return new int[] {};} //from Bezier
		public int[] endPoint() {return new int[] {};} //from Bezier
		public int[][] getPoints() {return new int[][] {{}};} //from Bezier
		public int[] getPoint(int p) {return new int[] {XY[0][p], XY[1][p]};} //from Bezier
		public Arrow startArrow() { //from Bezier
			int dX = getStart()[0] - XY[0][1];
			int dY = getStart()[1] - XY[1][1];
			return new Arrow(getStart(), dX, dY);
		}
		public Arrow endArrow() { //from Bezier
			int L = length();
			int dX = getEnd()[0] - XY[0][L-2];
			int dY = getEnd()[1] - XY[1][L-2];
			return new Arrow(getEnd(), dX, dY);
		}
		public void setPoint(int[] xy, int i) {} //from Bezier
		public void setPointX(int x, int i) {} //from Bezier
		public void setPointY(int y, int i) {} //from Bezier
		public int numPoints() {return 0;} //from Bezier
		
		public int length() {return XY[0].length;}
		
		public void offset(int x, int y) {
			for (int i = 0; i < length(); i++) {
				XY[0][i] += x; XY[1][i] += y;
			}
		}
		public void squeezeXY(double m, int k) {
			for (int i = 0; i < length(); i++) {
				XY[k][i] = (int) Math.round(m*XY[k][i]);
			}
		}
		public void flipXY(int k) {
			int[] c = center();
			for (int i = 0; i < length(); i++) {
				XY[k][i] = 2 * c[k] - XY[k][i];;
			}
		}
		public int[] center() {
			double[] sums = {0,0};
			for (int i = 0; i < XY[0].length; i++) {
				sums[0]+=XY[0][i];   sums[1]+=XY[1][i];
			}
			return new int[] {(int) Math.round(sums[0]/XY[0].length), (int) Math.round(sums[1]/XY[1].length)};
		}
		public double[] closestPoint(int x, int y) {
			int[] center = center();
			double dist = Math.sqrt(Math.pow(x - center[0],2) + Math.pow(y - center[1],2));
			return new double[] {dist, 0};
		}
		

		public void drawEdge(Graphics g, int f, int[] c, boolean lashes) {
			for (int i = 0; i < length(); i+=f) {
				int d = (int) Math.round(Math.toDegrees(Math.atan((double)(c[1]-XY[1][i])/(XY[0][i]-c[0]))));
				boolean ccw = false;
				if (XY[0][i] < c[0]) {d+=180; ccw = true;}
				if (lashes) {headhair.plantlash(g, XY[0][i], XY[1][i], d, ccw);}
				else {headhair.plantone(g, XY[0][i], XY[1][i], d);}
			}
		}
		
		
		
	}
	
	class GCombo extends GKata {
		public GCombo(GKata gk1, GKata gk2) {
			XY = new int[2][gk1.length() + gk2.length()];
			System.arraycopy(gk1.getX(), 0, XY[0], 0, gk1.length());
			System.arraycopy(gk1.getY(), 0, XY[1], 0, gk1.length());
			System.arraycopy(gk2.getX(), 0, XY[0], gk1.length(), gk2.length());
			System.arraycopy(gk2.getY(), 0, XY[1], gk1.length(), gk2.length());
		}
		public GCombo(GKata[] GK) {
			int length = 0;
			int p = 0;
			for (int k = 0; k < GK.length; k++) {
				length+=GK[k].length();
			}
			XY = new int[2][length];
			for (int i = 0; i < GK[0].length(); i++) {
				XY[0][i] = GK[0].getX(i); XY[1][i] = GK[0].getY(i);
			}
			for (int k = 1; k < GK.length; k++) {
				p += GK[k-1].length();
				for (int i = p; i < p + GK[k].length(); i++) {
					XY[0][i] = GK[k].getX(i-p); XY[1][i] = GK[k].getY(i-p);
				}
			}
		}
		public void printDets() {System.out.println("GCombo"); Calc.printArray(XY);}
	}
	
	class GArc extends GKata {
		int start;
		int radius;
		public GArc(double slope) {
			radius = 100;
			start = ((int)Math.toDegrees(Math.atan(slope)));
			int N = 180 + 2*(-start) + 1;
			XY = new int[2][N];
			for (int i = 0; i < N; i++) {
				XY[0][i] = radius + (int)(radius*Math.cos(Math.toRadians((start + i) % 360)));
				XY[1][i] = radius - (int)(radius*Math.sin(Math.toRadians((start + i) % 360)));
			}
		}
		public GArc(int x, int y, int width, int height, int startA, int endA, int N) {
			if (startA > endA) {startA -= 360;}
			double sA = Math.toRadians(startA);
			double eA = Math.toRadians(endA);
			int sx = (int) Math.round(Math.cos(sA) * width / 2);
			int sy = -(int) Math.round(Math.sin(sA) * height / 2);
			XY = new int[2][N + 1];
			double i = sA;
			int k = 0; int oldk = k;
			while ((i%360) < eA) {
				oldk = k;
				XY[0][k] = x-sx + (int) Math.round(Math.cos(i) * width / 2);
				XY[1][k] = y-sy - (int) Math.round(Math.sin(i) * height / 2);
				i += (eA-sA)/N;   k = (int)(N*(i-sA)/(eA-sA));
				if (k>oldk+1) {XY[0][oldk+1] = XY[0][oldk];   XY[1][oldk+1] = XY[1][oldk];}
			}
			XY[0][N] = x-sx + (int) Math.round(Math.cos(eA) * width / 2);
			XY[1][N] = y-sy - (int) Math.round(Math.sin(eA) * height / 2);
			//System.out.println("X"+x+", Y"+y);
			//System.out.println("AX"+XY[0][0]+", AY"+XY[1][0]);
		}
		public int getXHWID() {
			return XY[0][0] - getRadius();
		}
		public int getRadius() {
			int maxX = 0;
			for (int i = 0; i < length(); i++) {
				if (XY[0][i] > maxX) {maxX = XY[0][i];}
			}
			return maxX - XY[0][length()/2];
		}
	}
	
	class GPow extends GKata {
		double slope;
		int power;
		int XHWID;
		public GPow(double s, int p, int x) {
			slope = s;
			power = p;
			XHWID = x;
			XY = new int[2][];
			XY[0] = new int[2*XHWID+1];
			XY[1] = new int[2*XHWID+1];
			double a = slope / power / Math.pow(XHWID, power-1);
			int k = 0;
			for (int i = -XHWID; i <= XHWID; i++) {
				XY[0][k] = i + XHWID; XY[1][k] = func(i, a, power, XHWID);
				k++;
			}
		}
		public void setSlope(double s) {slope = s;}
		public double getSlope() {return slope;}
		public void setPower(int p) {power = p;}
		public int getPower() {return power;}
		public void setXHWID(int x) {XHWID = x;}
		public int getXHWID() {return XHWID;}
	}

	
	class GBezier extends GKata {
		private int[][] points;
		private int intervals;
		public GBezier(int[][] pts, int N) {
			points = pts;
			intervals = N;
			map();
		}
		public GBezier(int[] pt1, double slope1, int[] pt2, double slope2, int N) {
			points = new int[3][2];
			points[0] = pt1; points[2] = pt2;
			if (slope1-slope2 == 0) {
				points[1][0] = (int) Math.round((double)(points[0][0]+points[2][0])/2);
				points[1][1] = (int) Math.round((double)(points[0][1]+points[2][1])/2);
			}
			else {
				double X = (slope2*pt2[0]-slope1*pt1[0]+pt2[1]-pt1[1])/(slope2-slope1);
				points[1][0] = (int) Math.round(X);
				points[1][1] = (int) Math.round(-slope1*(X-pt1[0])+pt1[1]);
			}
			intervals = N;
			map();
		}
		public GBezier (Arrow a1, Arrow a2, double F, int N) {
			points = new int[4][2];
			points[0] = a1.getXY();   points[3] = a2.getXY();
			double dist = distance(points[0], points[3]);
			int[] mp1 = {points[0][0] - (points[3][1]-points[0][1]), points[0][1] + (points[3][0]-points[0][0])};
			int[] mp2 = {points[3][0] - (points[3][1]-points[0][1]), points[3][1] + (points[3][0]-points[0][0])};
			double sidedist1 = distance(points[3], mp1);
			double sidedist2 = distance(points[0], mp2);
			double dist1 = Math.min(dist, sidedist1) * F;
			double dist2 = Math.min(dist, sidedist2) * F;
			points[1][0] = points[0][0] + (int) Math.round(dist1 * a1.cosine());
			points[1][1] = points[0][1] + (int) Math.round(dist1 * a1.sine());
			points[2][0] = points[3][0] + (int) Math.round(dist2 * a2.cosine());
			points[2][1] = points[3][1] + (int) Math.round(dist2 * a2.sine());
			intervals = N;
			map();
		}

		
		public void map() {
			//points is start, controls, end
			double[][][] V = new double[points.length-1][intervals+1][2];
			for (int c = 0; c < V.length; c++) {
				for (int i = 0; i < V[c].length; i++) {
					V[c][i][0] = (1 - (double)i/intervals)*points[c][0] + ((double)i/intervals)*points[c+1][0];
					V[c][i][1] = (1 - (double)i/intervals)*points[c][1] + ((double)i/intervals)*points[c+1][1];
				}
			}
			while (V.length > 1) {
				double[][][] VV = new double[V.length - 1][intervals+1][2];
				for (int c = 0; c < VV.length; c++) {
					for (int i = 0; i < VV[c].length; i++) {
						VV[c][i][0] = (1 - (double)i/intervals)*V[c][i][0] + ((double)i/intervals)*V[c+1][i][0];
						VV[c][i][1] = (1 - (double)i/intervals)*V[c][i][1] + ((double)i/intervals)*V[c+1][i][1];
					}
				}
				V = VV;
			}
			XY = new int[2][V[0].length];
			for(int i = 0; i < XY[0].length; i++) {
				XY[0][i] = (int) Math.round(V[0][i][0]);
				XY[1][i] = (int) Math.round(V[0][i][1]);
			}
		}
		public int numPoints() {return points.length;}
		public double[] closestPoint(int x, int y) {
			//returns closest Bezier Point to given input point
			double mindist = 10000000; int k = -1;
			for (int i = 0; i < points.length; i++) {
				double dist = Math.sqrt(Math.pow(x - points[i][0],2) + Math.pow(y - points[i][1],2));
				if (dist < mindist) {mindist = dist; k = i;}
			}
			return new double[] {mindist, k};
		}
		public void setPoint(int[] xy, int i) {
			points[i][0] = xy[0];
			points[i][1] = xy[1];
			map();
		}
		public void setPointX(int x, int i) {
			points[i][0] = x; map();
		}
		public void setPointY(int y, int i) {
			points[i][1] = y; map();
		}
		public int[][] getPoints() {return points;}
		public int[] getPoint(int p) {return points[p];}
		public int[] startPoint() {return points[0];}
		public int[] endPoint() {return points[points.length-1];}
		public Arrow startArrow() {
			int dX = points[0][0] - points[1][0];
			int dY = points[0][1] - points[1][1];
			return new Arrow(points[0], dX, dY);
		}
		public Arrow endArrow() {
			int L = points.length;
			int dX = points[L-1][0] - points[L-2][0];
			int dY = points[L-1][1] - points[L-2][1];
			return new Arrow(points[L-1], dX, dY);
		}
		public int[] center() {
			double[] sums = {0,0};
			for (int i = 0; i < points.length; i++) {
				sums[0]+=points[i][0];   sums[1]+=points[i][1];
			}
			return new int[] {(int) Math.round(sums[0]/points.length), (int) Math.round(sums[1]/points.length)};
		}
		
	}
	class Arrow {
		int dx;
		int dy;
		int[] xy;
		public Arrow() {
			xy = new int[] {0,0}; dx = 0; dy = 0;
		}
		public Arrow(int[] XY, int dX, int dY) {
			xy = XY; dx = dX; dy = dY;
		}
		public int getDX() {return dx;}
		public int getDY() {return dy;}
		public int[] getXY() {return xy;}
		public int side(int[] ref) {
			double y = dy*(double)ref[0]/(double)dx;
			return (int) Math.signum(y - ref[0]);
		}
		public double slope() {return (double) dy / (double) dx;}
		public double hypotenuse() {
			return Math.sqrt(dx*dx + dy*dy);
		}
		public double acosine() {
			return Math.cos(Math.atan(slope()));
		}
		public double asine() {
			return Math.sin(Math.atan(slope()));
		}
		public double cosine() {
			return (double)dx / hypotenuse();
		}
		public double sine() {
			return (double)dy / hypotenuse();
		}
	}

	class Parte {
		protected GKata[] K;
		protected int [] baseXY;
		protected int selected;
		protected int[] temp;

		protected static final double Npointiness = 1;
		
		public GCombo Whole() {
			return new GCombo(K);
		}
		public GKata getGK(int k) {return K[k];}
		public int[] getBaseXY() {return baseXY;}
		public void setBaseXY(int[]xy) {baseXY[0] = xy[0];   baseXY[1] = xy[1];}
		public void refix(){}
		public void printSpecs() {
			for (int i = 0; i < K.length; i++) {
				System.out.println();
				Calc.printArray(K[i].getPoints());
			}
		}
		public int[][] getPoints() {
			int[] L = new int[K.length];
			for (int i = 0; i < L.length; i++) {L[i] = K[i].numPoints();}
			int cum = 0;
			int[][] M = new int[Calc.sum(L)][2];
			for (int i = 0; i < L.length; i++) {
				for (int j = 0; j < L[i]; j++) {
					M[i + cum + j] = K[i].getPoint(j);
				}
				cum += L[i]-1;
			}
			return M;			
		}
		public void paintPart(Graphics g) {
			GCombo polygon = Whole();
			g.setColor(scol);
			g.fillPolygon(polygon.getX(), polygon.getY(), polygon.length());
			g.setColor(new Color(0xff000000));
			g.drawPolyline(polygon.getX(), polygon.getY(), polygon.length());
		}
		public void paintPart(Graphics g, Color c) {
			GCombo polygon = Whole();
			g.setColor(c);
			g.fillPolygon(polygon.getX(), polygon.getY(), polygon.length());
			g.setColor(new Color(0xff000000));
			g.drawPolyline(polygon.getX(), polygon.getY(), polygon.length());
			//printSpecs();   polygon.printDets();
		}
		public void paintHair(Graphics g) {}


		
		public int[] getLeftEdge() {return new int[] {0, 0};}
		public int[] getRightEdge() {return new int[] {0, 0};}
		public Arrow getJawArrow() {return new Arrow();}
		public int[] getCenter() {
			double[] sums = {0,0};
			for (int i = 0; i < K.length; i++) {
				int[] C = K[i].center();
				sums[0]+=C[0];   sums[1]+=C[1];
			}
			return new int[] {(int) Math.round(sums[0]/K.length), (int) Math.round(sums[1]/K.length)};
		}
		public void megafix() {}
	}
	
	class Nariz extends Parte {
		public static final int NUPPER = 0;
		public static final int POINT = 1;
		public static final int NLOWER = 2;
		public static final int TRIL = 3;
		
		protected static final int minwid = 8;
		
		protected int[][] tops;
		protected int[][] bottoms;
		protected int[] nasoness;//not input
		
		
		public Nariz(int[] base) {
			K = new GKata[4];
			baseXY = new int[] {base[0], base[1]};
			
			tops = new int[3][2]; tops[0][0] = baseXY[0];  tops[0][1] = baseXY[1];

			tops[1][0] = tops[0][0] - NOSERX;
			tops[1][1] = tops[0][1] + NOSERY;
			tops[2][0] = tops[1][0] - NOSELX;
			tops[2][1] = tops[1][1] + NOSELY;
			
			bottoms = new int[3][2];
			bottoms[0][0] = tops[2][0];
			bottoms[0][1] = tops[2][1] + NOSELW;
			bottoms[1][0] = tops[1][0];
			bottoms[1][1] = bottoms[0][1] + NOSEMW;
			bottoms[2][0] = tops[0][0];
			bottoms[2][1] = bottoms[1][1] + NOSERW;
			K[NUPPER] = new GBezier(tops, sharpN);
			K[NLOWER] = new GBezier(bottoms, sharpN);
			nasoness = new int[] {10, Math.min(8, Math.abs(bottoms[2][1] - tops[0][1])/2)};
			refix();
		}
		
		public void refix() {
			for (int i = 0; i < 3; i++) {
				K[NLOWER].setPointY(Math.max(K[NLOWER].getPoint(2-i)[1], minwid + K[NUPPER].getPoint(i)[1]), 2-i);
			}
			//K[NLOWER].setPointY(Math.max(2*minwid + K[NUPPER].startPoint()[1], K[NLOWER].endPoint()[1]), K[NLOWER].numPoints()-1);
			
			
			nasoness[1] = Math.min(8, Math.abs(K[NLOWER].endPoint()[1] - K[NUPPER].startPoint()[1])/2);
			int[] LR = K[NLOWER].endPoint();
			K[TRIL] = new GArc(LR[0], LR[1], nasoness[0], nasoness[1], -180, 90, sharpN);
			K[POINT] = new GBezier(K[NUPPER].endArrow(), K[NLOWER].startArrow(), Npointiness, sharpN);
		}

		
		public void paintPart(Graphics g) {
			g.setColor(scol);
			int[] u = K[NUPPER].startPoint();
			int[] b = K[TRIL].getEnd();
			int[] j = partez[BESO].getLeftEdge();
			int[][] T = {new int[] {u[0], u[0], u[0], j[0], b[0]}, new int[] {u[1], b[1], j[1], b[1], b[1]}};
			g.fillPolygon(T[0], T[1], T[0].length);

			super.paintPart(g);
			
			g.setColor(dcol);
			paintNotches(g, K[NUPPER]);
			g.setColor(new Color(0xff000000));
			int[] LR = K[NLOWER].endPoint();
			g.fillOval(LR[0]+nasoness[0]/6, LR[1]-nasoness[1]/10, nasoness[0]*2/3, nasoness[1]/2);
		}

		public void megafix() {
			K[NUPPER] = new GBezier(tops, sharpN);
			K[NLOWER] = new GBezier(bottoms, sharpN);
			refix();
			partez[MIRO].setBaseXY(K[NUPPER].startPoint());   partez[MIRO].refix(); 
			partez[BESO].setBaseXY(K[NLOWER].endPoint());   partez[BESO].refix(); 
		}
		public int[] getLeftEdge() {return K[POINT].getPoint(1);}
	}
	
	class Boca extends Parte {
		public static final int BRIDGE = 0;
		public static final int UPPER = 1;
		public static final int MIDDLE = 2;
		public static final int LOWERL = 3;
		public static final int LOWERR = 4;
		public static final int JAW = 5;
		public static final int WRINKLE = 6;
		
		
		protected int[] LbaseXY;
		protected int width;
		protected int height;
		protected int jawwidth;
		protected int jawheight;
		protected double pointiness;
		protected int cavity;
		protected int openness;
		
		GKata BL; //not input
		
		public Boca(int[] base) {
			K = new GKata[7];
			baseXY = new int[] {base[0], base[1]};
			cavity = MOUTHC;
			pointiness = (double) MOUTHP / 15;
			openness = randInt(12);
			width = MOUTHLW;
			height = MOUTHLH;
			jawwidth = width + MOUTHJW;
			jawheight = height + (int)(pointiness*height) + MOUTHJH + cavity/2;
			LbaseXY = new int[] {baseXY[0] - width/10 - MOUTHBX, baseXY[1] + height + MOUTHBY};
			refix();
		}

		public void refix() {
			openness = Math.max(0, Math.min(openness, 12));
			pointiness = Math.max(0, Math.min(pointiness, 1));
			cavity = Math.min(Math.max(cavity, -50), 150);
			height = Math.min(Math.max(height, 2), 10);
			width = Math.min(Math.max(width, 3*height/2), 60);
			jawheight = Math.max(10, Math.max(jawheight, height + (int)(pointiness*height) + cavity/4));
			LbaseXY[0] = Math.max(Math.min(baseXY[0] - width/10, LbaseXY[0]), baseXY[0] - width);
			LbaseXY[1] = Math.min(baseXY[1] + height + 20, Math.max(baseXY[1] + height + 4, LbaseXY[1]));
			int[][] BN = new int[3][2];
			BN[0][0] = baseXY[0];   BN[0][1] = baseXY[1];
			BN[1][0] = baseXY[0] + (LbaseXY[0]- baseXY[0])*3/5;;   BN[1][1] = baseXY[1] + (LbaseXY[1]- baseXY[1])*3/5;
			BN[2][0] = LbaseXY[0];   BN[2][1] = LbaseXY[1];
			K[BRIDGE] = new GBezier(BN, sharpN);
			int[][] UN = new int[7][2];
			UN[0][0] = LbaseXY[0];   UN[0][1] = LbaseXY[1];
			UN[1][0] = LbaseXY[0] + width*5/12;   UN[1][1] = LbaseXY[1] - 2*height;
			UN[2][0] = LbaseXY[0] + width*11/24;   UN[2][1] = LbaseXY[1] + height*3/2;
			UN[3][0] = LbaseXY[0] + width/2;   UN[3][1] = LbaseXY[1] - height*7/2;
			UN[4][0] = LbaseXY[0] + width*13/24;   UN[4][1] = LbaseXY[1] + height*3/2;
			UN[5][0] = LbaseXY[0] + width*7/12;   UN[5][1] = LbaseXY[1] - 2*height;
			UN[6][0] = LbaseXY[0] + width;   UN[6][1] = LbaseXY[1];
			K[UPPER] = new GBezier(UN, sharpN);
			int[][] MN = new int[5][2];
			MN[0][0] = LbaseXY[0];   MN[0][1] = LbaseXY[1];
			MN[1][0] = LbaseXY[0] + width*5/12;   MN[1][1] = LbaseXY[1] - height/2;
			MN[2][0] = LbaseXY[0] + width/2;   MN[2][1] = LbaseXY[1] + height/2;
			MN[3][0] = LbaseXY[0] + width*7/12;   MN[3][1] = LbaseXY[1] - height/2;
			MN[4][0] = LbaseXY[0] + width;   MN[4][1] = LbaseXY[1];
			K[MIDDLE] = new GBezier(MN, sharpN);
			int[][] LN = new int[4][2];
			LN[3][0] = LbaseXY[0];   LN[3][1] = LbaseXY[1] + openness;
			LN[2][0] = LbaseXY[0] + width/6;   LN[2][1] = LbaseXY[1] + height*3/4 + openness;
			LN[1][0] = LbaseXY[0] + width*2/6;   LN[1][1] = LbaseXY[1] + height*3/4 + openness;
			LN[0][0] = LbaseXY[0] + width*3/6;   LN[0][1] = LbaseXY[1] + height*2/3 + openness;
			K[LOWERR] = new GBezier(LN, sharpN);
			LN[3][0] = LbaseXY[0] + width*3/6;   LN[3][1] = LbaseXY[1] + height*2/3 + openness;
			LN[2][0] = LbaseXY[0] + width*4/6;   LN[2][1] = LbaseXY[1] + height*3/4 + openness;
			LN[1][0] = LbaseXY[0] + width*5/6;   LN[1][1] = LbaseXY[1] + height*3/4 + openness;
			LN[0][0] = LbaseXY[0] + width;   LN[0][1] = LbaseXY[1] + openness;
			K[LOWERL] = new GBezier(LN, sharpN);
			int[][] JN = new int[5][2];
			int pnt = (int) Math.round(pointiness * width/2);
			JN[0][0] = LbaseXY[0];   JN[0][1] = LbaseXY[1] + openness;
			JN[1][0] = LbaseXY[0] + pnt;   JN[1][1] = LbaseXY[1] + jawheight + cavity/2 + openness;
			JN[2][0] = LbaseXY[0] + width/2;   JN[2][1] = LbaseXY[1] + jawheight - cavity + openness;
			JN[3][0] = LbaseXY[0] + width - pnt;   JN[3][1] = LbaseXY[1] + jawheight + cavity/2 + openness;
			JN[4][0] = LbaseXY[0] + jawwidth;   JN[4][1] = LbaseXY[1];
			K[JAW] = new GBezier(JN, sharpN);
			int[][] BLN = new int[5][2];
			BLN[4][0] = LbaseXY[0];   BLN[4][1] = LbaseXY[1] + openness;
			BLN[3][0] = LbaseXY[0] + pnt;   BLN[3][1] = LbaseXY[1] + jawheight*2/3 + cavity/3 + openness;
			BLN[2][0] = LbaseXY[0] + width/2;   BLN[2][1] = LbaseXY[1] + jawheight - cavity + openness;
			BLN[1][0] = LbaseXY[0] + width - pnt;   BLN[1][1] = LbaseXY[1] + jawheight*2/3 + cavity/3 + openness;
			BLN[0][0] = LbaseXY[0] + jawwidth;   BLN[0][1] = LbaseXY[1] + openness;
			BL = new GBezier(BLN, sharpN);
			int[][] WN = new int[5][2];
			WN[4][0] = partez[NASO].getGK(Nariz.TRIL).getEnd()[0];   WN[4][1] = partez[NASO].getGK(Nariz.TRIL).getEnd()[1];
			WN[3][0] = LbaseXY[0] + width;   WN[3][1] = partez[NASO].getGK(Nariz.TRIL).getStart()[1];
			WN[2][0] = LbaseXY[0] + width;   WN[2][1] = LbaseXY[1];
			WN[1][0] = LbaseXY[0] + (width+jawwidth)/2;   WN[1][1] = (baseXY[1] + LbaseXY[1])/2;
			WN[0][0] = LbaseXY[0] + width;   WN[0][1] = LbaseXY[1] + jawheight/2 + openness;
			K[WRINKLE] = new GBezier(WN, sharpN);
		}
		public void paintPart(Graphics g) {
			GCombo shape = new GCombo(new GKata[] {K[BRIDGE], K[JAW]});
			//GCombo labios = new GCombo(new GKata[] {K[UPPER], K[LOWERL], K[LOWERR]});
			GCombo labios = new GCombo(new GKata[] {K[LOWERL], K[LOWERR]});
			g.setColor(scol);
			
			g.fillPolygon(shape.getX(), shape.getY(), shape.length());
			g.setColor(new Color(0xff000000));
			g.drawPolyline(shape.getX(), shape.getY(), shape.length());


			if (!female) {
				GCombo beard = new GCombo(new GKata[] {K[JAW], BL});
				GCombo wrinkle = new GCombo(new GKata[] {K[WRINKLE]});
				g.setColor(dcol);
				g.drawPolyline(Calc.offsetArray(wrinkle.getX(),-1), wrinkle.getY(), wrinkle.length());
				g.setColor(new Color(0xff000000));
				g.drawPolyline(wrinkle.getX(), wrinkle.getY(), wrinkle.length());
				
				chinhair.setHairs(beard, 4);
				chinhair.paintHairs(g, partez[BESO].getRightEdge());
			}
			
			g.setColor(Color.black);
			g.fillRect(LbaseXY[0], LbaseXY[1], width+1, openness);
			GBezier Side = new GBezier(K[UPPER].endArrow(), K[LOWERL].startArrow(), Npointiness/2, sharpN);
			g.fillPolygon(Side.getX(), Side.getY(), Side.length());
			
			int NT = 6; //TEETH
			for (int i = 0; i < NT; i++) {
				g.setColor(ecol);
				if (!female) {g.fillRect(LbaseXY[0] + i*width/NT, LbaseXY[1], width/NT, openness);}
				g.fillRect(LbaseXY[0] + i*width/NT, LbaseXY[1]+1, width/NT-(female?0:1), openness-(female?1:2));
				g.setColor(Color.black);
				g.drawRect(LbaseXY[0] + i*width/NT, LbaseXY[1], width/NT, openness);
				int h = openness/2 + (female ? 0 : i%2 - 1);
				g.drawLine(LbaseXY[0] + i*width/NT, LbaseXY[1] + h, LbaseXY[0] + (i+1)*width/NT, LbaseXY[1] + h);
			}

			g.setColor(scol);
			if (female) {
				g.setColor(scol.darker());  //g.setColor(scol.brighter());
			}
			g.fillPolygon(labios.getX(), labios.getY(), labios.length());
			g.fillPolygon(K[UPPER].getX(), K[UPPER].getY(), K[UPPER].length());
			
			g.fillPolygon(labios.getX(), Calc.offsetArray(labios.getY(),3), labios.length());
			g.setColor(Color.black);

			g.fillPolygon(K[MIDDLE].getX(), K[MIDDLE].getY(), K[MIDDLE].length());
			g.setColor(dcol);
			g.drawPolyline(K[MIDDLE].getX(), K[MIDDLE].getY(), K[MIDDLE].length());
			g.setColor(female?scol:dcol);
			paintNotchesL(g, K[MIDDLE], true);
			
			g.setColor(Color.black);
			//g.drawPolyline(labios.getX(), labios.getY(), labios.length());
			g.drawPolyline(K[UPPER].getX(), K[UPPER].getY(), K[UPPER].length());
			g.drawPolyline(labios.getX(), Calc.offsetArray(labios.getY(),3), labios.length());
			K[MIDDLE].offset(0, openness);
			g.drawPolyline(K[MIDDLE].getX(), K[MIDDLE].getY(), K[MIDDLE].length());
			g.setColor(female?scol:dcol);
			paintNotchesL(g, K[MIDDLE], false);
		}
		

		public int[] getLeftEdge() {return new int[] {baseXY[0], LbaseXY[1]-height};}
		public int[] getRightEdge() {return K[UPPER].getEnd();}
		public Arrow getJawArrow() {return K[JAW].endArrow();}
		
	}
	
	
	class Ojo extends Parte {
		public static final int RCIRCTOP = 0;
		public static final int RCIRCBOTTOM = 1;
		public static final int RLIDTOP = 2;
		public static final int RLIDBOTTOM = 3;
		public static final int LCIRCTOP = 4;
		public static final int LCIRCBOTTOM = 5;
		public static final int LLIDTOP = 6;
		public static final int LLIDBOTTOM = 7;
		public static final int BROW = 8;

		
		protected int rpoint;
		protected int lpoint;
		protected int rwid;
		protected int lwid;
		protected int lidheight;
		protected int spread;
		
		protected int rhgt; //not input
		protected int lhgt; //not input

		public Ojo(int[] base) {
			K = new GKata[9];
			baseXY = new int[] {base[0], base[1]};
			rpoint = 50 - EYERP;
			lpoint = 50 - EYELP;
			rwid = EYERW;
			lwid = EYELW;
			if(rwid > lwid) {int tmp = lwid; lwid = rwid; rwid = tmp;}
			
			lidheight = EYEHGT;
			spread = EYESPRD;
			
			rhgt = rwid;   lhgt = lwid;
			//rwid = (int)Math.round((double)rwid / Math.cos(Math.toRadians(rpoint)));
			//lwid = (int)Math.round((double)lwid / Math.cos(Math.toRadians(lpoint)));
			
			refix();
		}

		public void refix() {
			
			K[RCIRCTOP] = new GArc(baseXY[0], baseXY[1], rwid, rhgt, rpoint, 180-rpoint, sharpN);
			int[] ep = K[RCIRCTOP].getEnd();
			K[RCIRCBOTTOM] = new GArc(ep[0], ep[1], rwid, rhgt, -(180-rpoint), -rpoint, sharpN);
			
			K[LCIRCBOTTOM] = new GArc(baseXY[0] + spread, baseXY[1], lwid, lhgt, -(180-lpoint), -lpoint, sharpN);
			ep = K[LCIRCBOTTOM].getEnd();
			K[LCIRCTOP] = new GArc(ep[0], ep[1], lwid, lhgt, lpoint, 180-lpoint, sharpN);
			
			rpoint -= 10;   lpoint -= 10;
			rwid = (int)Math.round((double)rhgt / Math.cos(Math.toRadians(rpoint)));
			lwid = (int)Math.round((double)lhgt / Math.cos(Math.toRadians(lpoint)));
			
			K[RLIDTOP] = new GArc(baseXY[0], baseXY[1], rwid, rwid+lidheight, rpoint, 180-rpoint, sharpN);
			ep = K[RLIDTOP].getEnd();
			K[RLIDBOTTOM] = new GArc(ep[0], ep[1], rwid, rwid+lidheight, -(180-rpoint), -rpoint, sharpN);
			
			K[LLIDBOTTOM] = new GArc(baseXY[0] + spread, baseXY[1], lwid, lwid+lidheight, -(180-lpoint), -lpoint, sharpN);
			ep = K[LLIDBOTTOM].getEnd();
			K[LLIDTOP] = new GArc(ep[0], ep[1], lwid, lwid+lidheight, lpoint, 180-lpoint, sharpN);
			
			int[][] UN = new int[11][2];
			UN[0][0] = baseXY[0] - rhgt;   UN[0][1] = baseXY[1];
			UN[1][0] = baseXY[0] - rhgt*3/2;   UN[1][1] = baseXY[1] - rhgt - lidheight/2;
			UN[2][0] = baseXY[0] - rhgt;   UN[2][1] = baseXY[1] - rhgt - lidheight*3/2;
			UN[3][0] = baseXY[0];   UN[3][1] = baseXY[1] - rhgt - lidheight*5/2;
			UN[4][0] = baseXY[0];   UN[4][1] = baseXY[1] - rhgt - lidheight*4/2;
			UN[5][0] = baseXY[0] + spread/2;   UN[5][1] = baseXY[1] + rhgt + lhgt + lidheight*5/2;
			UN[6][0] = baseXY[0] + spread;   UN[6][1] = baseXY[1] - lhgt - lidheight*4/2;
			UN[7][0] = baseXY[0] + spread;   UN[7][1] = baseXY[1] - lhgt - lidheight*5/2;
			UN[8][0] = baseXY[0] + spread + lhgt;   UN[8][1] = baseXY[1] - lhgt - lidheight*3/2;
			UN[9][0] = baseXY[0] + spread + lhgt*3/2;   UN[9][1] = baseXY[1] - lhgt - lidheight/2;
			UN[10][0] = baseXY[0] + spread + lhgt;   UN[10][1] = baseXY[1];
			K[BROW] = new GBezier(UN, sharpN);
			
			rwid = rhgt;  lwid = lhgt;
			rpoint += 10;   lpoint += 10;
		}
		
		public void paintPart(Graphics g) {
			GCombo ojoL1 = new GCombo(new GKata[] {K[LCIRCBOTTOM], K[LCIRCTOP]});
			GCombo ojoR1 = new GCombo(new GKata[] {K[RCIRCBOTTOM], K[RCIRCTOP]});
			GCombo ojoL2 = new GCombo(new GKata[] {K[LLIDBOTTOM], K[LLIDTOP]});
			GCombo ojoR2 = new GCombo(new GKata[] {K[RLIDBOTTOM], K[RLIDTOP]});
			if (female) {
				ojoL2 = new GCombo(new GKata[] {K[LLIDTOP]});
				ojoR2 = new GCombo(new GKata[] {K[RLIDTOP]});
			}		
			g.setColor(dcol);
			if (!female) {
				g.setColor(scol);
				g.fillPolygon(K[BROW].getX(), K[BROW].getY(), K[BROW].length());
				g.setColor(ecol);
				g.drawPolyline(K[BROW].getX(), K[BROW].getY(), K[BROW].length());
				g.setColor(dcol);
				g.drawPolyline(K[RLIDBOTTOM].getX(), Calc.offsetArray(K[RLIDBOTTOM].getY(),1), K[RLIDBOTTOM].length());
				g.drawPolyline(K[LLIDBOTTOM].getX(), Calc.offsetArray(K[LLIDBOTTOM].getY(),1), K[LLIDBOTTOM].length());
			}
			g.fillPolygon(ojoL2.getX(), ojoL2.getY(), ojoL2.length());
			g.fillPolygon(ojoR2.getX(), ojoR2.getY(), ojoR2.length());
			g.setColor(ecol);
			g.drawPolyline(ojoL2.getX(), ojoL2.getY(), ojoL2.length());
			g.drawPolyline(ojoR2.getX(), ojoR2.getY(), ojoR2.length());

			int[] rt = K[RCIRCTOP].center();
			int[] lt = K[LCIRCTOP].center();
			if (female) {
				K[RCIRCTOP].drawEdge(g, 5, rt, true);
				K[LCIRCTOP].drawEdge(g, 5, lt, true);
			}
			
			g.setColor(ecol);
			g.fillPolygon(ojoL1.getX(), ojoL1.getY(), ojoL1.length());
			g.fillPolygon(ojoR1.getX(), ojoR1.getY(), ojoR1.length());
			if (female) {
				g.setColor(Color.gray);
				g.drawPolyline(ojoL1.getX(), Calc.offsetArray(ojoL1.getY(),-1), ojoL1.length());
				g.drawPolyline(ojoR1.getX(), Calc.offsetArray(ojoR1.getY(),-1), ojoR1.length());
			}
			g.setColor(new Color(0xff000000));
			g.drawPolyline(ojoL1.getX(), ojoL1.getY(), ojoL1.length());
			g.drawPolyline(ojoR1.getX(), ojoR1.getY(), ojoR1.length());

			//g.fillOval(rt[0] - rs/2, rt[1], rs, rs);
			//g.fillOval(lt[0] - ls/2, lt[1], ls, ls);

		}
		


		//public int[] getLeftEdge() {return K[BROW].getStart();}
		public int[] getLeftEdge() {return K[RCIRCBOTTOM].getStart();}
		public int[] getRightEdge() {return K[BROW].getEnd();}
		
	}
	
	
	class Oreja extends Parte {
		public static final int EUPPER = 0;
		public static final int EPOINT = 1;
		public static final int EOUTER = 2;
		public static final int RIDGE = 3;
		public static final int LOBE = 4;

		protected static final int sidehgt = 15;
		protected static final int Epointiness = 1;
		
		protected int Eheight;
		protected int Ewidth;
		protected double thickness;
		protected int droop;
	
		public Oreja(int[] base) {
			K = new GKata[5];

			Eheight = EARH;
			Ewidth = EARW;
			thickness = 0.2 + 0.8 * EART / 15;
			droop = EARD;
			
			refix();
		}
		

		public void refix() {
			Ewidth = Math.max(Ewidth, 20);
			thickness = Math.min(Math.max(thickness, 0), 1);
			int[] bEnd = partez[MIRO].getRightEdge();
			int[] jEnd = partez[BESO].getJawArrow().getXY();
			baseXY = Calc.between(bEnd, jEnd,2,3);
			int[][] EUN = new int[3][2];
			EUN[0][0] = baseXY[0];   EUN[0][1] = baseXY[1] - sidehgt;
			EUN[1][0] = baseXY[0] + Ewidth*4/7;   EUN[1][1] = baseXY[1] - sidehgt - Eheight*3/7;
			EUN[2][0] = baseXY[0] + Ewidth;   EUN[2][1] = baseXY[1] - sidehgt - Eheight;
			K[EUPPER] = new GBezier(EUN, sharpN);
			int[][] ON = new int[4][2];
			ON[0][0] = baseXY[0] + Ewidth;   ON[0][1] = baseXY[1] - Eheight;
			ON[1][0] = baseXY[0] + (int)(Ewidth*thickness);   ON[1][1] = baseXY[1] - (int)(Eheight*thickness);
			ON[2][0] = baseXY[0] + (int)(Ewidth*thickness/2);   ON[2][1] = baseXY[1] + droop;
			ON[3][0] = baseXY[0];   ON[3][1] = baseXY[1];
			K[EOUTER] = new GBezier(ON, sharpN);
			K[EPOINT] = new GBezier(K[EUPPER].endArrow(), K[EOUTER].startArrow(), Epointiness, sharpN);
			int[][] RN = new int[3][2];
			RN[0][0] = baseXY[0];   RN[0][1] = baseXY[1] - sidehgt/2;
			RN[1][0] = baseXY[0] + Ewidth*4/7;   RN[1][1] = baseXY[1] - sidehgt/2 - Eheight*3/7;
			RN[2][0] = baseXY[0] + Ewidth;   RN[2][1] = baseXY[1] - sidehgt/2 - Eheight;
			K[RIDGE] = new GBezier(RN, sharpN);
			int[][] ELN = new int[3][2];
			ELN[0][0] = baseXY[0];   ELN[0][1] = baseXY[1] - sidehgt/4;
			ELN[1][0] = baseXY[0] + (int)(Ewidth*thickness/2);   ELN[1][1] = baseXY[1] - sidehgt/2;
			ELN[2][0] = baseXY[0] + (int)(Ewidth*thickness/3);   ELN[2][1] = baseXY[1] + droop/4 - sidehgt/2;
			K[LOBE] = new GBezier(ELN, sharpN);
			
		}
		
		public void paintPart(Graphics g) {
			refix();
			if (female) {pRing(g, (K[LOBE].startPoint()[0] + K[LOBE].endPoint()[0])/2, (K[LOBE].startPoint()[1] + K[LOBE].endPoint()[1])/2, false, ringlen, ringwid);}
			GCombo shape = new GCombo(new GKata[] {K[EUPPER], K[EPOINT], K[EOUTER]});
			g.setColor(scol);
			g.fillPolygon(shape.getX(), shape.getY(), shape.length());
			g.setColor(dcol);
			paintNotches(g, K[EUPPER]);
			g.drawPolyline(K[RIDGE].getX(), Calc.offsetArray(K[RIDGE].getY(),1), K[RIDGE].length());
			g.drawPolyline(Calc.offsetArray(K[LOBE].getX(),1), K[LOBE].getY(), K[LOBE].length());
			g.setColor(new Color(0xff000000));
			g.drawPolyline(shape.getX(), shape.getY(), shape.length());
			g.drawPolyline(K[RIDGE].getX(), K[RIDGE].getY(), K[RIDGE].length());
			g.drawPolyline(K[LOBE].getX(), K[LOBE].getY(), K[LOBE].length());
			if (female) {pRing(g, (K[LOBE].startPoint()[0] + K[LOBE].endPoint()[0])/2, (K[LOBE].startPoint()[1] + K[LOBE].endPoint()[1])/2, true, ringlen, ringwid);}
		}
		public int[] getRightEdge() {return K[EPOINT].getPoint(1);}
	}
	
	class Cabeza extends Parte {
		public static final int FSIDE = 0;
		public static final int CRANIUM = 1;
		
		GKata HL; //not input
		protected Arrow lowArrow; //not input
		
		public Cabeza(int[] base) {
			K = new GKata[2];
			//baseXY = new int[] {base[0], base[1]};
			refix();
		}
		public void refix() {
			baseXY = partez[BESO].getBaseXY();
			int[] fEnd = partez[MIRO].getLeftEdge();
			int[] jEnd = partez[BESO].getJawArrow().getXY();
			int[][] FS = new int[3][2];
			int[][] CS = new int[4][2];
			int[][] HS = new int[4][2];
			FS[0][0] = baseXY[0];   FS[0][1] = baseXY[1];
			FS[1][0] = Math.min(baseXY[0]+5, fEnd[0]);   FS[1][1] = (fEnd[1]+baseXY[1])/2;
			FS[2][0] = fEnd[0];   FS[2][1] = fEnd[1];
			K[FSIDE] = new GBezier(FS, sharpN);
			CS[0][0] = fEnd[0];   CS[0][1] = fEnd[1];
			CS[1][0] = fEnd[0] + (jEnd[1]-fEnd[1])*4/3;   CS[1][1] = fEnd[1] - (jEnd[0]-fEnd[0])*4/3;
			CS[2][0] = jEnd[0] + (jEnd[1]-fEnd[1])*4/3;   CS[2][1] = jEnd[1] - (jEnd[0]-fEnd[0])*4/3;
			CS[3][0] = jEnd[0];   CS[3][1] = jEnd[1];
			K[CRANIUM] = new GBezier(CS, sharpN);
			if (female) {HS[0][0] = jEnd[0]; HS[0][1] = fEnd[1];}
			else {HS[0][0] = CS[3][0]*2/3 + CS[0][0]/3;   HS[0][1] = CS[3][1];}
			HS[1][0] = CS[3][0];   HS[1][1] = jEnd[1] - (jEnd[0]-fEnd[0])*2/3;
			HS[2][0] = fEnd[0] + (jEnd[1]-fEnd[1])*2/3;   HS[2][1] = fEnd[1] - (jEnd[0]-fEnd[0])*2/3;
			HS[3][0] = CS[0][0];   HS[3][1] = CS[0][1];
			HL = new GBezier(HS, sharpN);
			
			GCombo hairspace = new GCombo(new GKata[] {K[CRANIUM], HL});
			if (!female) {headhair.setHairs(hairspace, 4);}
			else {headhair.setHairs(hairspace, 3);}
		}
		
		public void paintPart(Graphics g) {
			refix();
			super.paintPart(g);
		}

		
	}
	
	class Hairstyle {
		int length;
		int thickness;
		int curl;
		int chaos;
		int ccwn; //num out of 10 that go counter clockwise
		int[][] hairses;
		private static final int lashlen = 8;
		public Hairstyle() {
			length = (female?5:0) + HAIRL;
			if (female) {length = length*2;}
			thickness = 1+HAIRW;
			curl = HAIRC;
			chaos = HAIRX;
			ccwn = HAIRS;
			hairses = new int[][] {{}};
			//System.out.println("chaos "+ chaos + " curl"+curl + " ccwn"+ccwn);
		}
		public Hairstyle(int l, int t, int c, int ch) {
			length = l; thickness = t; curl = c; chaos = ch;
		}
		public void plantone(Graphics g, int x, int y, int dir) {
			int rand1 = chaos/2 - randR()%chaos; incR(); //randInt(chaos);
			int rand2 = chaos/2 - randR()%chaos; incR();
			boolean rand3 = false; if((randR()%10)<ccwn) {rand3 = true;} incR();

			pHair(g, x, y, dir+rand1, curl+rand2, length, thickness, rand3);
		}
		public void plantlash(Graphics g, int x, int y, int dir, boolean ccw) {
			Color tmp = hcol;
			hcol = new Color(0xff000000);
			pHair(g, x, y, dir, curl, (int)Math.round(Math.abs(Math.cos(Math.toRadians(dir))) * lashlen), 1, ccw);	
			hcol = tmp;
		}
		public void setHairs(int[][] in) {
			//memory leak??
			hairses = in;
		}
		public void setLength(int x) {length = x;}
		public void setCurl(int x) {curl = x;}
		public void setThickness(int x) {thickness = x;}
		public void setChaos(int x) {chaos = x;}
		public void setCCWN(int x) {ccwn = x;}
		public int getLength() {return length;}
		public int getCurl() {return curl;}
		public int getThickness() {return thickness;}
		public int getChaos() {return chaos;}
		public int getCCWN() {return ccwn;}
		public int getR() {return hcol.getRed();}
		public int getG() {return hcol.getGreen();}
		public int getB() {return hcol.getBlue();}
		
		
		public void setHairs(GKata space, int f) {
			Polygon poly = new Polygon(space.getX(), space.getY(), space.length());
			Rectangle rect = poly.getBounds();
			int[][] send = new int[(rect.width/f)*(rect.height/f)][2];
			int k = 0;
			for (int x = rect.x + rect.width - 1; x >= rect.x; x-=f) {
				for (int y = rect.y; y < rect.y + rect.height; y+=f) {
					if(poly.contains(x, y)) {
						send[k][0] = x; send[k++][1] = y;
					}
				}
			}
			int[][] out = Calc.cropArray(send, 0, k);
			setHairs(out);
		}
		
		public void paintHairs(Graphics g, int[] c) {
			for (int i = 0; i < hairses.length; i++) {
				int d = (int) Math.round(Math.toDegrees(Math.atan((double)(c[1]-hairses[i][1])/(hairses[i][0]-c[0]))));
				if (hairses[i][0]-c[0] < 0) {d+=180;}
				headhair.plantone(g, hairses[i][0], hairses[i][1], d);
			}
			r = 0;
		}
		public void incR() {r = (r<10000 ? r+1 : 0);}
		public int randR() {return rands[r]+128;}
		
		
	}
	
	
	public void repaintA() {
		repaint();
	}
	public void redefine(Clan gob) {
    	this.loadAttributes(gob.FB);
    	hcol = new Color(155,155,155);
		
		dcol = scol.darker();
		hcol2 = hcol.darker();
    	headhair = new Hairstyle();
    	chinhair = new Hairstyle();
		partez = new Parte[5];
		partez[NASO] = new Nariz(new int[] {80, 200});
		partez[BESO] = new Boca(new int[] {80, 220});
		partez[MIRO] = new Ojo(new int[] {80, 200});
		partez[CARA] = new Cabeza(new int[] {140, 220});
		partez[JITA] = new Oreja(new int[] {140, 220});
    	partez[NASO].megafix();
    	if (gob.getGender() == Misc.MALE) {setFemale(false);} else {setFemale(true);}
    	
    	x = partez[NASO].getBaseXY()[0] - 60;
    	w = 180;
    	y = partez[NASO].getBaseXY()[1] - 60;
    	h = 180;
    	y -= (310 - partez[BESO].getGK(Boca.JAW).center()[1]) / 2;
    	loaded = true;
    	repaint();
	}
	
	public void setFemale(boolean fem) {female = fem;}

	public int getR() {return scol.getRed();}
	public int getG() {return scol.getGreen();}
	public int getB() {return scol.getBlue();}
    

}