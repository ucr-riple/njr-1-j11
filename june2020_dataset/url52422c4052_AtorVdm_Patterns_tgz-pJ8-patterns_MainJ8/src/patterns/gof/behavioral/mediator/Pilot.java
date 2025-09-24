package patterns.gof.behavioral.mediator;

public abstract class Pilot {
	private Mediator mediator;
	
	public Pilot(Mediator mediator) {
		this.mediator = mediator;
	}
	
	public void send(String message) {
		mediator.send(message, this);
	}
	
	public abstract void notify(String message);
}