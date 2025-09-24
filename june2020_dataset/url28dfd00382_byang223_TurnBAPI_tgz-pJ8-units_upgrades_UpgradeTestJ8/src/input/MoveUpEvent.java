package input;

import modes.TBGameMode;
/**
 * MoveEvents class for up movement represented by key
 * @author prithvi
 *
 */
public class MoveUpEvent extends MoveEvents{

	public MoveUpEvent(TBGameMode game, int keyBinding) {
		super(game, keyBinding);
	}

	protected int getDx(int mouseX) {
		return 0;
	}

	protected int getDy(int mouseY) {
		return -1;
	}

}