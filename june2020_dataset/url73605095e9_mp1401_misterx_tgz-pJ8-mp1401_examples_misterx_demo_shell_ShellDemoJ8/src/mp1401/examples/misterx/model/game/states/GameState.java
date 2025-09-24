package mp1401.examples.misterx.model.game.states;

import java.io.Serializable;

import mp1401.examples.misterx.model.gameitems.Character;
import mp1401.examples.misterx.model.gameitems.City;
import mp1401.examples.misterx.model.gameitems.Detective;
import mp1401.examples.misterx.model.gameitems.MisterX;
import mp1401.examples.misterx.model.mapparser.MapDataParser;

public interface GameState extends Serializable {

	public void fillMap(MapDataParser mapDataParser);

	public void addMisterX(MisterX misterX);

	public void addDetective(Detective detective);

	public void setStartPosition(Character character, City startPosition);

	public void startGame();

	public void moveMisterXTo(City destinationCity);

	public void moveDetectiveTo(Detective detective, City destinationCity);

	public void checkSituation();
}
