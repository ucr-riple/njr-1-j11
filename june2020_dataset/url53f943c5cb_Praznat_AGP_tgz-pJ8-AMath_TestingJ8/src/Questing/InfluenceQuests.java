package Questing;

import java.util.*;

import AMath.Calc;
import Avatar.SubjectiveType;
import Defs.M_;
import Descriptions.GobLog;
import Game.*;
import Government.Order;
import Ideology.*;
import Questing.Quest.PatronedQuest;
import Questing.Quest.PatronedQuestFactory;
import Questing.Quest.TransactionQuest;
import Sentiens.*;
import Sentiens.Stress.StressorFactory;

public class InfluenceQuests {
	public static PatronedQuestFactory getMinistryFactory() {return new PatronedQuestFactory(InfluenceQuest.class) {public Quest createFor(Clan c, Clan p) {return new InfluenceQuest(c, p);}};}
	
	public static class PropagandaQuest extends Quest {
		public PropagandaQuest(Clan P) {super(P);}
		@Override
		public void pursue() {
			// TODO preach
		}
		@Override
		public String description() {return "Spread propaganda";}
	}
	
	public static class RecruitmentQuest extends TransactionQuest {
		private final Clan patron; // basically PatronQuest but extends TransactionQuest
		public RecruitmentQuest(Clan P, Clan patron) {super(P); this.patron = patron;}
		@Override
		public String description() {return "Recruit followers";}
		@Override
		protected FindTargetAbstract findWhat() {
			return new FindTargetAbstract(Me, TargetQuest.getReasonableCandidates(Me), Me) {
				@Override
				public boolean meetsReq(Clan POV, Clan target) {return target.myOrder() != POV.myOrder();}
				@Override
				protected void onFailure() {
					failure(StressorFactory.createShireStressor(Me.myShire(), Values.INFLUENCE));
				}
				@Override
				protected String searchDesc() {return "new minion";}
			};
		}
		@Override
		protected void setContractDemand() {
			contract().demandAllegiance();
		}
		@Override
		protected void setContractOffer() {
			// options:
			// -improve respectability (target ideology = allegiance)
			// -hire paid employees (target ideology = wealth)
			// -offer salvation (target is sinful)
			// -offer power/status (target ideology = influence)
			// -offer land (target ideology = might??)
			if (timesLeft > 2) {contract().offerPatronage();}
			else {contract().offerReward(Me.getMillet() * timesLeft / 10);} // TODO this formula is arbitrary...
		}
		@Override
		protected void successCase() {
			// TODO Auto-generated method stub
			success();
		}
		@Override
		protected void failCase() {
			// TODO Auto-generated method stub
			finish(); // maybe try to improve patron's respectability?
		}
		@Override
		protected void report(boolean success) {
			// TODO Auto-generated method stub
		}
	}
	
	public static class InfluenceQuest extends PatronedQuest {
		private Clan rival;
		public InfluenceQuest(Clan P, Clan patron) {super(P, patron);}
		@Override
		public String description() {return "Expand influence";}

		@Override
		public void avatarPursue() {
			if (!(patron == Me || patron.isSomeBossOf(Me))) {Me.MB.finishQ();   return;}
			commonPursuit();
			final GradedQuest[] options = {new GradedQuest(new RecruitmentQuest(Me, patron), rivalFollowers() - patron.getMinionTotal()),
					new GradedQuest(new OrganizeMinistries(Me), patron.getMinionTotal() - rivalFollowers())};
			avatarConsole().showChoices("Choose quest", Me, options, SubjectiveType.QUEST_ORDER, new Calc.Listener() {
				@Override
				public void call(Object arg) {
					replaceAndDoNewQuest(Me, ((GradedQuest) arg).getQuest());
				}
			});
		}
		
		@Override
		public void pursue() {
			if (!(patron == Me || patron.isSomeBossOf(Me))) {Me.MB.finishQ();   return;}
			commonPursuit();
			if (patron.getMinionTotal() <= rivalFollowers()) {
				replaceAndDoNewQuest(Me, new RecruitmentQuest(Me, patron));
			}
			else {
				replaceAndDoNewQuest(Me, new OrganizeMinistries(Me));
			}
		}
		private void commonPursuit() {
			rival = Calc.oneOf(AGPmain.TheRealm.getPopulation());
			if (patron.myOrder() == null) {Order.createBy(patron); patron.addReport(GobLog.createOrder(false));}
		}
		private int rivalFollowers() {return rival == null || rival.myOrder() == null ? 0 : rival.getMinionTotal();}
	}
	
