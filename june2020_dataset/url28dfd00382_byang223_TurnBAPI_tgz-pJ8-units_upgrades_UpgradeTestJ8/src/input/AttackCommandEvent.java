package input;

import modes.TBGameMode;
/**
 * CommandSelectEvent for attack
 * @author prithvi
 *
 */
public class AttackCommandEvent extends CommandSelectEvents {

	public AttackCommandEvent(TBGameMode game, int keyBinding) {
		super(game, keyBinding);
	}
	
	protected String getCommandName() {
		return "Attack";
	}
}
