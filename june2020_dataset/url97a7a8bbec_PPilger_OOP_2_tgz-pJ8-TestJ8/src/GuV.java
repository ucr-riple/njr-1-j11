import java.util.List;


/**
 * 
 * @author Christian Kletzander
 * 
 */

public class GuV extends Selection<Posten> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Band band;

	public GuV(Band band) {
		this.band = band;
	}

	/**
	 * Erstelle eine neue Termin Sammlung die auf den selben Daten wie
	 * <code>base</code> arbeitet. Es sind jedoch nur Elemente sichtbar, die von
	 * den Selektoren selektiert werden.
	 * 
	 * @param base
	 * @param selectors
	 */
	private GuV(Band band, GuV base, List<Selector<Posten>> selectors) {
		super(base, selectors);
		this.band = band;
	}

	/**
	 * Liefert eine Selektion der in diesem Objekt gespeicherten Posten. Mit
	 * den uebergebenen Selektoren kann bestimmt werden, welche Posten
	 * selektiert werden. Aenderungen in der zurueckgegebenen Selektion wirken
	 * sich direkt auf das Original aus.
	 * 
	 * @param selectors
	 * @return
	 */
	public GuV select(List<Selector<Posten>> selectors) {
		return new GuV(band, this, selectors);
	}

	/**
	 * Berechnet die Summe der Einnahmen aller selektierter Posten. Daber werden
	 * auch Posten der Termine beruecksichtigt.
	 * 
	 * @return Summe der Einnahmen der selektierten Posten
	 */
	public double getEinnahmen() {
		double einnahmen = 0;

		// Durchiterieren der gespeicherten Posten
		for (Posten posten : this) {
			einnahmen += posten.getEinnahmen();
		}

		// Durchiterieren aller Termine der Band
		for (Termin termin : band.getTermine()) {
			Posten posten = termin.getPosten();
			if (selected(posten)) {
				einnahmen += posten.getEinnahmen();
			}
		}

		return einnahmen;
	}

	/**
	 * Berechnet die Summe der Ausgaben aller selektierter Posten. Daber werden
	 * auch Posten der Termine beruecksichtigt.
	 * 
	 * @return Summe der Ausgaben der selektierten Posten
	 */
	public double getAusgaben() {
		double ausgaben = 0;

		// Durchiterieren der gespeicherten Posten
		for (Posten posten : this) {
			ausgaben += posten.getAusgaben();
		}

		// Durchiterieren aller Termine der Band
		for (Termin termin : band.getTermine()) {
			Posten posten = termin.getPosten();
			if (selected(posten)) {
				ausgaben += posten.getAusgaben();
			}
		}

		return ausgaben;
	}

	/**
	 * Berechnet den Gesamtgewinn aller selektierter Posten. Daber werden auch
	 * Posten der Termine beruecksichtigt.
	 * 
	 * @return Gesamtgewinn der selektierten Posten
	 */
	public double getGewinn() {
		double ausgaben = 0;

		// Durchiterieren der gespeicherten Posten
		for (Posten posten : this) {
			ausgaben += posten.getEinnahmen() - posten.getAusgaben();
		}

		// Durchiterieren aller Termine der Band
		for (Termin termin : band.getTermine()) {
			Posten posten = termin.getPosten();
			if (selected(posten)) {
				ausgaben += posten.getEinnahmen() - posten.getAusgaben();
			}
		}

		return ausgaben;
	}
}
