package patterns.gof.behavioral.mediator;

public abstract class Mediator {
	public abstract void send(String message, Pilot pilot);
}