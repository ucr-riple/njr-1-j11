package patterns.gof.behavioral.command;

import java.util.ArrayList;
import java.util.List;

public class CommandCenter {
	private List<Command> history = new ArrayList<Command>();
	
	public void executeAndSave(Command command) {
		command.execute();
		history.add(command);
	}
	
	public void resetLastExecute() {
		resetLastExecutes(1);
	}
	
	public void resetLastExecutes(int count) {
		Command command;
		for (int i = getMin(history.size(), count); i > 0; i--) {
			command = history.get(history.size() - 1);
			command.undo();
			history.remove(command);
		}
	}
	
	private int getMin(int a, int b) {
		return b < a? b: a;
	}
}