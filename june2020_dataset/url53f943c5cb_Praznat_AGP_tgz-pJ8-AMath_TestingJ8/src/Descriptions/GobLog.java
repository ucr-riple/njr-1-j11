package Descriptions;

import java.util.*;

import AMath.Calc;
import Defs.P_;
import Game.*;
import Ideology.Value;
import Questing.FaithQuests.ActOfFaith;
import Questing.*;
import Questing.Knowledge.KnowledgeBlock;
import Sentiens.*;
import Sentiens.Law.PersonalCommandment;
import Shirage.Shire;

public class GobLog {
	
	/**
	 * will not be persistable in this format!
	 * @author alexanderbraylan
	 *
	 */
	public static abstract class Reportable {
		private final int date;
		private Reportable(boolean undated) {date = -1;};
		private Reportable() {date = AGPmain.TheRealm.getDay();};
		public abstract String toString();
		public int getDate() {return date;}
	}
	
	public static class Book {
		private static final int LENGTH = 50;
		private Reportable[] book = new Reportable[LENGTH];
		public Book() {for(int i = 0; i < book.length; i++) {book[i] = blank();}}
		public void addReport(Reportable R) {for (int i = 0; i < book.length-1; i++) {book[i] = book[i+1];} book[book.length-1] = R;}
		public Reportable[] getBook() {return book;}
		@Override
		public String toString() {
			String out = "";
			for (Reportable r : book) {out += r.toString() + ", ";}
			return out;
		}
	}
	
	private static Reportable blank() {
		return new Reportable(true) {
			public String toString() {return "";}
		};
	}

	public static Reportable idle() {
		return new Reportable() {
			public String toString() {return "Idled";}
		};
	}
	
	public static Reportable beginQuest(final Quest q) {
		return new Reportable() {
			public String toString() {return "Began " + q;}
		};
	}
	public static Reportable endQuest(final Quest q) {
		return new Reportable() {
			public String toString() {return "Finished " + q;}
		};
	}

//	public static Reportable buySellFair(final int g, final int px, final boolean buyNotSell) {
//		return new Reportable() {
//			public String out() {return "Tried to " + (buyNotSell? "buy " : "sell ") + Naming.goodName(g) + " at fair price of " + px;}
//		};
//	}

	public static Reportable limitOrder(final int g, final int px, final boolean buyNotSell) {
		return new Reportable() {
			public String toString() {return "Placed " + (buyNotSell? "bid" : "offer") + " for " + Naming.goodName(g) + " at price of " + px;}
		};
	}

	public static Reportable transaction(final int g, final int px, final boolean buyNotSell, final Clan other) {
		return new Reportable() {
			public String toString() {return (buyNotSell? "Bought " : "Sold ") + Naming.goodName(g) + " at price of " + px + " "+(buyNotSell? "from" : "to")+" " + other.getNomen();}
		};
	}

	public static Reportable consume(final int g) {
		return new Reportable() {
			public String toString() {return "Consumed " + Naming.goodName(g);}
		};
	}

	public static Reportable produce(final int g) {
		return new Reportable() {
			public String toString() {return "Produced " + Naming.goodName(g);}
		};
	}

	public static Reportable produce(final short W) {
		return new Reportable() {
			public String toString() {return "Forged " + XWeapon.weaponName(W);}
		};
	}

	public static Reportable accumulated(final int amt) {
		return new Reportable() {
			public String toString() {return "Accumulated " + amt + " millet";}
		};
	}

	public static Reportable hungry() {
		return new Reportable() {
			public String toString() {return "Hungry...";}
		};
	}
	public static Reportable lostChild() {
		return new Reportable() {
			public String toString() {return "Lost a spawn";}
		};
	}
	public static Reportable died() {
		return new Reportable() {
			public String toString() {return "Died";}
		};
	}
	
