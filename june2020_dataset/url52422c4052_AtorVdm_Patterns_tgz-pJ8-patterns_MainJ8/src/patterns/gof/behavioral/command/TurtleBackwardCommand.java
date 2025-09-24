package patterns.gof.behavioral.command;

public class TurtleBackwardCommand extends Command {
	public TurtleBackwardCommand(Turtle turtle) {
		super(turtle);
	}

	@Override
	public void execute() {
		getTurtle().moveBackward();
		
	}

	@Override
	public void undo() {
		getTurtle().moveForward();
	}
}