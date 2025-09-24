package Ideology;



import java.util.*;

import AMath.ArrayUtils;
import Defs.*;
import Game.Job;
import Questing.ExpertiseQuests;
import Sentiens.*;
import Sentiens.Law.PersonalCommandment;

public class Values {
	
	static int ord = 0;

	public static final Value MIGHT = new ValuatableValue("Might", Q_.PICKFIGHT, Job.GENERAL,
			new P_[] {P_.COMBAT, P_.MARKSMANSHIP, P_.STRENGTH}) {
		@Override
		public int value(Clan POV, Clan clan) {
			return clan.FB.getPrs(P_.BATTLEP);
		}
	};

	public static final Value WEALTH = new ValuatableValue("Wealth", Q_.BUILDWEALTH, Job.TREASURER,
			new P_[] {P_.CARPENTRY, P_.SMITHING, P_.MASONRY, P_.ARTISTRY, P_.LOBOTOMY}) {
		@Override
		public int value(Clan POV, Clan clan) {
			return (int) Math.min(clan.getNetAssetValue(POV), Integer.MAX_VALUE/2);
		}
	};
	
	public static final Value INFLUENCE = new ValuatableValue("Influence", Q_.BUILDPOPULARITY, Job.VIZIER, new P_[] {}) {
		@Override
		public int value(Clan POV, Clan clan) {
			//minus my minions if hes my boss.. so i can judge myself against my bosses
			return clan.getMinionTotal() - (clan.isSomeBossOf(POV) ? POV.getMinionTotal() : 0);
		} // * P_.RSPCT ??
	};

	//TODO VIRTUE "Honor - Virtue"

	public static final Value RIGHTEOUSNESS = new ValuatableValue("Righteousness", Q_.CREEDQUEST, Job.JUDGE, new P_[] {}) {
		@Override
		public double compare(double A, double B) {return logCompNeg(A, B);}
		@Override
		public int value(Clan POV, Clan clan) {   //maybe add human sacrifice?
			int sins = 0;
			PersonalCommandment[] myCommandments = POV.FB.commandments;
			PersonalCommandment[] hisCommandments = clan.FB.commandments;
			for (int i = 0; i < myCommandments.length; i++) {
				if (myCommandments[i].isSinful()) {sins += hisCommandments[i].getTransgressions();}
			}
			return -sins;
		}
		/** how sinful proposer views evaluator versus how sinful evaluator views himself */
		@Override
		protected double evaluateContent(Clan evaluator, Clan proposer, int content, double curval) {
			return value(proposer, evaluator);
		}
	};
	
	public static final Value ALLEGIANCE = new ValuatableValue("", Q_.LOYALTYQUEST, Job.NOBLE, new P_[] {}) {
		@Override
		public String description(Clan POV) {return "Allegiance" + (POV != null && POV != POV.getBoss() ? " to " + POV.getBoss() : "");}
		@Override
		public double compare(double A, double B) {return logCompNeg(A, B);}
		@Override
		public int value(Clan POV, Clan clan) { // how far from same Order boss are you
			return -(clan.myOrder() == POV.myOrder() ? clan.distanceFromTopBoss() : Integer.MAX_VALUE/2);
		}
		@Override
		protected double evaluateContent(Clan evaluator, Clan proposer, int content, double curval) {
			return value(evaluator, proposer); // -1 because evaluator would go under proposer?
		}
	};
	
	public static final Value LEGACY = new ValuatableValue("Legacy", Q_.LEGACYQUEST, Job.HISTORIAN, new P_[] {}) {
		@Override
		public int value(Clan POV, Clan clan) {
			return clan.LB.getLegacyFor(POV.FB.randomValueInPriority());
		}
	};
	
