package br.odb.open688.app.commands;

import br.odb.gameapp.ApplicationClient;
import br.odb.gameapp.ConsoleApplication;
import br.odb.gameapp.UserCommandLineAction;
import br.odb.open688.app.InvalidShipPart;
import br.odb.open688.app.Open688Server;
import br.odb.open688.simulation.ship.ShipPart;
import br.odb.open688.simulation.ship.Submarine;

public class ToggleCommand extends UserCommandLineAction {

	@Override
	public void run(ConsoleApplication application, String operand)
			throws Exception {

		ApplicationClient client = application.getClient();
		Open688Server server = ( ( Open688Server )application );
		
		Submarine submarine = (Submarine) server.getMission().getPlayerCapitalShip();
		ShipPart part = submarine.getShipPart( operand );
		
		if ( part == null ) {
			throw new InvalidShipPart();
		}
		part.setActive( !part.isActive() );
		
		client.printNormal( "toggling " + operand + ", my captain." );
	}

	@Override
	public String toString() {
		return "toggle";
	}

	@Override
	public int requiredOperands() {
		return 1;
	}

	@Override
	public String getHelp() {
		return "activates or deactivates a certain ship component";
	}

}
