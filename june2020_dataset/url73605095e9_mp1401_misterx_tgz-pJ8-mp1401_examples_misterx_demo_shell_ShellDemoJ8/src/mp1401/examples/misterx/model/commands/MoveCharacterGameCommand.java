package mp1401.examples.misterx.model.commands;

import mp1401.examples.misterx.model.gameitems.Character;
import mp1401.examples.misterx.model.gameitems.City;
import mp1401.examples.misterx.model.gameitems.Detective;

public class MoveCharacterGameCommand extends AbstractGameCommand {

	Character character;
	City destinationCity;

	public MoveCharacterGameCommand(Character character, City destinationCity) {
		this.character = character;
		this.destinationCity = destinationCity;
	}

	@Override
	public void execute() {
		if (isCharacterMisterX(character)) {
			getGame().moveMisterXTo(destinationCity);
		} else {
			getGame().moveDetectiveTo((Detective) character, destinationCity);
		}
        
	}

	private boolean isCharacterMisterX(Character character) {
		return getGame().getMisterX().equals(character);
	}

}
