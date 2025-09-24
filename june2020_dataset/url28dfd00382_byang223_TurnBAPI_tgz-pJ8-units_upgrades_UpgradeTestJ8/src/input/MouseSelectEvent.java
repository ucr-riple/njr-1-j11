package input;

import modes.TBGameMode;
/**
 * SelectEvents class represented by mouse input
 * @author prithvi
 *
 */
public class MouseSelectEvent extends SelectEvents {

	public MouseSelectEvent(TBGameMode game, int keyBinding) {
		super(game, keyBinding);
	}

	protected int getXdx(int mouseX) {
		return mouseX/getMap().getTileDimX() - getGame().getTile().getTileCoordinateX();
	}

	protected int getYdx(int mouseY) {
		return mouseY/getMap().getTileDimY() - getGame().getTile().getTileCoordinateY();
	}

	protected boolean isNotInBounds(int mouseX, int mouseY) {
		return mouseX>700 || mouseY>500;
	}

}
