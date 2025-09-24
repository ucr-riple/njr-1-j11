
package myastro.constants;
public enum Rashi {

	MESA(Graha.MANGAL),

	VRISABHA(Graha.SHUKRA),

	MITHUNA(Graha.BUDHA),

	KARKATA(Graha.CHANDRA),

	SIMHA(Graha.SURYA),

	KANYA(Graha.BUDHA),

	TULA(Graha.SHUKRA),

	VRISHCIKA(Graha.MANGAL),

	DHANUS(Graha.BRIHASPATI),

	MAKARA(Graha.SHANI),

	KUMBHA(Graha.SHANI),

	MINA(Graha.BRIHASPATI);

	private Rashi(Graha rashiLordName) {
		this.rashiAthipati = rashiLordName;
	}

	private Graha rashiAthipati;

	public Graha getRashiAthipati() {
		return rashiAthipati;
	}
	
	public String toDetailString() {
		StringBuilder info = new StringBuilder();
		info.append(getRashiAthipati() + "\n");
		return info.toString();
	}

}
