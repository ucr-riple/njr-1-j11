package patterns.gof.behavioral.command;

public class TurtleLeftCommand extends Command {
	public TurtleLeftCommand(Turtle turtle) {
		super(turtle);
	}

	@Override
	public void execute() {
		getTurtle().moveLeft();
		
	}

	@Override
	public void undo() {
		getTurtle().moveRight();
	}
}