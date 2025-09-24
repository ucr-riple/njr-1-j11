package Markets;

import Descriptions.XWeapon;
import Game.*;
import Sentiens.Clan;

public class EntryB extends Entry {
	public short des;
	
	public EntryB() {super(); des = XWeapon.NULL;}
	public void set(int p, Clan t, short w) {px = p; trader = t; des = w;}
	public void set(Entry e) {px = e.px; trader = e.trader; des = ((EntryB)e).des;}
	public void setDes(byte w) {des = w;}
}