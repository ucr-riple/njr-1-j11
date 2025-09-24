package input;

import modes.TBGameMode;
/**
 * MoveEvents class for down movement represented by key
 * @author prithvi
 *
 */
public class MoveDownEvent extends MoveEvents{

	public MoveDownEvent(TBGameMode game, int keyBinding) {
		super(game, keyBinding);
	}

	protected int getDx(int mouseX) {
		return 0;
	}

	protected int getDy(int mouseY) {
		return 1;
	}

}