package risiko.tcp;

public class ClientState {
	
	private final String name;
	private String inputBuffer = "";
	private String outputBuffer = "";
	
	ClientState(String name){
		this.name=name;
	}
	
	String getName(){
		return name;
	}

	String getBuffer() {
		return inputBuffer;
	}

	void setBuffer(String buffer) {
		if (buffer==null){
			throw new IllegalArgumentException("buffer may not be null; enter an empty String instead");
		}
		this.inputBuffer = buffer;
	}

	public String getOutputBuffer() {
		return outputBuffer;
	}

	public void setOutputBuffer(String outputBuffer) {
		this.outputBuffer = outputBuffer;
	}
	
}
