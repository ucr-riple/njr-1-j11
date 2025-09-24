package input;

import modes.TBGameMode;
/**
 * CommandSelectEvent for Move
 * @author prithvi
 *
 */
public class MoveCommandEvent extends CommandSelectEvents {

	public MoveCommandEvent(TBGameMode game, int keyBinding) {
		super(game, keyBinding);
	}

	protected String getCommandName() {
		return "Move";
	}
}
