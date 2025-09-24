package Ideology;

import Defs.*;
import Game.Ministry;
import Sentiens.Clan;
import Sentiens.Stress.Blameable;

public interface Value extends Blameable {
	/** returns difference in prestige in this Value between A & B in eyes of POV */
	public double compare(Clan POV, Clan A, Clan B);
	public String description(Clan POV);
	public Q_ pursuit();
	public Ministry getMinistry();
	public P_[] getRelevantSkills();
//	public double contentBuyable(Clan assessor, int millet);
	public int ordinal();
}