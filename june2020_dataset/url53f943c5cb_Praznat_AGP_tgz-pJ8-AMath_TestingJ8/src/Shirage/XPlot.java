package Shirage;

import java.awt.Graphics;

public class XPlot extends Plot {
	public XPlot(double v) {super(v);}
	public boolean isNull() {return true;}
	public void drawPlot(Graphics g) {}
	public double getHoodAvgVal() {return 0;}
	public double getEV() {return 0;}
	public void flow() {}
	public double getRBM() {return 0;}
	public double evaporate(double rate) {
		double ev = getWVal();
		chgW(-ev);   return ev;
	}
	public boolean isOcean() {return false;}
	@Override
	public Shire getLinkedShire() {return null;}
}
