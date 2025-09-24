package mp1401.examples.misterx.model.commands;

import mp1401.examples.misterx.model.gameitems.Character;
import mp1401.examples.misterx.model.gameitems.City;

public class SetStartPositionGameCommand extends AbstractGameCommand {
	
	Character character;
	City startPosition;
	
	public SetStartPositionGameCommand(Character character, City startPosition) {
		this.character = character;
		this.startPosition = startPosition;
	}

	@Override
	public void execute() {
		getGame().setStartPosition(character, startPosition);
		
	}

}
