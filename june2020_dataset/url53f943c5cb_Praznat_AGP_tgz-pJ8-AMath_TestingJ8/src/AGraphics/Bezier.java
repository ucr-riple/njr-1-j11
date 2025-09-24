package AGraphics;

import java.awt.Polygon;

import AMath.Calc;

public class Bezier extends Kata {
	private int[][] points;
	private int intervals;
	public Bezier(int[][] pts, int N) {
		points = pts;
		intervals = N;
		map();
	}
	public Bezier(int[] pt1, double slope1, int[] pt2, double slope2, int N) {
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
	public Bezier (Arrow a1, Arrow a2, double F, int N) {
		points = new int[4][2];
		points[0] = a1.getXY();   points[3] = a2.getXY();
		double dist = Calc.distance(points[0], points[3]);
		int[] mp1 = {points[0][0] - (points[3][1]-points[0][1]), points[0][1] + (points[3][0]-points[0][0])};
		int[] mp2 = {points[3][0] - (points[3][1]-points[0][1]), points[3][1] + (points[3][0]-points[0][0])};
		double sidedist1 = Calc.distance(points[3], mp1);
		double sidedist2 = Calc.distance(points[0], mp2);
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
		if(points.length-1==0){return;}
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
	public void setPoints(int[][] xy) {
		points = xy;
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
	public void fractalize(double[] F, double mult) {
		for (int i = 0; i < XY[1].length; i++) {
			XY[1][i] = Math.min(XY[1][0], (int) (XY[1][i] + mult * (F[2*i] + F[2*i+1]) / 2));
		}
	}
	public void fractalize(double[] F, double mult, int radX, int radY) {
		double min = Calc.max(F);
		for (int i = XY[1].length - 2; i > 0; i--) {
			XY[0][i] += mult * (XY[0][i] - radX) * ((F[2*i] + F[2*i+1]) / 2 + min);
			XY[1][i] += mult * (XY[1][i] - radY) * ((F[2*i] + F[2*i+1]) / 2 + min);
			
		}
	}
	public static Polygon BCombo(Bezier[] GK) {
		int length = 0;
		int p = 0;
		for (int k = 0; k < GK.length; k++) {
			length+=GK[k].length();
		}
		int[][] XY = new int[2][length];
		for (int i = 0; i < GK[0].length(); i++) {
			XY[0][i] = GK[0].getX(i); XY[1][i] = GK[0].getY(i);
		}
		for (int k = 1; k < GK.length; k++) {
			p += GK[k-1].length();
			for (int i = p; i < p + GK[k].length(); i++) {
				XY[0][i] = GK[k].getX(i-p); XY[1][i] = GK[k].getY(i-p);
			}
		}
		return new Polygon(XY[0], XY[1], XY[0].length);
	}
}

