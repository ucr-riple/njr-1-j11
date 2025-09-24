package br.odb.open688.app.commands;

import br.odb.gameapp.ConsoleApplication;
import br.odb.open688.app.Open688Server;

public class StepCommand extends br.odb.gameapp.UserMetaCommandLineAction {

	public StepCommand(ConsoleApplication application) {
		super(application);
	}

	@Override
	public void run(ConsoleApplication application, String operand)
			throws Exception {

		Open688Server server = ( ( Open688Server )application );
		server.update( Long.parseLong( operand ) );
		server.getClient().printVerbose( "ticking the clock in " + operand + "ms" );
	}

	@Override
	public String toString() {
		return "step";
	}

	@Override
	public int requiredOperands() {
		return 1;
	}

	@Override
	public String getHelp() {
		return "provides the simulation with a clock tick (acording to operand, in milisseconds)";
	}
}
