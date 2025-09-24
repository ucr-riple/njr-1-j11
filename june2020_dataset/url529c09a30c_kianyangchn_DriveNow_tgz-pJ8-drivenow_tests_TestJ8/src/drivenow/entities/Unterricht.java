package drivenow.entities;

import java.util.Date;

public interface Unterricht {
	/**
	 * Geben die Anfangszeit eines Unterricht zurueck.
	 * @return 
	 * 		die Anfangszeit eines Unterricht
	 */
	public Date get_Datum();
	
	/**
	 * Geben den verantwortliche Fahrlehrerin zurueck.
	 * @return
	 * 		ein verantwortliche Fahrlehrerin.
	 */
	public Fahrlehrerindaten get_fahrlehrerindaten();
}
