package normchan.crapssim.engine.event;

public class SessionEvent extends GameEvent {
	public enum EventType {
		BEGIN, END
	}

	private final EventType type;

	public SessionEvent(EventType type, String message) {
		super(message);
		this.type = type;
	}

	public EventType getType() {
		return type;
	}
}
