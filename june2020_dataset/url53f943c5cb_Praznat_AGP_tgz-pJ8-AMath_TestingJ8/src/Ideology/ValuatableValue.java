package Ideology;

import Defs.*;
import Game.Ministry;
import Sentiens.Clan;

public abstract class ValuatableValue extends AbstractValue implements Assessable {
	public ValuatableValue(String d, Q_ q, Ministry j, P_[] rs) {super(d, q, j, rs);}
	@Override
	public double compare(Clan POV, Clan A, Clan B) {
		return compare(value(POV, A), value(POV, B));
	}
	public double compare(double A, double B) {return Values.logComp(A, B);}
	protected double evaluateContent(Clan evaluator, Clan proposer, int content, double curval) {return content + curval;}
	/** returns comparison between current value and proposed value, times weight of value to clan */
	@Override
	public final double evaluate(Clan evaluator, Clan proposer, int content) {
		double curVal = value(evaluator, evaluator);
		return evaluator.FB.weightOfValue(this) * compare(evaluateContent(evaluator, proposer, content, curVal), curVal);
	}
	public abstract int value(Clan POV, Clan clan);
}
