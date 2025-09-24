package patterns.gof.behavioral.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import patterns.gof.helpers.Client;

public class CommandClient extends Client {
	@Override
	public void main() {
		cleanOutput();
		
		Turtle turtle = new Turtle();
		
		Map<String, Command> commands = new HashMap<String, Command>();
		commands.put("right", new TurtleRightCommand(turtle));
		commands.put("left", new TurtleLeftCommand(turtle));
		commands.put("forward", new TurtleForwardCommand(turtle));
		commands.put("backward", new TurtleBackwardCommand(turtle)); 
		
		List<String> sequence = Arrays.asList(new String[]
				{"forward", "right", "forward", "left", "left", "backward"});
		
		CommandCenter center = new CommandCenter();
		
		for (String direction : sequence) {
			center.executeAndSave(commands.get(direction));
		}
		
		addOutput("coordinates: [" + turtle.getPosition().x + ", " + turtle.getPosition().y + "]");
		
		center.resetLastExecutes(3);
		
		addOutput("coordinates: [" + turtle.getPosition().x + ", " + turtle.getPosition().y + "]");
		
		super.main("Command");
	}
}