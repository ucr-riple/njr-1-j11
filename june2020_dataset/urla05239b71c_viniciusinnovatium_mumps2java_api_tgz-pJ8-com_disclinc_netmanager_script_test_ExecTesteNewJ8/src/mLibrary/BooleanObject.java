package mLibrary;

public class BooleanObject {
	private final Object value;
	private final boolean condition;

	private BooleanObject(boolean condition, Object valor) {
		this.condition = condition;
		this.value = valor;
	}
	
	public static BooleanObject valueOf(boolean condition, Object value) {
		return new BooleanObject(condition, value);
	}

	public Object getValue() {
		return value;
	}

	public boolean isTrue() {
		return condition;
	}
}
