package Questing.Knowledge;

import java.util.*;

import Game.AGPmain;
import Sentiens.Clan;

/**
 * for storage in library
 */
public abstract class KnowledgeBlock<T> {
	protected Object[] x;
	protected int[] y;
	protected int obsUsed, date;
	private final Clan discoverer;
	protected final KnowledgeType kType;
	
	public KnowledgeBlock(Clan clan, KnowledgeType kType) {discoverer = clan; this.kType = kType;}
	
	public Object[] getXs() {return x;}
	public int[] getYs() {return y;}
	public int getNumObservationsUsed() {return obsUsed;}
	public int getDateRecorded() {return date;}
	public Clan getDiscoverer() {return discoverer;}
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < x.length; i++) {
			final T o = (T)x[i];
			if (o == null) {break;}
			s += descO(o) + ":" + y[i] + "; ";
		}
		return s + " at day " + date + " by " + discoverer;
	}
	protected String descO(T o) {return o.toString();}
	public void useKnowledge(Clan user, boolean doAlterBrain) {
		if (doAlterBrain && user != AGPmain.getAvatar()) {alterBrain(user);} // should it not be automatic?
		if (discoverer != null) discoverer.incKnowledgeAttribution();
	}
	public void useKnowledge(Clan user) {
		useKnowledge(user, true);
	}
	protected abstract void alterBrain(Clan user);
	public boolean isApplicableFor(KnowledgeType k) {return k == relK();}
	public abstract KnowledgeType relK();
}
/** finds top three x Objects ordered by y Score */
abstract class Top3Block<T> extends KnowledgeBlock<T> {
	public Top3Block(Clan clan, KnowledgeType type, Map<T, Integer> map) {
		this(clan, type, mapToPlot(map));
	}
	public Top3Block(Clan clan, KnowledgeType type, List<DataPoint<T>> plot) {
		super(clan, type);
		int gold = Integer.MIN_VALUE, silver = Integer.MIN_VALUE, bronze = Integer.MIN_VALUE;
		T goldO = null, silverO = null, bronzeV = null;
		for (DataPoint<T> dp : plot) {
			final T v = dp.x; final int i = dp.y;
			if (i > gold) {bronze = silver; bronzeV = silverO; silver = gold; silverO = goldO; gold = i; goldO = v;}
			else if (i > silver) {bronze = silver; bronzeV = silverO; silver = i; silverO = v;}
			else if (i > bronze) {bronze = i; bronzeV = v;}
		}
		x = new Object[] {goldO, silverO, bronzeV};
		y = new int[] {gold, silver, bronze};
		obsUsed = plot.size(); date = AGPmain.TheRealm.getDay();
	}
	@Override
	public KnowledgeType relK() {
		return kType;
	}
	private static <T> List<DataPoint<T>> mapToPlot(Map<T, Integer> map) {
		List<DataPoint<T>> result = new ArrayList<DataPoint<T>>();
		for (Map.Entry<T, Integer> entry : map.entrySet()) {
			result.add(new DataPoint<T>(entry.getKey(), entry.getValue()));
		}
		return result;
	}
}