package input;

import modes.TBGameMode;
/**
 * MoveEvents class for right movement represented by key
 * @author prithvi
 *
 */
public class MoveRightEvent extends MoveEvents{

	public MoveRightEvent(TBGameMode game, int keyBinding) {
		super(game, keyBinding);
	}

	protected int getDx(int mouseX) {
		return 1;
	}

	protected int getDy(int mouseY) {
		return 0;
	}

}
