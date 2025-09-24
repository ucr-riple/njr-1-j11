package Questing.Knowledge;

import AMath.Calc;
import Avatar.SubjectiveType;
import Defs.*;
import Game.Job;
import Government.Order;
import Ideology.*;
import Questing.*;
import Questing.Quest.PatronedQuest;
import Questing.Quest.PatronedQuestFactory;
import Sentiens.Clan;
import Shirage.Shire;

public class KnowledgeQuests {
	public static PatronedQuestFactory getMinistryFactory() {return new PatronedQuestFactory(KnowledgeQuest.class) {public Quest createFor(Clan c, Clan p) {return new KnowledgeQuest(c, p);}};}
	
	static G_[] equip = {G_.ARMOR, G_.SWORD, G_.MACE, G_.BOW, G_.GUN, G_.LOBODONKEY};
	
	public static class KnowledgeQuest extends PatronedQuest {
		private final KnowledgeType kToStudy;
		public KnowledgeQuest(Clan P, Clan patron) {super(P, patron); kToStudy = valToK(patron);}
		public KnowledgeQuest(Clan P, Clan patron, KnowledgeType k) {super(P, patron); kToStudy = k;}

		@Override
		public void pursue() {
			pursueKnowledge(kToStudy);
		}
		
		@Override
		public void avatarPursue() {
			avatarConsole().showChoices("Choose quest", Me, K_.values(), SubjectiveType.NO_ORDER, new Calc.Listener() {
				@Override
				public void call(Object arg) {
					pursueKnowledge((K_) arg);
				}
			}, new Calc.Transformer<KnowledgeType, String>() {
				@Override
				public String transform(KnowledgeType k) {
					return "Study " + k;
				}
			});
		}
		@SuppressWarnings("rawtypes")
		void useKnowledgeBlock(KnowledgeBlock kb) {
			kb.useKnowledge(patron);
			finish();
		}
		@SuppressWarnings("rawtypes")
		public void pursueKnowledge(KnowledgeType k) {
			final KnowledgeBlock kb = patron.getRelevantLibrary().findKnowledge(k);
			if (kb != null) {useKnowledgeBlock(kb); return;}
			final Quest newQuest = kToKnowledgeQuest(k);
			if (newQuest == null) {finish(); return;} // eventually should never happen
			Me.MB.newQ(newQuest);
		}
		private KnowledgeType valToK(Clan patron) {
			final Value v = patron.FB.randomValueInPriority();
			if (v == Values.WEALTH) return K_.JOBS;
			else if (v == Values.INFLUENCE) return K_.POPVALS;
			else if (v == Values.MIGHT) { // TODO if patron = student, just pick random one
				int mins = patron.getMinionN();
				if (mins == 0) return K_.WEAPONMEMS;
				int subMins = patron.getSubminionN();
				if (subMins == 0) return K_.TACTICSMEMS;
				int rank = patron.distanceFromTopBoss();
				if (rank <= 1) return K_.STRATEGYMEMS;
				// more subminions vs minions means more likely to do tactics vs weapons
				return Calc.pFraction(subMins, subMins + mins) ? K_.TACTICSMEMS : K_.WEAPONMEMS;
			}
			else return K_.NADA;
		}
		private Quest kToKnowledgeQuest(KnowledgeType kt) {
			if (K_.class.isAssignableFrom(kt.getClass())) {
				K_ k = (K_) kt;
				switch(k) {
				case JOBS: return new ObservationQuest<Clan, Job>(Me, patron, new JobObserver(), TargetQuest.getReasonableCandidates(patron), 5);
				case WEAPONMEMS: 
					final Order myOrder = patron.myOrder();
					return new ObservationQuest<Order, G_>(Me, patron, new WeaponObserver(), myOrder != null ? patron.myOrder().getRivalOrders() : new Order[] {}, 1);
				case POPVALS: return new ObservationQuest<Clan, Value>(Me, patron, new ValueObserver(), TargetQuest.getReasonableCandidates(patron), 3);
				}
			}
			if (SK_.class.isAssignableFrom(kt.getClass())) {
				return new ExplorationQuest(Me, patron, new ShireExplorer((SK_)kt, Me), patron.myShire().getNeighborRoute(true, 8), 1);
			}
			return null;
		}
		@Override
		public String description() {return "Pursue knowledge";}
	}
	
	// Knowledge :
	// Spirituality : find out what prayStyles and "nature" memes correlate to high holiness levels
	// Freedom :
	// Beauty : find out what values ppl tend to have in shire / order?
	// Wealth : find out what jobs and "business" memes correlate with NAV / earningsMA
	// Expertise :
	// Allegiance : find out what values ppl tend to have in shire / order
	// Influence : find out what values ppl tend to have in shire / order?
	// Righteousness : find out what "nature" memes correlate to low sin levels
	// Might : find out what "war" memes correlate with BATTLEP
	// Legacy : "normalize" other people's legacies
	// Comfort :
	
	
	@SuppressWarnings("rawtypes")
	static Calc.ThreeObjects<Shire, Object, Integer> bestInShires(
			Clan clan, KnowledgeType kType, double threshold, boolean hiOrLo, boolean areaOrPath, boolean stopAtNoLibrary) {

		final int numShiresToLookAt = clan.FB.getBeh(M_.PATIENCE) / 3 + 2; // one for myshire
		Shire[] shires = areaOrPath ? clan.myShire().getNeighbors(true) : clan.myShire().getNeighborRoute(true, numShiresToLookAt);
		Shire lastShire = null; Shire bestShire = null;
		double best = threshold; Object bestO = null;
		int min = Math.min(numShiresToLookAt, shires.length);
		for (int i = 0; i < min; i++) {
			final Shire newShire = lastShire == null ? clan.myShire() : shires[i];
			if (newShire == null) {continue;} // edge
			final KnowledgeBlock kb = newShire.getLibrary().findKnowledge(kType);
			if (kb == null) {if (stopAtNoLibrary) {break;} else {continue;}}
			int newPay = kb.getYs()[0];
			int n = hiOrLo ? 1 : -1;
			if (n*newPay > n*best) {
				best = newPay; bestO = kb.getXs()[0]; bestShire = newShire;
			}
		}
		return bestO != null ? new Calc.ThreeObjects<Shire, Object, Integer>(bestShire, bestO, (int)best) : null;
	}
	
}
