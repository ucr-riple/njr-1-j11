package Ideology;

import Defs.*;
import Game.Ministry;
import Sentiens.Clan;

public class ToDoValue implements Value {
	private int ordinal;
	public ToDoValue() {ordinal = Values.ord++;System.out.println("DONT..");}
	@Override
	public double compare(Clan POV, Clan A, Clan B) {return 0;}
	@Override
	public String description(Clan POV) {return "Value yet undefined";}
	@Override
	public Q_ pursuit() {return Q_.NOTHING;}
	@Override
	public Ministry getMinistry() {return null;}
	@Override
	public P_[] getRelevantSkills() {return new P_[] {};}
	@Override
	public int ordinal() {return ordinal;}
}