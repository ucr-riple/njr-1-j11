package myastro.constants;

public enum Nakshatra {

	ASHVINI(Boolean.FALSE),

	BHARANI(Boolean.FALSE),

	KRITTIKA(Boolean.TRUE),

	ROHINI(Boolean.FALSE),

	MRIGASHIRSHA(Boolean.TRUE),

	ARDRA(Boolean.FALSE),

	PUNARVASU(Boolean.TRUE),

	PUSHYA(Boolean.FALSE),

	ASHLESHA(Boolean.FALSE),

	MAGHA(Boolean.FALSE),

	PURVA_PHALGUNI(Boolean.FALSE),

	UTTARA_PHALGUNI(Boolean.TRUE),

	HASTA(Boolean.FALSE),

	CHITRA(Boolean.TRUE),

	SVATI(Boolean.FALSE),

	VISHAKHA(Boolean.TRUE),

	ANURADHA(Boolean.FALSE),

	JYESHTHA(Boolean.FALSE),

	MULA(Boolean.FALSE),

	PURVA_ASHADHA(Boolean.FALSE),

	UTTARA_ASHADHA(Boolean.TRUE),

	SHRAVANA(Boolean.FALSE),

	DHANISHTA(Boolean.TRUE),

	SHATABHISHA(Boolean.FALSE),

	PURVA_BHADRAPADA(Boolean.TRUE),

	UTTARA_BHADRAPADA(Boolean.FALSE),

	REVATI(Boolean.FALSE);

	private Nakshatra(Boolean split) {
		this.split = split;
	}

	private Boolean split;

	public Boolean getSplit() {
		return split;
	}

	public String toDetailString() {
		StringBuilder info = new StringBuilder();
		info.append(getSplit() + "\n");
		return info.toString();
	}

}