	public static final Value BEAUTY = new ValuatableValue("Beauty", Q_.SPLENDORQUEST, Job.ARCHITECT,
			new P_[] {P_.MASONRY, P_.ARTISTRY}) {
		@Override
		public int value(Clan POV, Clan clan) {
			int result = clan.FB.getFac(F_.NOSELX) + clan.FB.getFac(F_.NOSERX);
			result += clan.FB.getFac(F_.EYELW);
			result -= 2 * Math.abs(7 - clan.FB.getFac(F_.MOUTHJW));
			result -= Math.abs(25 - clan.getAge()); // age
			result += Math.abs(clan.FB.getBeh(M_.OCD) - clan.FB.getFac(F_.HAIRL));
			result += Math.min(15, clan.getAssets(Misc.jewelry)); // no need for more than 15 jewelry
			result += 10 * clan.getSplendor();
			return result;
		}
	};
	public static final Value COPULATION = new ValuatableValue("Copulation", Q_.BREED, Job.COURTESAN, new P_[] {}) {
		@Override
		public int value(Clan POV, Clan clan) {
			return clan.getNumOffspring();
		}
	};
	public static final Value HARMONY = new ValuatableValue("Harmony", Q_.BREED, Job.APOTHECARY, new P_[] {}) {
		@Override
		public double compare(double A, double B) {return logCompNeg(A, B);}
		@Override
		public int value(Clan POV, Clan clan) {
			return (int) Math.round(clan.AB.getStressLevel() * 100);
		}
	};
	
	
	public static final Value EXPERTISE = new ValuatableValue("Expertise", Q_.TRAIN, Job.TUTOR, new P_[] {}) {
		@Override
		public int value(Clan POV, Clan clan) {
			double sum = 0;   for (P_ s : ExpertiseQuests.ALLSKILLS) {sum += clan.FB.getPrs(s);}
			return (int) Math.round(sum / ExpertiseQuests.ALLSKILLS.length);
		}
	};
	public static final Value KNOWLEDGE = new ValuatableValue("Knowledge", Q_.KNOWLEDGEQUEST, Job.PHILOSOPHER, new P_[] {P_.ARITHMETIC}) {
		@Override
		public int value(Clan POV, Clan clan) {   //maybe add human sacrifice?
			//TODO
			return clan.getKnowledgeAttribution();
		}
	};
	public static final Value SPIRITUALITY = new ValuatableValue("Spirituality", Q_.FAITHQUEST, Job.SORCEROR, new P_[] {}) {
		@Override
		public int value(Clan POV, Clan clan) {   //maybe add human sacrifice?
			return clan.getHoliness();
		}
	};

	
	
	
	
	
	

	private static final Value[] AllValues = new Value[] {
		WEALTH, INFLUENCE, MIGHT, BEAUTY, HARMONY, COPULATION,
		ALLEGIANCE, RIGHTEOUSNESS, LEGACY, KNOWLEDGE, SPIRITUALITY, EXPERTISE
	};
	private static Value[] filterTodos(Value[] varray) {
		Set<Value> set = new HashSet<Value>();
		for (Value v : varray) {if (!(v instanceof ToDoValue)) {set.add(v);}}
		Value[] result = new Value[set.size()];
		int i = 0; for(Value v : set) {result[i++] = v;}
		return result;
	}
	
	public static Value[] All = ArrayUtils.shuffle(Value.class, filterTodos(AllValues)); //final
//	public static final Value[] All = ArrayUtils.orderByComparator(Value.class, AllValues, new Comparator<Value>() {
//		@Override
//		public int compare(Value v1, Value v2) {return (int) Math.signum(v1.ordinal() - v2.ordinal());}
//	});
	
	public static final int MAXVAL = 10;
	public static final int MINVAL = -MAXVAL;

//	private static int countLand(Clan clan) {return 0;}
//	private static int countAnimals(Clan clan) {return 0;}
//	private static int countVassals(Clan clan) {return 0;}
//	private static int countWeapons(Clan clan) {return 0;}  //LOBODONKEY IS WEAPON!
	
	public static double logComp(int a, int b) {
		return logComp((double) a, (double) b);
	}
	public static double logComp(final double a, final double b) {
		return Math.min(Math.max(Math.log((a+1) / (b+1)), MINVAL), MAXVAL);
	}
	public static double logCompNeg(final double a, final double b) {
		return Math.min(Math.max(Math.log((b-1) / (a-1)), MINVAL), MAXVAL);
	}
	public static double fourbitComp(int a, int b) {
		return ((double) (a - b) * MAXVAL) / (double) 15;
	}

//	public static double inIsolation(double in, Value value, Clan POV) {
//		return in * (double)value.getWeighting(POV) / (double)((CompoundValue)VALUE_SYSTEM).sumWeights(POV);
//	}
	
	
	

}
