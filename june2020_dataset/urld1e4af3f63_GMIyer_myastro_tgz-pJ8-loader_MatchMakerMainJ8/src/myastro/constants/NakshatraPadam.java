package myastro.constants;

public enum NakshatraPadam {
	PADAM_1(1), PADAM_2(2), PADAM_3(3), PADAM_4(4);

	private NakshatraPadam(int value) {
		this.value = value;
	}

	private int value;

	public int getValue() {
		return value;
	}

}
