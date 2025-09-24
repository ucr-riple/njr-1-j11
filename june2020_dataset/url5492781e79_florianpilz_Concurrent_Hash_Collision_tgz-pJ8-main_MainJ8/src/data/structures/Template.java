package data.structures;

import java.util.Map.Entry;

public class Template implements Entry<String, String> {

	final String key;
	final String value;

	public Template(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String setValue(String value) {
		throw new UnsupportedOperationException(
				"Unsere Pairkomponenten sind fiiiiiinal!");
	}

}
