package ori.mas.core;

public interface Percept {

	/**
	 * Percept type.
	 * <ul>
	 * <li>UNDEFINED : type has not been defined,</li>
	 * <li>POSITIVE : this belief is a new belief to considere,</li>
	 * <li>NEGATIVE : this belief is an old belief which is not true any longer.</li>
	 * </ul>
	 */
	public static enum TYPE {
		UNDEFINED,
		POSITIVE,
		NEGATIVE
	}

	/** @see .TYPE */
	public TYPE type();

	public Sensor source();
	public void setSource(Sensor value);

};