	public static Reportable dealTermTribute(final Clan prop, final Clan eval, final int millet) {
		return new Reportable() {
			public String toString() {return prop.getNomen() + " demanded " + millet + " millet from " + eval.getNomen();}
		};
	}
	public static Reportable dealTermReward(final Clan prop, final Clan eval, final int millet) {
		return new Reportable() {
			public String toString() {return prop.getNomen() + " offered " + millet + " millet to " + eval.getNomen();}
		};
	}
	public static Reportable dealTermPatronage(final Clan prop, final Clan eval) {
		return new Reportable() {
			public String toString() {return prop.getNomen() + " offered patronage to " + eval.getNomen();}
		};
	}
	public static Reportable dealTermAllegiance(final Clan prop, final Clan eval) {
		return new Reportable() {
			public String toString() {return prop.getNomen() + " demanded allegiance from " + eval.getNomen();}
		};
	}
	public static Reportable dealTermRepentance(final Clan prop, final Clan eval) {
		return new Reportable() {
			public String toString() {return prop.getNomen() + " demanded repentance from " + eval.getNomen();}
		};
	}
	public static Reportable dealTermSuitor(final Clan prop, final Clan eval) {
		return new Reportable() {
			public String toString() {return prop.getNomen() + " demanded " + eval.getNomen() + " to abandon mate";}
		};
	}
	public static Reportable dealTermService(final Clan prop, final Clan eval, final Value val) {
		return new Reportable() {
			public String toString() {return prop.getNomen() + " demanded " + val.getMinistry().getDesc(eval) + " service from " + eval.getNomen();}
		};
	}
	public static Reportable dealTermThreat(final Clan prop, final Clan eval) {
		return new Reportable() {
			public String toString() {return prop.getNomen() + " threatened " + eval.getNomen();}
		};
	}
	public static Reportable contractOutcome(final Clan prop, final Clan eval, final boolean accepted,
			final double demandValue, final double offerValue) {
		return new Reportable() {
			public String toString() {return eval.getNomen() + (accepted ? " accepted" : " rejected") + " conditions" + (accepted?"":"!") +
					" D:"+Calc.roundy(demandValue,2) + " + O:"+Calc.roundy(offerValue,2);}
		};
	}

	public static Reportable successfulCourt(final Clan mate) {
		return new Reportable() {
			public String toString() {return "Courted " + mate.getNomen() + " successfully";}
		};
	}

	public static Reportable findSomeone(final Clan target, final String what) {
		return new Reportable() {
			public String toString() {return "Searched for " + what + " " + (target == null ? "but found nobody worthwhile.": "and found " + target.getNomen());}
		};
	}

	public static Reportable createOrder(final boolean preexisting) {
		return new Reportable() {
			public String toString() {return preexisting ? "Decided not to follow anyone." : "Decided to start own Order";}
		};
	}
	
	public static Reportable assignMinistry(final Ministry m, final Clan c, final Clan replaced) {
		return new Reportable() {
			public String toString() {return "Assigned " + c.getNomen() + " to " + m.getDesc(c) + (replaced != null ? ", replacing " + replaced.getNomen() : "");}
		};
	}
	public static Reportable decidedMoral(final PersonalCommandment c, final boolean enabled) {
		return new Reportable() {
			public String toString() {return "Decided that to " + c.getVerb() + " is " + (enabled ? "" : "not ") + "a sin";}
		};
	}
	public static Reportable converted(final Clan target, final boolean success) {
		return new Reportable() {
			public String toString() {return (success ? "Converted " : "Failed to convert ") + target.getNomen();}
		};
	}
	public static Reportable wasConverted(final Clan converter, final boolean success) {
		return new Reportable() {
			public String toString() {return (success ? "Bowed to conversion attempt by " : "Rejected conversion attempt by ") + converter.getNomen();}
		};
	}
	
	public static Reportable handToHand(final Clan opponent, final boolean winorlose) {
		return new Reportable() {
			public String toString() {return "Defeated " + (winorlose?"":"by ") + opponent.getNomen() +" in combat.";}
		};
	}
	
	public static Reportable compete4Mate(final Clan mate, final Clan rival, final double result) {
		return new Reportable() {
			public String toString() {
				return (result > 0 ? "Impressed " + mate.getNomen() + (rival!=mate?" more than " + rival.getNomen():"") :
					(result < 0 ? "Failed to impress " + mate.getNomen() + (rival!=mate?" more than " + rival.getNomen():"") : ""));
			}
		};
	}
	public static Reportable preach(final Clan target, final boolean success) {
		return new Reportable() {
			public String toString() {
				return (success ? "Succeeded in preaching" : "Failed to preach") + " values to " + target.getNomen();
			}
		};
	}
	@SuppressWarnings("rawtypes")
	public static Reportable contributeKnowledge(final KnowledgeBlock kb) {
		return new Reportable() {
			public String toString() {return "Contributed " + kb;}
		};
	}
	public static Reportable observe(final Object observee) {
		return new Reportable() {
			public String toString() {return "Collected data on " + observee.toString();}
		};
	}
	
