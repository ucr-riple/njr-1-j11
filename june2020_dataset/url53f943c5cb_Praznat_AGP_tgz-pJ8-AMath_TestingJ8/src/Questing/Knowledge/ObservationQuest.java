package Questing.Knowledge;

import AMath.Calc;
import Defs.M_;
import Descriptions.GobLog;
import Questing.Knowledge.KnowledgeQuests.*;
import Questing.Quest.PatronedQuest;
import Sentiens.Clan;

public class ObservationQuest<O, T> extends PatronedQuest {
	private final int obsPerTurn;
	private final KnowledgeObserver<O, T> knowledgeObserver;
	private int turnsLeft;
	private transient int[] popv; //really transient??
	protected final O[] observationPopulation;
	protected transient int place = 0; //really transient??
	public ObservationQuest(Clan P, Clan patron, KnowledgeObserver<O, T> ko, O[] observationPopulation, int numObsPerTurn) {
		super(P, patron);
		knowledgeObserver = ko;
		turnsLeft = P.FB.getBeh(M_.PATIENCE) + 10; //between 10 and 25
		this.obsPerTurn = numObsPerTurn;
		this.observationPopulation = observationPopulation;
		popv = Calc.randomOrder(observationPopulation.length);
	}
	@Override
	public void pursue() {
		if (turnsLeft-- <= 0 || place >= observationPopulation.length) {
			final KnowledgeBlock<T> result = knowledgeObserver.createKnowledgeBlock(Me);
			patron.getRelevantLibrary().putKnowledge(result); // patron doesnt get credit but does get knowledge placement
			Me.addReport(GobLog.contributeKnowledge(result));
			success();
			((KnowledgeQuest)Me.MB.QuestStack.peek()).useKnowledgeBlock(result);
			return;
		}
		for (int k = 0; k < obsPerTurn; k++) {
			if (place >= observationPopulation.length) break;
			doObservation(determineTarget());
		}
	}
	protected O determineTarget() {
		return observationPopulation[popv[place++]];
	}
	protected void doObservation(O target) {
		knowledgeObserver.observe(target);
		Me.addReport(GobLog.observe(target));
	}
	@Override
	public String description() {return "Make observations";}
}