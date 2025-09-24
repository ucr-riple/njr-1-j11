package Questing.Might;

import java.util.*;

import Descriptions.GobLog;
import Questing.*;
import Questing.Quest.BranchQuest;
import Questing.Might.MightQuests.DefendPatron;
import Sentiens.Clan;

public class FormArmyQuest extends BranchQuest implements InvolvesArmy {
	private boolean doneRecruiting = false;
	public FormArmyQuest(Clan P, WarQuest root) {
		super(P, root);
		if (root != null) {
			Clan recruiter = root.getDoer();
			Me.addReport(GobLog.recruitForWar(recruiter, Me));
			if (Me != root.getDoer()) {recruiter.addReport(GobLog.recruitForWar(recruiter, Me));}
		}
	}
	static boolean recruit(Clan recruiter, WarQuest root) {
		if (recruiter.myOrder() != null) {
			Set<Clan> followers = recruiter.myOrder().getFollowers(recruiter, false, false);
			//TODO should get all DefendPatrons first... otherwise guy with some defenders but lots of non defendes has low chan
			for (Iterator<Clan> iter = followers.iterator(); iter.hasNext();) {
				Clan f = iter.next();
				final Quest topQuest = f.MB.QuestStack.peek();
				
				if (topQuest instanceof FormArmyQuest) {iter.remove();   continue;}
				// DOESNT COST TURN IF CANDIDATE'S QUEST IS ALREADY DEFENDPATRON (upside of standing army = instant formation of first tier)
				if (topQuest instanceof DefendPatron) {iter.remove();   f.MB.newQ(new FormArmyQuest(f, root));}
//		Contract.getInstance().enter(e, p) ?
			}
			for (Clan f : followers) {f.MB.newQ(new FormArmyQuest(f, root)); return false;}
		}
		return true;
	}
	protected WarQuest getRoot() {
		return (WarQuest) root;
	}
	protected boolean checkArmyStatus() {
		Set<FormArmyQuest> army = getArmy();
		boolean isActive = army != null && !army.isEmpty();
		if (!isActive) finish();
		return isActive;
	}
	@Override
	public void pursue() {
		if (!checkArmyStatus()) {return;}
		if (root != null) { // root == null means this is FormOwnArmy
			Set<FormArmyQuest> army = getRoot().getArmy();
			if (army != null) {
				Clan recruiter = getRoot().getDoer();
				if (Me.currentShire() == recruiter.currentShire()) {army.add(this);}
				else {Me.moveTowards(recruiter.currentShire());}
			}
		}
		
		if (!doneRecruiting) {doneRecruiting = recruit(Me, getRoot());} // just chill until disband?
	}
	@Override
	public Set<FormArmyQuest> getArmy() {return root != null ? getRoot().getArmy() : null;}
	@Override
	public void setArmy(Set<FormArmyQuest> army) {} // this really shouldnt even be here
	@Override
	public String description() {
		return "Form army for " + getRoot().getDoer().getFirstName();
	}
	
}