	public static Reportable pray(final Clan prayer, final Clan prayee, final int mana, final ActOfFaith aof) {
		return new Reportable() {
			public String toString() {return prayer.getNomen() + " generated " + mana + " mana" + (prayee!=prayer?" for "+prayee:"") + " through " + aof.desc();}
		};
	}
	
	public static Reportable build(final Clan subject, final int amt, final boolean transitive) {
		return new Reportable() {
			public String toString() {return transitive ? ("Produced " + amt + " splendor for " + subject.getNomen()) :
				("Acquired " + amt + " splendor built by " + subject.getNomen());}
		};
	}

	public static Reportable discovery(final Job job) {
		return new Reportable() {
			public String toString() {return "Dreamt of being a " + job.getDesc();}
		};
	}
	public static Reportable practice(final P_ skill, final boolean success) {
		return new Reportable() {
			public String toString() {return Naming.prestName(skill) + (success ? " level up!" : " practiced");}
		};
	}
	
	public static Reportable demandRespect(final Clan target, final boolean success) {
		return new Reportable() {
			public String toString() {return (success ? "Paid respect by " : "Disrespected by ") + target.getNomen();}
		};
	}
	
	public static Reportable nowhereToAttack() {
		return new Reportable() {
			public String toString() {return "Could not find suitable shire to conquer";}
		};
	}
	public static Reportable backedDown(final Clan me, final Clan target) {
		return new Reportable() {
			public String toString() {return me.getFirstName() + " yielded to " + target.getFirstName();}
		};
	}
	public static Reportable recruitForWar(final Clan recruiter, final Clan recruit) {
		return new Reportable() {
			public String toString() {
				if (recruiter == recruit) return "Mobilized for combat";
				return recruiter.getNomen() + " mobilized " + recruit.getNomen() + " for combat";
			}
		};
	}
	public static Reportable battleResult(final Clan attacker, final Clan defender, final int numA, final int numD, final boolean attackerWins) {
//		System.out.println(winner.getNomen() + "(" + numA + ") defeated " + loser.getNomen() + "(" + numD + ") in battle!");
		return new Reportable() {
			public String toString() {return attackerWins ? attacker.getNomen() + "(" + numA + ") successfully attacked " + defender.getNomen() + "(" + numD + ")!"
					: defender.getNomen() + "(" + numD + ") successfully defended against " + attacker.getNomen() + "(" + numA + ")!";}
		};
	}
	public static Reportable moveCurrentShire(final Shire origin, final Shire destination) {
		return new Reportable() {
			public String toString() {return "Moved from " + origin.getName() + " to " + destination.getName();}
		};
	}

	public static Reportable disbanded() {
		return new Reportable() {
			public String toString() {return "Disbanded army";}
		};
	}
	public static Reportable warStarted(final Clan newEnemy, final Collection<Clan> allEnemies) {
		return new Reportable() {
			public String toString() {
				String s = "New war with " + newEnemy;
				if (allEnemies != null) { s += ". also fighing: "; for (Clan enemy : allEnemies) if (enemy != newEnemy) s += enemy + ","; }
				return s;
			}
		};
	}
	public static Reportable warEnded(final Clan oldEnemy, final Collection<Clan> remainingEnemies) {
		return new Reportable() {
			public String toString() {
				String s = "Ended war with " + oldEnemy + ". still fighing: "; for (Clan enemy : remainingEnemies) s += enemy + ",";
				return s;
			}
		};
	}
	
	public static Reportable tookFromTreasury(final int amount) {
		return new Reportable() {
			public String toString() {return "Took " + amount + " millet from treasury";}
		};
	}
	
	public static Reportable paidTax(final Clan collector, final int amount) {
		return new Reportable() {
			public String toString() {return "Paid " + amount + " tax to " + collector;}
		};
	}

	public static Reportable enoughTaxes(final Clan taxee, final Clan taxer) {
		return new Reportable() {
			public String toString() {return taxee + " has had enough of paying taxes to " + taxer;}
		};
	}
	public static Reportable beginBloodVengeance(final Clan blamee) {
		return new Reportable() {
			public String toString() {return "Begun blood vengeance against " + blamee;}
		};
	}
	
	public static Reportable questExplosion() {
		return new Reportable() {
			public String toString() {return "QuestExplostion!";}
		};
	}
	
}
