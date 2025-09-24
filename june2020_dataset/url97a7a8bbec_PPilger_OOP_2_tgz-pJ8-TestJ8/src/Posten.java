import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 * 
 * @author Christian Kletzander
 * 
 */

public class Posten implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double einnahmen;
	private double ausgaben;
	private String bezeichnung;
	private Date datum;

	public Posten(double einnahmen, double ausgaben, String bezeichnung,
			Date datum) {

		this.einnahmen = einnahmen;
		this.ausgaben = ausgaben;
		this.bezeichnung = bezeichnung;
		this.datum = datum;

	}

	public static class ZeitraumSelektor implements Selector<Posten> {

		private Zeitraum zeitraum;

		public ZeitraumSelektor(Zeitraum zeitraum) {
			this.zeitraum = zeitraum;
		}

		@Override
		public boolean select(Posten item) {
			return this.zeitraum.inZeitraum(item.datum);
		}

	}

	public double getEinnahmen() {
		return this.einnahmen;
	}

	public double getAusgaben() {
		return this.ausgaben;
	}

	public String toString() {
		String gewinn = String.format("%+,.2f", (einnahmen - ausgaben));
		return this.bezeichnung + ": " + gewinn;
	}

	public String toDetailString() {
		String datum = DateFormat.getDateInstance().format(this.datum);
		String einAus = String.format("+%,.2f/-%,.2f", einnahmen, ausgaben);
		return datum + ": " + this.bezeichnung + " (" + einAus + ")";
	}
}
