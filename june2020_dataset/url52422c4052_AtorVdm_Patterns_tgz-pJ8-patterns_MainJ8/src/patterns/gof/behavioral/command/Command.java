package patterns.gof.behavioral.command;

public abstract class Command {
	private Turtle turtle;
	public abstract void execute();
	public abstract void undo();
	
	public Command(Turtle turtle) {
		this.turtle = turtle;
	}
	
	public Turtle getTurtle() {
		return turtle;
	}
}