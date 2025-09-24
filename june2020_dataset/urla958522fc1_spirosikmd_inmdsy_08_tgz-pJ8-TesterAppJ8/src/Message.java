import java.io.Serializable;
import java.util.UUID;

public class Message implements Serializable {

	private UUID id;
	private String mes;

	public Message(UUID id, String mes) {
		this.id = id;
		this.mes = mes;
	}

	public UUID getID() {
		return this.id;
	}

	public String getMessage() {
		return this.mes;
	}
}