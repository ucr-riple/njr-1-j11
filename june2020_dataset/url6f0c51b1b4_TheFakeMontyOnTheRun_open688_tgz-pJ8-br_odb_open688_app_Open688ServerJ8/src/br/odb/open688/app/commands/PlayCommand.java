package br.odb.open688.app.commands;

import br.odb.gameapp.ConsoleApplication;
import br.odb.gameapp.UserMetaCommandLineAction;
import br.odb.open688.app.Open688Server;

public class PlayCommand extends UserMetaCommandLineAction {

	public PlayCommand(ConsoleApplication application) {
		super(application);
	}

	@Override
	public void run(ConsoleApplication application, String operand)
			throws Exception {

		Open688Server server = ( ( Open688Server )application );
		server.play();
		server.getClient().printVerbose( "Simulation resumed." );
	}

	@Override
	public String toString() {
		return "play";
	}

	@Override
	public int requiredOperands() {
		return 0;
	}

	@Override
	public String getHelp() {
		return "puts the simulation in a continuos running state";
	}
}
