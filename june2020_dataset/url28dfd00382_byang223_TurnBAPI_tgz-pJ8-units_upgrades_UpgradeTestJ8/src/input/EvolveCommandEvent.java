package input;

import modes.TBGameMode;
/**
 * CommandSelectEvent for Evolve
 * @author prithvi
 *
 */
public class EvolveCommandEvent extends CommandSelectEvents{

	public EvolveCommandEvent(TBGameMode game, int keyBinding) {
		super(game, keyBinding);
	}

	protected String getCommandName() {
		return "Evolve";
	}

}