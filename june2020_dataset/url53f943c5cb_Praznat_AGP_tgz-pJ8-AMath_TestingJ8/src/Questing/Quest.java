package Questing;

import AMath.Calc;
import Avatar.*;
import Defs.*;
import Descriptions.GobLog;
import Game.*;
import Questing.AllegianceQuests.AllegianceQuest;
import Questing.RomanceQuests.BreedQuest;
import Questing.Knowledge.KnowledgeQuests;
import Questing.Might.MightQuests;
import Questing.Wealth.BuildWealthQuest;
import Sentiens.Clan;
import Sentiens.Stress.*;

public abstract class Quest {
	protected Clan Me;
	public Quest(Clan P) {Me = P; Me.addReport(GobLog.beginQuest(this));}
	public void pursueQuest() {
		if (!AGPmain.AUTOPILOT && Me == avatar()) {
			avatarPursue();
		}
		else {pursue();}
	}
	public abstract void pursue();
	public void avatarPursue() {pursue();}  //default leaves it to AI

	protected void success() {finish();   if (Me.MB.QuestStack.empty()) {Me.AB.catharsis(1);}}
	protected void success(Blameable relief) {success(); Me.AB.relieveFrom(new Stressor(Stressor.ANNOYANCE, relief));}
	protected void success(Blameable... reliefs) {success(reliefs[AGPmain.rand.nextInt(reliefs.length)]);}
	protected void finish() {
		 Me.addReport(GobLog.endQuest(this));
		if (!this.getClass().isAssignableFrom(Me.MB.QuestStack.peek().getClass())) {
			System.out.println("bullshit "+this+" finish");specialDelete();
		}
		else {Me.MB.finishQ();}
	}
	public void specialDelete() {Me.MB.QuestStack.remove(this);}
	protected void failure(Blameable blamee) {failure(new Stressor(Stressor.ANNOYANCE, blamee));} // must finish first in case stressor adds new quest
	protected void failure(Blameable... blamees) {failure(blamees[AGPmain.rand.nextInt(blamees.length)]);}
	protected void failure(Stressor stressor) {finish(); Me.AB.add(stressor);}
	protected Quest upQuest() {return Me.MB.QuestStack.peekUp();}
	public String shortName() {return description();}
	public abstract String description();
	@Override
	public String toString() {return description();}
	public Clan getDoer() {return Me;}
	
	protected static AvatarConsole avatarConsole() {return AGPmain.mainGUI != null ? AGPmain.mainGUI.AC : null;}
	public Clan avatar() {return avatarConsole() == null ? null : avatarConsole().getAvatar();}
		
	public static class DefaultQuest extends PatronedQuest {
		public static PatronedQuestFactory getMinistryFactory() {return new PatronedQuestFactory(DefaultQuest.class) {public Quest createFor(Clan c, Clan p) {return new DefaultQuest(c);}};}
		private boolean doForever = false;
		public DefaultQuest(Clan P) {super(P);}
		public DefaultQuest(Clan P, boolean isTest) {super(P); doForever = isTest;}
		@Override
		public void pursue() {Me.addReport(GobLog.idle()); if (!doForever) Me.MB.finishQ();}
		@Override
		public String description() {return "Default Quest";}
	}
	
	public static abstract class RootQuest extends Quest {
		protected BranchQuest branch;
		public RootQuest(Clan P) { super(P); }
		public BranchQuest getBranch() {return branch;}
		@Override
		public void pursue() {
			setup();
			if (branch != null) branch.pursue(); // onNullBranch might set it to not null
			cleanup(); // pursue might set
		}
		@Override
		public String description() {
			return (branch != null ? branch.description() : desc());
		}
		protected void setup() {};
		protected void cleanup() {};
		protected abstract String desc();
	}
	public static abstract class BranchQuest extends Quest {
		protected final RootQuest root;
		public BranchQuest(Clan P, RootQuest root) {
			super(P);
			this.root = root;
		}
		@Override
		protected void finish() {
			root.branch = null;
		}
	}
	
	public static interface PatronedQuestInterface {
		public Clan getPatron();
	}
	public static abstract class PatronedQuest extends Quest implements PatronedQuestInterface {
		protected Clan patron;
		public PatronedQuest(Clan P) {this(P, P);}
		public PatronedQuest(Clan P, Clan patron) {super(P); this.patron = patron;}
		@Override
		public void pursueQuest() {
			// TODO get paid by patron!!!
			super.pursueQuest();
		}
		@Override
		public Clan getPatron() {return patron;}
		@Override
		public String toString() {return super.toString() + (patron != Me ? " for " + patron.getNomen() : "");}
	}
	public static abstract class TargetQuest extends Quest { //TODO make into interface or something
		protected Clan target;
		public TargetQuest(Clan P) {super(P);}
		public TargetQuest(Clan P, Clan T) {super(P); target = T;}
		public void setTarget(Clan t) {target = t;}
		public Clan getTarget() {return target;}
		public static Clan[] getReasonableCandidates(Clan pov) {
			if (pov == pov.myShire().getGovernor()) {
				// TODO return neighbor governors...
			}
			return pov.myShire().getCensus().toArray(new Clan[0]);
		}
	}
	public static interface RelationCondition {
		public boolean meetsReq(Clan POV, Clan target);
	}
	public static abstract class FindTargetAbstract extends Quest implements RelationCondition {
		protected final Clan[] candidates;
		protected final Blameable blameSource;
		public FindTargetAbstract(Clan P) {this(P, TargetQuest.getReasonableCandidates(P), P.myShire());}
		public FindTargetAbstract(Clan P, Clan[] candidates, Blameable c) {
			super(P);
			this.candidates = candidates;
			this.blameSource = c;
		}
		@Override
		public String description() {return "Find target";}
		public void setTarget(Clan c) {
			((TargetQuest) upQuest()).setTarget(c);  //must be called by TargetQuest
		}
		@Override
		public void pursue() {
			for (int i = Math.min(triesPerTurn(), candidates.length); i > 0; i--) {
				Clan candidate = candidates[AGPmain.rand.nextInt(candidates.length)];
				if(meetsReq(Me, candidate)) {
					setTarget(candidate);
					Me.addReport(GobLog.findSomeone(candidate, searchDesc()));
					success(Me.myShire()); return;
				}
			}
			Me.addReport(GobLog.findSomeone(null, searchDesc()));
			onFailure();
		}
		protected abstract String searchDesc();
		protected abstract void onFailure();
		@Override
		public void avatarPursue() {
			avatarConsole().showChoices("Choose target", Me, Me.myShire().getCensus().toArray(),
					SubjectiveType.RESPECT_ORDER, new Calc.Listener() {
				@Override
				public void call(Object arg) {
					FindTargetAbstract.this.setTarget((Clan) arg);
					Me.MB.finishQ();
				}
			});
		}
		protected int triesPerTurn() {return 3;} //maybe size of pub?
	}

