package Questing.Knowledge;

import Sentiens.Clan;
import Shirage.Shire;

public class ExplorationQuest extends ObservationQuest<Shire, Shire> {

	public ExplorationQuest(Clan P, Clan patron, ShireExplorer se,
			Shire[] observationPopulation, int numObsPerTurn) {
		super(P, patron, se, observationPopulation, numObsPerTurn);
	}
	@Override
	protected Shire determineTarget() {
		return observationPopulation[place++];
	}
	@Override
	protected void doObservation(Shire target) {
		Me.moveTowards(target);
		super.doObservation(target);
	}

}
