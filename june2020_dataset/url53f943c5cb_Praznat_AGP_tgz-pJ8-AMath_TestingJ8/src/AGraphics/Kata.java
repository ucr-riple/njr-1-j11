package AGraphics;

import java.awt.Graphics;


public class Kata {
	int[][] XY;

	public int[][] getXY() {return XY;}
	public int[] getX() {return XY[0];}
	public int[] getY() {return XY[1];}
	public int getX(int p) {return XY[0][p];}
	public int getY(int p) {return XY[1][p];}
	public int[] getStart() {return new int[] {XY[0][0], XY[1][0]};}
	public int[] getEnd() {return new int[] {XY[0][XY[0].length-1], XY[1][XY[0].length-1]};}
	public int[] startPoint() {return getStart();} //from Bezier
	public int[] endPoint() {return getEnd();} //from Bezier
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
	

	
	
	
}