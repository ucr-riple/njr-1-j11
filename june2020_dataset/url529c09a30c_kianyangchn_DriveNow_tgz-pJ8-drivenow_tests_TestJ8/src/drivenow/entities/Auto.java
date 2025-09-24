package drivenow.entities;

public class Auto {
	
	private String kennzeichen;
	private Fahrlehrerindaten fahrlehrerindaten;
	
	public Auto(String kennzeichen) {
		this.kennzeichen=kennzeichen;
	}
	
	/**
	 * Geben der Kennzeichen des Autos zurueck.
	 * @return
	 * 		Kennzeichen eines Autos
	 */
	public String get_kennzeichen() {
		return this.kennzeichen;
	}
	
	/**
	 * Geben den verantwortliche Fahrlehrerin fuer das Auto zurueck.
	 * @return
	 * 		Ein verantwortliche Fahrlehrerin.
	 */
	public Fahrlehrerindaten get_fahrlehrerin() {
		return this.fahrlehrerindaten;
	}
	
	/**
	 * Set einen Fahrlehrerin fuer das Auto.
	 * Dann ist er fuer das Auto verantwortlich
	 * @param f_new
	 * 		Ein Fahrlehrerin.
	 */
	public void set_fahrlehrerin(Fahrlehrerindaten f_new) {
		this.fahrlehrerindaten=f_new;
	}
	
	/**
	 * Print den Kennzeichen und den verantwortliche Fahrlehrerin eines Autos.
	 * @return
	 * 		Kennzeichen: <>, Fahrlehrerin: <>.
	 */
	@Override
	public String toString() {
		if (this.get_fahrlehrerin()!=null) {
			return "Kennzeichen: " + this.get_kennzeichen() + ", Fahrlehrerin: " + this.get_fahrlehrerin().get_name();
		}
		else {
			return "Kennzeichen: " + this.get_kennzeichen() + ", Fahrlehrerin: kein";
		}
	}
}
