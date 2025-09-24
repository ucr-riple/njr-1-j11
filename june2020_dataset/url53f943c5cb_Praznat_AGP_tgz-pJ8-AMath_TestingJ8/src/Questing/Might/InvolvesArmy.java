package Questing.Might;

import java.util.Set;

import Sentiens.Clan;

public interface InvolvesArmy {
	public Set<FormArmyQuest> getArmy();
	public void setArmy(Set<FormArmyQuest> army);
	public Clan getDoer(); // as in Quest
}