import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Christian Kletzander
 * 
 */
public class Song implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private Zeitraum zeitraum;
	private List<Variante> varianten;

	// Konstruktor
	public Song(String name, Zeitraum zeitraum, List<Variante> varianten) {
		this.name = name;
		this.zeitraum = zeitraum;
		this.varianten = varianten;
	}

	public List<Variante> getVarianten() {
		return varianten;
	}

	public String toString() {
		return name;
	}

	public String toDetailString() {
		return toString() + " " + zeitraum + " " + varianten;
	}

	/**
	 * 
	 * @author Koegler Alexander
	 * 
	 */
	public static class ZeitpunktSelektor implements Selector<Song> {
		private Date zeitpunkt;

		public ZeitpunktSelektor(Date zeitpunkt) {
			this.zeitpunkt = zeitpunkt;
		}

		@Override
		public boolean select(Song item) {
			return item.zeitraum.inZeitraum(zeitpunkt);
		}

	}

	/**
	 * 
	 * @author Koegler Alexander
	 * 
	 */
	public static class NameSelektor implements Selector<Song> {

		private String name;

		public NameSelektor(String name) {
			this.name = name;
		}

		@Override
		public boolean select(Song item) {
			return item.name.compareToIgnoreCase(name) == 0;
		}
	}

	/**
	 * Selektiert alle Songs, die bei einem Termin zur verfuegung stehen.
	 * 
	 * @author Peter Pilgerstorfer
	 * 
	 */
	public static class TerminSelector implements Selector<Song> {
		private Termin termin;

		public TerminSelector(Termin termin) {
			this.termin = termin;
		}

		@Override
		public boolean select(Song item) {
			return item.zeitraum.enthaelt(termin.getZeitraum());
		}
	}
}
