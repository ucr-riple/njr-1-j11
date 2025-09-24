package AGraphics;

import java.awt.Color;
import java.awt.Graphics;

import Game.AGPmain;
import Ledger.Memo;

public class Imagery {
	private static final Color BROWN = new Color(80,40,0);
	private static final Color VGREEN = new Color(0,50,0);

	public static void drawArc(Graphics g, int cx, int cy, int dir, int curve, int len) {
		int theta = curve + dir - 90;
		int end = 2 * dir - theta;
		int R = (int) Math.round(len / (2 * dCos(90 - curve)));
		int w = (int) Math.round((1 - dCos(180-end)) * R);
		int h = (int) Math.round((1 - dSin(180-end)) * R);
		g.drawArc(cx - w, cy - h, 2*R, 2*R, 180+theta, end-theta-180);
	}
	
	public static void drawArc(Graphics g, int x, int y, int dir, int radius, int len, boolean CW, boolean Ledge) {
		int startAngle = (CW ? dir+90 : dir+270);
		double moreDegs = (double) (len * 360) / (Math.PI * 2 * radius);
		int arcAngle = (int) Math.round((CW ? -moreDegs : moreDegs));
		int xx = (int) Math.round(x - (dCos(startAngle) * radius) - radius);
		int yy = (int) Math.round(y + (dSin(startAngle) * radius) - radius);
		g.drawArc(xx, yy, 2*radius, 2*radius, startAngle, arcAngle);
		if(Ledge) {
			Memo.add((int)Math.round(xx + dCos(startAngle + arcAngle) * radius + radius), 0);
			Memo.add((int)Math.round(yy - dSin(startAngle + arcAngle) * radius + radius), 1);
			Memo.add(-1, 2);
		}
	}
	public static void drawSTree(Graphics g, int x, int y, int len, Color color) {
		int dir = (int) Math.round(60 + AGPmain.rand.nextInt(61));
		g.setColor(color);
		drawArc(g,x,y,dir,3*len,len,AGPmain.rand.nextBoolean(),true);
	}
	public static void drawCTree(Graphics g, int x, int y, int len, boolean color) {
		int dir = color?(int) Math.round(60 + AGPmain.rand.nextInt(61)):85;
		if(color) {g.setColor(BROWN);}
		drawArc(g,x,y,dir,3*len,len,color?AGPmain.rand.nextBoolean():true,true);
		int tx = Ledger.Memo.take(0);  int ty = Ledger.Memo.take(1);    len = len/3;
		if(color) {g.setColor(VGREEN);}
		drawArc(g,tx,ty,dir+75,3*len,len,false,false);
		drawArc(g,tx,ty,dir+105,3*len,len,false,false);
		drawArc(g,tx,ty,dir-75,3*len,len,true,false);
		drawArc(g,tx,ty,dir-105,3*len,len,true,false);
		drawBush(g,x,y,2*len);
	}
	public static void drawCTreeFromTopRight(Graphics g, int x, int y, int len) {
		drawCTree(g, x + len/2, y + len*3/4, len/2, false);
	}
	public static void drawBush(Graphics g, int x, int y, int size) {
		g.setColor(VGREEN);
		drawArc(g,x-size/4,y,100,size,size,false,false);
		drawArc(g,x-size/2,y,120,size,size,false,false);
		drawArc(g,x+size/4,y,80,size,size,true,false);
		drawArc(g,x+size/2,y,60,size,size,true,false);
	}
	/** slant from 0-89 */
	public static void drawEye(Graphics g, int xx, int yy, int radius) {
		int w = 2 * radius;   int x = xx + w;   int y = yy + w / 2;
		drawArc(g,x,y,0,45,w);
		drawArc(g,x-w,y,180,45,w);
		drawArc(g,x,y,0,65,w);
		drawArc(g,x-w,y,180,65,w);
		g.drawOval(x+w*2/5-w, y-w/10, w/5, w/5);
		g.drawOval(x+w*2/5-w+1, y-w/10, w/5, w/5);
	}
	public static void drawStar(Graphics g, int xx, int yy, int radius) {
		int[] X = new int[5];   int[] Y = new int[5];
		int flw = (int) Math.round(radius * dCos(18));
		int flh = (int) Math.round(radius * dSin(18));
		int dnw = -(int) Math.round(radius * dCos(234));
		int dnh = -(int) Math.round(radius * dSin(234));
		X[0] = xx + radius - flw;   Y[0] = yy + radius - flh;
		X[1] = xx + radius + flw;   Y[1] = Y[0];
		X[2] = xx + radius - dnw;   Y[2] = yy + radius + dnh;
		X[3] = xx + radius;   Y[3] = yy;
		X[4] = xx + radius + dnw;   Y[4] = Y[2];
		g.drawPolygon(X, Y, 5);
	}
	public static void drawMemSign(Graphics g, int xx, int yy, int radius) {
		g.drawOval(xx, yy, radius*2, radius*2);
		drawArc(g, xx + radius-1, yy + radius, 270, 45, radius+1);
		drawArc(g, xx + radius+1, yy + radius, 90, 45, radius+1);
	}
	public static void drawInfoSign(Graphics g, int xx, int yy, int radius) {
		int yind = radius / 2;   int top = yy + yind * 3/2;   int w = radius / 3;
		g.fillOval(xx + radius - yind / 3, yy + yind / 2, yind / 2, yind / 2);
		g.drawLine(xx + radius - w, top, xx + radius, top);
		g.drawLine(xx + radius, top, xx + radius, yy + radius*2 - yind / 2);
		g.drawLine(xx + radius - w, yy + radius*2 - yind / 2, xx + radius + w, yy + radius*2 - yind / 2);

	}
	public static void drawFlag(Graphics g, int xx, int yy, int radius) {
		int top = radius / 5;   int base = xx + radius * 4 / 5;
		g.drawLine(base, yy + top, base, yy + radius*2 - top);
		g.drawLine(base, yy + top, xx + 3 * radius / 2, yy + (top + radius) / 2);
		g.drawLine(xx + 3 * radius / 2, yy + (top + radius) / 2, base, yy + radius);

	}
	public static void drawStickFigure(Graphics g, int xx, int yy, int radius) {
		int yind = radius / 2;   int top = yy + yind * 3/2;   int w = radius / 3;
		g.drawOval(xx + radius - yind / 2, yy + yind / 2, yind, yind);
		g.drawLine(xx + radius - w, top+1, xx + radius + w, top+1);
		g.drawLine(xx + radius, top, xx + radius, yy + radius*5/4);
		g.drawLine(xx + radius - w, yy + radius*2 - yind / 2, xx + radius, yy + radius*5/4);
		g.drawLine(xx + radius + w, yy + radius*2 - yind / 2, xx + radius, yy + radius*5/4);

	}
	public static void drawMilletSign(Graphics g, int xx, int yy, int radius) {
		int ind = radius / 3;
		int[] X = {xx + ind, xx + ind, xx + radius, xx + 2*radius - ind, xx + 2*radius - ind};
		int[] Y = {yy + 2*radius - ind, yy + ind, yy + radius + ind, yy + ind, yy + 2*radius - ind};
		g.drawPolyline(X, Y, X.length);
		g.drawLine(xx+ind/2, yy+radius, xx+2*radius-ind/2, yy+radius);
	}
	public static void drawPyramid(Graphics g, int xx, int yy, int radius) {
		int border = radius / 5;
		int[] X = {xx + border, xx + radius, xx + 2*radius - border};
		int[] Y = {yy + 2*radius - border, yy + border, yy + 2*radius - border};
		g.drawPolygon(X, Y, X.length);
	}
	
	
	
	private static double dSin(int d) {return Math.sin(Math.toRadians(d));}
	private static double dCos(int d) {return Math.cos(Math.toRadians(d));}
}
