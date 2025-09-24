package patterns.gof.behavioral.mediator;

public class PilotAnderson extends Pilot {
	public PilotAnderson(Mediator mediator) {
		super(mediator);
	}

	@Override
	public void notify(String message) {
		MediatorClient.addOutput("pilot Anderson gets message > " + message);
	}
}