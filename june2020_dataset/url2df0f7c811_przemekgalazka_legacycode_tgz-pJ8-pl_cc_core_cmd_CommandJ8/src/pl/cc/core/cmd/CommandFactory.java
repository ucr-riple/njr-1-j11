package pl.cc.core.cmd;

/**
 * @author Przemysław Gałązka
 * @since 25-07-2013
 */
public class CommandFactory extends AbstractCommandFactory {

  public CommandFactory() {
    registerCommand(new MatchFunction() {
                      @Override
                      public boolean checkIfLineMatch(String line) {
                        return Command.equalsExtended(line, "+OK LOGGED IN");
                      }
                    }, new CommandBuilder() {
                      @Override
                      public Command buildUsing(String line) {
                        return new CommandLoggedIn(line);
                      }
                    }
    );


    registerCommand(new MatchFunction() {
                      @Override
                      public boolean checkIfLineMatch(String line) {
                        return Command.equalsExtended(line, "Welcome. Please authorize.");
                      }
                    }, new CommandBuilder() {
                      @Override
                      public Command buildUsing(String line) {
                        return new CommandWelcome(line);
                      }
                    }
    );


    registerCommand(new MatchFunction() {
                      @Override
                      public boolean checkIfLineMatch(String line) {
                        return Command.getValuesINFO(line);

                      }
                    }, new CommandBuilder() {
                      @Override
                      public Command buildUsing(String line) {
                        return new InfoVersion(line);
                      }
                    }
    );
  }


}
