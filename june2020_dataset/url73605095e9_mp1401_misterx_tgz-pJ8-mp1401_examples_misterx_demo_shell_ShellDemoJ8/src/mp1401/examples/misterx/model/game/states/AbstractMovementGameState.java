package mp1401.examples.misterx.model.game.states;

import mp1401.examples.misterx.model.gameitems.Character;
import mp1401.examples.misterx.model.gameitems.City;

public abstract class AbstractMovementGameState extends AbstractGameState {

	private static final long serialVersionUID = -4670045964130984922L;

	public final void moveCharacterAction(Character character, City destinationCity) {
		if(getGame().getPositionChecker().canCharacterMoveToCity(character, destinationCity)) {
			character.setCurrentPosition(destinationCity);
			characterMovementPostAction(character);
		}
	}
	
	public abstract void characterMovementPostAction(Character character);
}
