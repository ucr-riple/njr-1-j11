package cz.dusanrychnovsky.chessendgames.gui;

import cz.dusanrychnovsky.chessendgames.core.Position;

/**
 * 
 * @author Dušan Rychnovský
 *
 */
public interface MouseEventListener
{
	/**
	 * 
	 * @param position
	 */
	public abstract void onMouseClicked(Position position);
}
