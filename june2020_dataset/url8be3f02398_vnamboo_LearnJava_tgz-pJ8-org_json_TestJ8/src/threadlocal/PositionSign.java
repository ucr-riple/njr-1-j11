package threadlocal;

public enum PositionSign {
	POSITIVE(1), NEGATIVE(-1);

	private int signValue;

	public int getSignValue() {
		return signValue;
	}

	private PositionSign(int signValue) {
		this.signValue = signValue;
	}
}
