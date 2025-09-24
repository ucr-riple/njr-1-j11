package input;

import modes.TBGameMode;
/**
 * Deselect Event that is represented by a key
 * @author prithvi
 *
 */
public class KeyDeselectEvent extends DeselectEvents{

	public KeyDeselectEvent(TBGameMode game, int keyBinding) {
		super(game, keyBinding);
	}

}
