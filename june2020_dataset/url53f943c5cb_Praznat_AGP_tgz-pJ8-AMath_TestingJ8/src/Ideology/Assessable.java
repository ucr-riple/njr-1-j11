package Ideology;

import Sentiens.Clan;

public interface Assessable {
	public double evaluate(Clan evaluator, Clan proposer, int content);
}
