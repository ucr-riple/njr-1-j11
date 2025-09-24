package pl.cc.core.cmd;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Przemysław Gałązka
 * @since 25-07-2013
 */
public class AbstractCommandFactory {

  protected Map<MatchFunction, CommandBuilder> functionCommandMap = new HashMap<MatchFunction, CommandBuilder>();


  interface MatchFunction {
    boolean checkIfLineMatch(String line);
  }

  interface CommandBuilder {
    Command buildUsing(String line);
  }


  public void registerCommand(MatchFunction matchFunction, CommandBuilder commandBuilder) {
    functionCommandMap.put(matchFunction, commandBuilder);
  }


  public Command matchBy(String line) {

    for (MatchFunction matchFunction : functionCommandMap.keySet()) {
      if (matchFunction.checkIfLineMatch(line)) {
        CommandBuilder commandBuilder = functionCommandMap.get(matchFunction);

        return commandBuilder
            .buildUsing(line);
      }
    }

    return null;
  }

}
