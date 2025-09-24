package patterns.gof.behavioral.command;

public class TurtleForwardCommand extends Command {
	public TurtleForwardCommand(Turtle turtle) {
		super(turtle);
	}

	@Override
	public void execute() {
		getTurtle().moveForward();
		
	}

	@Override
	public void undo() {
		getTurtle().moveBackward();
	}
}