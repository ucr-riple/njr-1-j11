package pl.cc.core.cmd;

/**
 * Komenta Welcome.
 * CCProxy gotowe do przetwarzania zdarzeń
 *
 * @since 2007-12-18
 */
public class CommandWelcome extends Command {


    public CommandWelcome(String orginalLine) {
        super(orginalLine);
    }

    public static Command factoryInt(String line){
        if (Command.equalsExtended(line, "Welcome. Please authorize.")){
            return new CommandWelcome(line);
        } else {
            return null;
        }
    }

  @Override
    public int getType() {
        return CMD_WELCOME;
    }


}
