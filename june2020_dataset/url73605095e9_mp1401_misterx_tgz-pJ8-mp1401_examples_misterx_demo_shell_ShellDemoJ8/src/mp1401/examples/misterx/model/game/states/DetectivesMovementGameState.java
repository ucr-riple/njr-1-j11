package mp1401.examples.misterx.model.game.states;

import java.util.ArrayList;
import java.util.List;

import mp1401.examples.misterx.model.gameitems.Character;
import mp1401.examples.misterx.model.gameitems.City;
import mp1401.examples.misterx.model.gameitems.Detective;
import mp1401.examples.misterx.model.util.Messsages;


public class DetectivesMovementGameState extends AbstractMovementGameState {
	
	private static final long serialVersionUID = -5969261800018994120L;

	private static final String DETECTIVE_HAS_ALREADY_MOVED_MESSAGE = "This detective has already moved in this round.";
	
	private List<Character> detectivesThatAlreadyMoved;
	
	public DetectivesMovementGameState() {
		this.detectivesThatAlreadyMoved = new ArrayList<Character>();
	}
	
	@Override
	public void moveDetectiveTo(Detective detective, City destinationCity) {
		if(hasDetectiveAlreadyMoved(detective)) {
			detectiveAlreadyMovedAction();
		} else {
			detectiveNotYetMovedAction(detective, destinationCity);
		}
	}
	
	private boolean hasDetectiveAlreadyMoved(Detective detective) {
		return detectivesThatAlreadyMoved.contains(detective);
	}
	
	private void detectiveAlreadyMovedAction() {
		Messsages.printMessage(DETECTIVE_HAS_ALREADY_MOVED_MESSAGE);
	}
	
	private void detectiveNotYetMovedAction(Detective detective, City destinationCity) {
		moveCharacterAction(detective, destinationCity);
		updateGameState();
	}
	
	private void updateGameState() {
		if(haveAllDetectivesMoved()) {
			detectivesThatAlreadyMoved.clear();
			changeGameState(new CheckSituationGameStates());
			getGame().checkSituation();
		}
	}
	
	private boolean haveAllDetectivesMoved() {
		return getGame().getDetectives().size() == detectivesThatAlreadyMoved.size();
	}

	@Override
	public void characterMovementPostAction(Character character) {
		detectivesThatAlreadyMoved.add(character);
	}

}
