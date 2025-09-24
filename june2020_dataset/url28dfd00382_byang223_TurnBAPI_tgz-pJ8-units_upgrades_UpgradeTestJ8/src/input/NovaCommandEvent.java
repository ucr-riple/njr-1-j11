package input;

import modes.TBGameMode;
/**
 * CommandSelectEvent for Nova
 * @author prithvi
 *
 */
public class NovaCommandEvent extends CommandSelectEvents{

	public NovaCommandEvent(TBGameMode game, int keyBinding) {
		super(game, keyBinding);
	}

	protected String getCommandName() {
		return "Nova";
	}
	
}
