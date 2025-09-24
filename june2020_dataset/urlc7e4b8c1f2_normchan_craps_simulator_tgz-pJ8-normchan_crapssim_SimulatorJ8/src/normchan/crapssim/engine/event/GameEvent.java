package normchan.crapssim.engine.event;

public class GameEvent {
	private final String message;

	public GameEvent(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
