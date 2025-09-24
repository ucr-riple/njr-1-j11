package input;

import modes.TBGameMode;
/**
 * CommandSelectEvent for Produce
 * @author prithvi
 *
 */
public class ProduceCommandEvent extends CommandSelectEvents{

	public ProduceCommandEvent(TBGameMode game, int keyBinding) {
		super(game, keyBinding);
	}

	protected String getCommandName() {
		return "Produce";
	}

}
