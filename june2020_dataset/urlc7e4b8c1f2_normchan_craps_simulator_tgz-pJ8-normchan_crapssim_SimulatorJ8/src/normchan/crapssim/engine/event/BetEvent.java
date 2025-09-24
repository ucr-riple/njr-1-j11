package normchan.crapssim.engine.event;

public class BetEvent extends GameEvent {
	public enum EventType {
		UPDATE, RETRACT, WIN, LOSS, PUSH, NUMBER_ESTABLISHED, POINT_MADE
	}
	
	private final EventType type;
	private final int amount;

	public BetEvent(EventType type, String message) {
		super(message);
		this.type = type;
		this.amount = 0;
	}

	public BetEvent(EventType type, String message, int amount) {
		super(message);
		this.type = type;
		this.amount = amount;
	}

	public EventType getType() {
		return type;
	}

	public int getAmount() {
		return amount;
	}
}
