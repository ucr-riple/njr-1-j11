package patterns.gof.behavioral.command;

public class TurtleRightCommand extends Command {
	public TurtleRightCommand(Turtle turtle) {
		super(turtle);
	}

	@Override
	public void execute() {
		getTurtle().moveRight();
		
	}

	@Override
	public void undo() {
		getTurtle().moveLeft();
	}
}