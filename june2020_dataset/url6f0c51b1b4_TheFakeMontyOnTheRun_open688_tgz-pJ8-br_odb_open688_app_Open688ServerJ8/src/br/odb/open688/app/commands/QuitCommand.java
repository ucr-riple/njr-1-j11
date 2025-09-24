package br.odb.open688.app.commands;

import br.odb.gameapp.ApplicationClient;
import br.odb.gameapp.ConsoleApplication;
import br.odb.gameapp.UserMetaCommandLineAction;

public class QuitCommand extends UserMetaCommandLineAction {

	public QuitCommand(ConsoleApplication application) {
		super(application);
	}

	@Override
	public void run( ConsoleApplication application, String operand ) {

		ApplicationClient client = application.getClient();

        client.sendQuit();

    }

    @Override
    public int requiredOperands() {
        return 0;
    }

    @Override
    public String toString() {
        return "quit";
    }
    
	@Override
	public String getHelp() {
		return "- Quit the game and (if applicable) send highscore to the online leaderboard.";
	}
    
}
