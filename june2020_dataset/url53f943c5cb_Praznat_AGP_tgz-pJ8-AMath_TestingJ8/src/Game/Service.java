package Game;

import Questing.Quest;
import Sentiens.Clan;

public class Service implements Act {
	Quest.PatronedQuestFactory questFactory;
	public Service(Quest.PatronedQuestFactory q) {
		questFactory = q;
	}

	@Override
	public void doit(Clan doer) {
		if (!doer.MB.QuestStack.prioritizeExistingMemberOfType(questFactory.getQuestType())) {
			doer.MB.newQ(questFactory.createFor(doer, doer.getBoss()));
		} //dont actually call pursue() because usually already a good deal of work was done by doer just to get here
	}

	@Override
	public double estimateProfit(Clan pOV) {
		// TODO patronage!!!
		return 0;
	}

	@Override
	public String getDesc() {
		return "Service";
	}

	@Override
	public int getSkill(Clan clan) {
		// TODO Auto-generated method stub
		return 0;
	}

}
