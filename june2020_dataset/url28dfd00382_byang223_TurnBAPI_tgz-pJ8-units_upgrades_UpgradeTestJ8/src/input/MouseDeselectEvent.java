package input;

import modes.TBGameMode;
/**
 * DeselectEvent represented by mouse input
 * @author prithvi
 *
 */
public class MouseDeselectEvent extends DeselectEvents{

	public MouseDeselectEvent(TBGameMode game, int keyBinding) {
		super(game, keyBinding);
	}

}
