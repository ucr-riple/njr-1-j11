package mp1401.examples.misterx.demo.gui.infopanels;

import mp1401.examples.misterx.model.gameitems.Character;
import mp1401.examples.misterx.model.observers.GameItemObserver;

public class CharacterInfoLabel extends AbstractGameViewInfoLabel implements GameItemObserver {

	private static final long serialVersionUID = -6313156305381125823L;
	
	private final Character character;

	public CharacterInfoLabel(Character character) {
		super(character.toString());
		this.character = character;
		this.character.addGameItemObserver(this);
		refreshText();
	}

	@Override
	public void gameItemUpdate() {
		refreshText();
	}

	@Override
	public String getValue() {
		return character.getCurrentPosition().toString();
	}

}
