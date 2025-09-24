package Markets;

import Sentiens.Clan;

public class Entry {
	public static final int BIDDIR = -1;
	public static final int OFFERDIR = 1;
	public static final int AUCTION = 0;
	public int px;
	public Clan trader;
	public Entry() {px = -1; trader = null;}
	public void set(int p, Clan t) {px = p; trader = t;}
	public void set(Entry e) {px = e.px; trader = e.trader;}
	private void setPX(int p) {px = p;}
	public void setTrader(Clan t) {trader = t;}
	/** get absolute value posted price */
	public int getPX() {return (px != Integer.MIN_VALUE ? Math.abs(px) : 0);}
	public void makeRented() {setPX(px != 0 ? -Math.abs(px) : Integer.MIN_VALUE);}
	public void makeUnrented() {setPX(px != Integer.MIN_VALUE ? Math.abs(px) : 0);}
	public boolean isRented() {return px < 0;}
	
	@Override
	public String toString() {return (trader == null ? "none" : trader.toString() + "@" + px);}
}