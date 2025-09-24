package myastro.constants.matchmaker;

public enum MatchResult {

	UTTAMA(1.0),

	MADHYAMA(0.5),

	ADHAMA(0.0),

	DEPENDS(0.0);

	private MatchResult(double value) {
		this.value = value;
	}

	private double value;

	public double getValue() {
		return value;
	}

}
