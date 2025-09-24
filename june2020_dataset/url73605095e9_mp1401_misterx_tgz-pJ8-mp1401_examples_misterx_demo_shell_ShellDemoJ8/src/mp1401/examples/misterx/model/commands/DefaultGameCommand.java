package mp1401.examples.misterx.model.commands;

import mp1401.examples.misterx.model.util.Messsages;

public class DefaultGameCommand extends AbstractGameCommand {

	@Override
	public void execute() {
		Messsages.printMessage("No Action Performed");
	}

}
