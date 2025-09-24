package mp1401.examples.misterx.model.gameitems.impl;

import mp1401.examples.misterx.model.gameitems.Character;
import mp1401.examples.misterx.model.gameitems.City;

public abstract class AbstractCharacterImpl extends AbstractGameItemImpl implements
		Character {

	private static final long serialVersionUID = -4606825984056977539L;
	
	City currentPosition;
	
	public AbstractCharacterImpl() {
		currentPosition = new UnknownCityImpl();
	}

	@Override
	public City getCurrentPosition() {
		return currentPosition;
	}

	@Override
	public void setCurrentPosition(City currentPosition) {
		this.currentPosition = currentPosition;
		notifyGameItemObservers();
	}
	
	@Override
	public abstract String toString();
}
