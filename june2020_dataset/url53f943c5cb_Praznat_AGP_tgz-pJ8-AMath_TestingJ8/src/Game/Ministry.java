package Game;

import Descriptions.MinisterNames;
import Questing.Quest;
import Sentiens.Clan;

public class Ministry extends Job {

	public Ministry(Quest.PatronedQuestFactory questFactory) {
		super("Citizen", new Service(questFactory));
	}
	
	@Override
	public String getDesc(Clan c) {return MinisterNames.getMinistryName(this, c);}
	
	public Service getService() {return (Service)acts[0];}

}
