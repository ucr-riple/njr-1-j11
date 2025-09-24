package mp1401.examples.misterx.demo.commands;

import mp1401.examples.misterx.demo.util.GameCycleSimplifier;


public class PlayRoundGameCommand extends AbstractDemoGameCommand {
	
	private final String misterXDestination;
	private final String[] detectivesDestinations;

	public PlayRoundGameCommand(GameCycleSimplifier gameCycleSimplifier, String misterXDestination, String...detectivesDestinations) {
		super(gameCycleSimplifier);
		this.misterXDestination = misterXDestination;
		this.detectivesDestinations = detectivesDestinations;
	}

	@Override
	public void execute() {
		gameCycleSimplifier.playRound(misterXDestination, detectivesDestinations);
	}

}
