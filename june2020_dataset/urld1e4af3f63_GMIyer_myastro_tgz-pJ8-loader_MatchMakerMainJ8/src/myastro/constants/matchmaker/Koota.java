package myastro.constants.matchmaker;

public enum Koota {

	DINA("N"), GANA("N"), MAHENDRA("N"), STHREE_DEERKHA("N"), YONI("N"), RASHI("R"), RASHI_ATHIPATI("R"), VASHIYA("R"), RAJJU("N"), VEDHAI("N"), NAADI("N");

	private Koota(String kootaType) {
		this.kootaType = kootaType;
	}

	private String kootaType;

	public String getKootaType() {
		return kootaType;
	}

}
