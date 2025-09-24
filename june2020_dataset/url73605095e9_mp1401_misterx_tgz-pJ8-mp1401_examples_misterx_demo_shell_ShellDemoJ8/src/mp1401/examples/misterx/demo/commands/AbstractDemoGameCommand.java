package mp1401.examples.misterx.demo.commands;

import mp1401.examples.misterx.demo.util.GameCycleSimplifier;
import mp1401.examples.misterx.model.commands.AbstractGameCommand;

public abstract class AbstractDemoGameCommand extends AbstractGameCommand {
	
	protected final GameCycleSimplifier gameCycleSimplifier;
	
	public AbstractDemoGameCommand(GameCycleSimplifier gameCycleSimplifier) {
		this.gameCycleSimplifier = gameCycleSimplifier;
	}

}
