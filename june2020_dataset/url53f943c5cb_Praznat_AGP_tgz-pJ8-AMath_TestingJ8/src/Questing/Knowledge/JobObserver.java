package Questing.Knowledge;

import java.util.*;

import AMath.Calc;
import Defs.K_;
import Game.Job;
import Questing.ImmigrationQuests;
import Sentiens.Clan;
import Shirage.Shire;

public class JobObserver extends MapKnowledgeObserver<Clan, Job> {
	private final Map<Job, int[]> map2 = new HashMap<Job, int[]>();
	
	/** 
	 * observes the job and average income of one clan
	 */
	@Override
	public void observe(Clan c) {
		final Job j = c.getJob();
		final int x = (int)Math.round(c.getAvgIncome()); // NAV ?
		int[] sumAndCount = map2.get(j);
		if (sumAndCount == null) map2.put(j, sumAndCount = new int[] {x, 1});
		else {sumAndCount[0] += x; sumAndCount[1]++;}
		map.put(j, (int) Math.round((double)sumAndCount[0] / sumAndCount[1]));
	}
	@Override
	public KnowledgeBlock<Job> createKnowledgeBlock(Clan creator) {
		return new Top3Block<Job>(creator, K_.JOBS, map) {
			@Override
			protected void alterBrain(Clan user) {
				// this means neighboring shires' KBs wont get "used" (no wisdom attribution to discoverer) IS THIS RIGHT?
				// probably? if they all get used, that means this is way more likely to get used than other types of KB
				// so u would always want to go around researching jobs in other shires rather than anything else to up ur attribution...
				Calc.ThreeObjects<Shire, Object, Integer> bestO = KnowledgeQuests.bestInShires(user, relK(), user.getAvgIncome(), true, true, true);
				if (bestO != null) {
					user.setJob((Job)bestO.get2nd());
					Shire newShire = bestO.get1st();
					if (newShire != user.myShire()) {user.MB.newQ(new ImmigrationQuests.EmigrateQuest(user, newShire));}
				}
			}
		};
	}
}
