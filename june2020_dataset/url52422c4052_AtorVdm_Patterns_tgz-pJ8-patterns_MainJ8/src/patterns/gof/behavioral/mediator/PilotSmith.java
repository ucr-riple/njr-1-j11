package patterns.gof.behavioral.mediator;

public class PilotSmith extends Pilot {
	public PilotSmith(Mediator mediator) {
		super(mediator);
	}

	@Override
	public void notify(String message) {
		MediatorClient.addOutput("pilot Smith gets message > " + message);
	}
}