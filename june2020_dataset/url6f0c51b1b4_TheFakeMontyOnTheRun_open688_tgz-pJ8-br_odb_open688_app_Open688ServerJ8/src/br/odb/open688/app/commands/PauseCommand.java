package br.odb.open688.app.commands;

import br.odb.gameapp.ConsoleApplication;
import br.odb.gameapp.UserMetaCommandLineAction;
import br.odb.open688.app.Open688Server;

public class PauseCommand extends UserMetaCommandLineAction {

	public PauseCommand(ConsoleApplication application) {
		super(application);
	}

	@Override
	public void run(ConsoleApplication application, String operand)
			throws Exception {

		Open688Server server = ( ( Open688Server )application );
		server.pause();
		server.getClient().printVerbose( "Simulation paused" );
	}

	@Override
	public String toString() {
		return "pause";
	}

	@Override
	public int requiredOperands() {
		return 0;
	}

	@Override
	public String getHelp() {
		return "pauses the simulation, so it can be verified in a step-by-step basis";
	}
}
