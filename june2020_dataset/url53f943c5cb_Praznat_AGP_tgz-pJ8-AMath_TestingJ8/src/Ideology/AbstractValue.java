package Ideology;

import Defs.*;
import Game.Ministry;
import Sentiens.Clan;

public abstract class AbstractValue implements Value {
	protected final String desc;
	protected final Q_ quest;
	protected final Ministry ministry;
	protected final P_[] relSkills;
	private int ordinal;
	public AbstractValue(String d, Q_ q, Ministry j, P_[] rs) {
		desc = d;
		quest = q;
		ordinal = Values.ord++;
		ministry = j;
		relSkills = rs;
	}
	@Override
	public String description(Clan POV) {return desc;}
	@Override
	public String toString() {return description(null);}
	@Override
	public Q_ pursuit() {return (quest != null ? quest : Q_.NOTHING);}
	@Override
	public Ministry getMinistry() {return ministry;}
	@Override
	public P_[] getRelevantSkills() {return relSkills;}
	@Override
	public int ordinal() {return ordinal;}
}