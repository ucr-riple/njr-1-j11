import java.util.EventObject;

public class ReadEvent extends EventObject {

	private byte[] utf;

	public ReadEvent(Object source, byte[] utf) {
		super(source);
		this.utf = utf;
	}

	public synchronized byte[] getReadInput() {
		return this.utf;
	}
}