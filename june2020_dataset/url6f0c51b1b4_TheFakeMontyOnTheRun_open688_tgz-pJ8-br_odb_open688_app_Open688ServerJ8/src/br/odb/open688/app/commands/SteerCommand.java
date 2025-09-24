package br.odb.open688.app.commands;

import br.odb.gameapp.ApplicationClient;
import br.odb.gameapp.ConsoleApplication;
import br.odb.gameapp.UserCommandLineAction;
import br.odb.open688.app.Open688Server;
import br.odb.open688.simulation.ship.Helm;
import br.odb.open688.simulation.ship.Submarine;

public class SteerCommand extends UserCommandLineAction {

	@Override
	public void run(ConsoleApplication application, String operand)
			throws Exception {

		ApplicationClient client = application.getClient();
		Open688Server server = ( ( Open688Server )application );
		
		Submarine submarine = (Submarine) server.getMission().getPlayerCapitalShip();
		Helm helm = ( (Helm )submarine.getShipPart( "Helm" ) );
		helm.setNewHeading( Integer.parseInt( operand ) );
		client.printNormal( "Changing course to " + operand + ", my captain." );
	}

	@Override
	public String toString() {
		return "steer";
	}

	@Override
	public int requiredOperands() {
		return 1;
	}

	@Override
	public String getHelp() {
		return "change course of your capital ship";
	}
}
