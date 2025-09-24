package learning;

import java.util.List;

public class History {

	private String lastButOneTag;
	
	private String lastTag;
	
	private List<String> tokens;
	
	private int position;

	public History(String lastButOneTag, String lastTag, List<String> tokens,
			int position) {
		super();
		this.lastButOneTag = lastButOneTag;
		this.lastTag = lastTag;
		this.tokens = tokens;
		this.position = position;
	}
	
	public String getLastButOneTag() {
		return lastButOneTag;
	}

	public String getLastTag() {
		return lastTag;
	}

	public List<String> getTokens() {
		return tokens;
	}

	public int getPosition() {
		return position;
	}
}