	public static abstract class TransactionQuest extends TargetQuest {
		protected int timesLeft;
		public TransactionQuest(Clan P) {super(P); timesLeft = Me.useBeh(M_.PATIENCE) / 3 + 3;}
		@Override
		public void pursue() {
			if (pursue1()) {
				setContractDemand();
				setContractOffer();
				pursue2();
			}
		}
		@Override
		public void avatarPursue() {
			if (pursue1()) {
				avatarSetContractDemand();
				avatarSetContractOffer();
				pursue2();
			}
		}
		private boolean pursue1() {
			if (timesLeft == 0) {failure(Me.myShire()); return false;}  //no one found
			if (target == null) {
				Me.MB.newQ(findWhat());
				timesLeft--;
				return false;
			}
			Contract.getNewContract(target, Me); return true;
		}
		private void pursue2() {
			boolean accepted = contract().acceptable();
			if (accepted) {
				contract().enact();
				successCase();
			}
			else {failCase();}
			report(accepted);
		}
		protected abstract FindTargetAbstract findWhat();
		protected abstract void setContractDemand();
		protected abstract void setContractOffer();
		protected void avatarSetContractDemand() {setContractDemand();}
		protected void avatarSetContractOffer() {setContractOffer();}
		protected abstract void successCase();
		protected abstract void failCase();
		protected Contract contract() {return Contract.getInstance();}
		protected abstract void report(boolean success);
	}

	public static abstract class PatronedQuestFactory {
		private Class<? extends PatronedQuest> questType;
		public PatronedQuestFactory(Class<? extends PatronedQuest> clasz) {questType = clasz;}
		public abstract Quest createFor(Clan c, Clan p);
		public Class<? extends PatronedQuest> getQuestType() {return questType;}
	}
	
	protected static void replaceAndDoNewQuest(Clan c, Quest newQuest) {
		c.MB.finishQ();
		c.MB.newQ(newQuest);
		newQuest.pursueQuest();
	}
	
	public static Quest QtoQuest(Clan clan, Q_ q) {
		if (q == null) {return new DefaultQuest(clan);}
		Quest quest;
		switch(q) {
		case BREED: quest = new BreedQuest(clan); break;
		case BUILDWEALTH: quest = new BuildWealthQuest(clan, clan); break;
		case CREEDQUEST: quest = new CreedQuests.PriestQuest(clan, clan); break;
		case LOYALTYQUEST: quest = new AllegianceQuest(clan, clan); break;
		case SPLENDORQUEST: quest = new SplendorQuests.UpgradeDomicileQuest(clan, clan); break;
		case FAITHQUEST: quest = new FaithQuests.ContactQuest(clan, clan); break;
		case LEGACYQUEST: quest = new LegacyQuests.LegacyQuest(clan, clan); break;
		case KNOWLEDGEQUEST: quest = new KnowledgeQuests.KnowledgeQuest(clan, clan); break;
		case BUILDPOPULARITY: quest = new InfluenceQuests.InfluenceQuest(clan, clan); break;
		case TRAIN: quest = new ExpertiseQuests.LearnQuest(clan); break;
		case PICKFIGHT: quest = new MightQuests.BasicMightQuest(clan); break;
		default: quest = new DefaultQuest(clan); break;
		}
		return quest;
	}
	
	public static class GradedQuest {
		private final Quest quest;
		private final double rating;
		public GradedQuest(Quest quest, double rating) {this.quest = quest; this.rating = rating;}
		public Quest getQuest() {return quest;}
		public double getRating() {return rating;}
		@Override
		public String toString() {return quest.description();}
	}
	
	public static class QuestRetrievalQuest extends Quest {
		@SuppressWarnings("unused")
		private final Class<? extends Quest>[] questTypes;
		public QuestRetrievalQuest(Clan c, Class<? extends Quest>[] questTypes) {
			super(c);
			this.questTypes = questTypes;
		}
		@Override
		public void pursue() {
			// TODO Resurrect old quests
			finish();
		}
		@Override
		public String description() {return "Remember old quests";}
	}
	
	public interface Unquenchable {
		public boolean isUnquenchable();
	}

}
