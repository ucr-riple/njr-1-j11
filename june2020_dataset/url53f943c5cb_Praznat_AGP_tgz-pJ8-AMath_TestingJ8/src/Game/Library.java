package Game;

import Defs.K_;
import Government.Order;
import Questing.Knowledge.*;
import Sentiens.Stress.Stressor;

@SuppressWarnings("rawtypes")
public class Library {
	private static final int START_CAPACITY = 10;
	private static final int MAX_CAPACITY = 30;
	private int capacity = START_CAPACITY;
	private KnowledgeBlock[] knowledge = new KnowledgeBlock[MAX_CAPACITY];
	private Order owningOrder;
	
	public void incCapacity() {if (capacity < MAX_CAPACITY) capacity++;}
	
	public int getCapacity() {return capacity * (owningOrder != null ? owningOrder.numShiresControlled() : 1);}
	
	public KnowledgeBlock findKnowledge(KnowledgeType k) {
		for (KnowledgeBlock kb : knowledge) {
			if (kb == null) return null;
			if (kb.isApplicableFor(k)) {return kb;}
		}	return null;
	}
	public void putKnowledge(KnowledgeBlock kb) {
		// if B started observing after A but before A finished, he will find that A beat him to it
		final KnowledgeBlock preexisting = findKnowledge(kb.relK());
		if (preexisting != null && betterThan(preexisting, kb)){
			kb.getDiscoverer().AB.add(new Stressor(Stressor.COMPETITION_LOSS, preexisting.getDiscoverer()));
		}
		else {actuallyPutKnowledge(kb);}
	}
	private boolean betterThan(KnowledgeBlock preexisting, KnowledgeBlock kb) {
		return true;// earliest wins or preexisting.getNumObservationsUsed() > kb.getNumObservationsUsed(); ?
	}

	/** puts knowledge at front of library */
	public void actuallyPutKnowledge(KnowledgeBlock kb) {
		final int effectiveCapacity = getCapacity();
		for (int i = effectiveCapacity-1; i > 0; i--) {
			knowledge[i] = knowledge[i-1]; // shift everything right (forgetting last one)
		}
		knowledge[0] = kb;
	}
	public KnowledgeBlock getKnowledge(int i) {return knowledge[i];}
	
	public int getAmountOfKnowledge() {
		int i = 0; for (; i < capacity; i++) if (knowledge[i] == null) return i;
		return i;
	}
	
	@Override
	public String toString() {
		String s = ""; for (KnowledgeBlock kb : knowledge) {if (kb == null) break; s+=kb;}
		return s;
	}
}
