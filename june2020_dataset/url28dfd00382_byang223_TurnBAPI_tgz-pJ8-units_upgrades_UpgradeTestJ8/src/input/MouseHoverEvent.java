package input;

import modes.TBGameMode;
/**
 * MoveEvents class represented by mouse movement
 * @author prithvi
 *
 */
public class MouseHoverEvent extends MoveEvents{

	public MouseHoverEvent(TBGameMode game, int keyBinding) {
		super(game, keyBinding);
	}

	protected int getDx(int mouseX) {
		return (mouseX-getMap().getXLocation())/getMap().getTileDimX() - getGame().getTile().getTileCoordinateX();
	}

	protected int getDy(int mouseY) {
		return (mouseY-getMap().getYLocation())/getMap().getTileDimY() - getGame().getTile().getTileCoordinateY();
	}

}