	public static class OrganizeMinistries extends Quest {
		private final ArrayList<Clan> followers;
		private final HashMap<Ministry, Integer> numEach = new HashMap<Ministry, Integer>();
		private int movesLeft = 1 + Me.useBeh(M_.PATIENCE);
		public OrganizeMinistries(Clan P) {
			super(P);
			followers = new ArrayList<Clan>();
			final Order order = Me.myOrder();
			if (order != null) {followers.addAll(order.getFollowers(Me, false, false));}
		}
		@Override
		public String description() {return "Assign subordinates";}
		
		@Override
		public void avatarPursue() {
			if (Math.min(movesLeft--, followers.size()) <= 0) {
				Me.MB.finishQ();   return;
			}
			Calc.Transformer<Clan, String> describer = new Calc.Transformer<Clan, String>() {
				@Override
				public String transform(Clan c) {
					return c.getNomen() + " : " + c.getJob().getDesc(c);
				}
			};
			avatarConsole().showChoices("Choose subordinate to reassign", Me, followers, SubjectiveType.RESPECT_ORDER, new Calc.Listener() {
				@Override
				public void call(Object arg) {
					final Clan clan = (Clan)arg;
					avatarConsole().showChoices("Choose ministry to assign " + clan.getNomen() + " to", Me, Values.All,
							SubjectiveType.VALUE_ORDER, new Calc.Listener() {
						@Override
						public void call(Object arg) {
							clan.setJob(((Value)arg).getMinistry());
							followers.remove(clan);
						}
					}, new Calc.Transformer<Value, String>() {
						@Override
						public String transform(Value v) {
							return v.getMinistry().getDesc(clan);
						}
					});
				}
			}, describer);
		}
		
		/**
		 * 1. determine number allotted each ministry according to value %s
		 * 2. pick a follower C at random
		 * 3. pick a value V at random according to priority
		 * 4. if there is still space left at the ministry corresponding to V, assign C there
		 * 5. if no space, pick a random minister M in the ministry corresponding to V
		 * 6. if C is more suitable than M, replace, leaving M with Allegiance job
		 */
		@Override
		public void pursue() {
			if (Math.min(movesLeft--, followers.size()) <= 0) {
				Me.MB.finishQ();   return;
			}
			if (numEach.isEmpty()) {
				determineNumEach();
				return;
			}
			final int i = AGPmain.rand.nextInt(followers.size());
			final Clan c = followers.get(i);
			if (Me.isDirectBossOf(c)) {
				final Value v = Me.FB.randomValueInPriority();
				final Ministry m = v.getMinistry();
				final Integer N = numEach.get(m);
				final int n = N != null ? N : 0;
				
				if (n > 0) {
					c.setJob(m);
					Me.addReport(GobLog.assignMinistry(m, c, null));
					numEach.put(m, n-1);
				}
				else {
					final ArrayList<Clan> ministers = findFollowersWithMinistry(m);
					if (ministers.isEmpty()) {return;} //TODO wait why
					final Clan alt = ministers.get(AGPmain.rand.nextInt(ministers.size()));
					if (v.compare(Me, c, alt) > 0) {
						alt.setJob(Job.NOBLE); // ALLEGIANCE ministry is default (minion)
						c.setJob(m);
						Me.addReport(GobLog.assignMinistry(m, c, alt));
					}
				}
			}
			followers.remove(i);
		}
		private void determineNumEach() {
			numEach.clear();
			int assignableLeft = Me.getMinionN();
			final Value[] sncs = new Value[Ideology.FSM[0].length];
			final double[] pcts = new double[Ideology.FSM[0].length];
			Me.FB.getSancPcts(sncs, pcts);
			for (int i = 0; i < sncs.length; i++) {
				final int n = Math.min((int) Math.round(pcts[i] * Me.getMinionN()), assignableLeft);
				// TODO what about if highest is 10% and only 2 minions???
				if (n <= 0) {return;}
				numEach.put(sncs[i].getMinistry(), n);
				assignableLeft -= n;
				if (assignableLeft == 0) {return;}
			}
		}
		private ArrayList<Clan> findFollowersWithMinistry(Ministry m) {
			ArrayList<Clan> result = new ArrayList<Clan>();
			Collection<Clan> f = Me.myOrder().getFollowers(Me, false, false);
			for (Clan c : f) {
				if (c.getJob() == m) {result.add(c);}
			}
			return result;
		}
	}
	
}
