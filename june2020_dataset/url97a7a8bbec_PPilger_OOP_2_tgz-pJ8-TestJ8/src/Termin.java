import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Speichert Ort, Zeitraum, Dauer ab. Bietet Methoden fuer die kaufmaennische
 * Berechnungslehre.
 * 
 * @author Koegler Alexander
 * 
 */
public class Termin implements Serializable {
	private static final long serialVersionUID = 1L;

	private Typ typus;
	private Ort ort;
	private Zeitraum zeitraum;
	private Posten posten;
	private List<Mitglied> teilnehmer; // aenderungen sind nicht zugelassen

	private Termin orig;

	private Termin() {
	}

	public Termin(Typ typus, Ort ort, Date von, Date bis, double ausgaben,
			double einnahmen, List<Mitglied> teilnehmer) {
		this.typus = typus;
		this.ort = ort;
		this.zeitraum = new Zeitraum(von, bis);
		this.posten = new Posten(einnahmen, ausgaben, typus.toString(), bis);
		this.teilnehmer = teilnehmer;
		this.orig = null;
	}
	
	public Zeitraum getZeitraum() {
		return zeitraum;
	}
	
	public Posten getPosten() {
		return posten;
	}

	public double getAusgaben() {
		return posten.getAusgaben();
	}

	public double getEinnahmen() {
		return posten.getEinnahmen();
	}

	/**
	 * @return Teilnehmerliste. Diese darf nicht geaendert werden!
	 */
	public List<Mitglied> getTeilnehmer() {
		return teilnehmer;
	}

	/**
	 * Legt eine Kopie des Termins auf den Undo-Stack.
	 */
	private void prepareUpdate() {
		Termin other = new Termin();
		other.typus = typus;

		// flache kopie (kann nicht geaendert werden, da privat)
		other.zeitraum = zeitraum;

		// flache kopie (eine aenderung in ort aendert nichts an der bedeutung)
		other.ort = ort;

		// flache kopie (unveraenderbar)
		other.posten = posten;

		// flache kopie (aenderungen sind nicht zugelassen)
		other.teilnehmer = teilnehmer;

		// haenge other hinter this in die historie ein
		other.orig = orig;
		this.orig = other;
	}

	public boolean undo() {
		if (orig == null) {
			return false;
		}

		meldeUpdate("zurueckgesetzt auf vorige Version");

		this.typus = orig.typus;
		this.ort = orig.ort;
		this.zeitraum = orig.zeitraum;
		this.posten = orig.posten;
		this.teilnehmer = orig.teilnehmer;
		this.orig = orig.orig;

		return true;
	}

	/**
	 * Benachrichtigt alle Teilnehmer ueber die gemachte Aenderung.
	 * @param aenderung
	 */
	private void meldeUpdate(String aenderung) {
		for (Mitglied t : teilnehmer) {
			t.sende(orig + " wurde geaendert: " + aenderung);
		}
	}

	/**
	 * @author Christian Kletzander
	 * @param ort
	 *            ueberspeichern des Ortes
	 */
	public void setOrt(Ort ort) {
		this.prepareUpdate();
		this.ort = ort;
		this.meldeUpdate(orig.ort + " -> " + ort);
	}

	/**
	 * @author Christian Kletzander
	 * @param zeitraum
	 *            ueberspeichern des Zeitraums
	 */
	public void setZeitraum(Date von, Date bis) {
		this.prepareUpdate();
		this.zeitraum = new Zeitraum(von, bis);
		this.meldeUpdate(orig.zeitraum + " -> " + zeitraum);
	}

	public void setAusgaben(double kosten) {
		this.prepareUpdate();
		this.posten = new Posten(posten.getEinnahmen(), kosten,
				typus.toString(), zeitraum.getLast());
		this.meldeUpdate("Kosten: " + orig.getAusgaben() + " -> "
				+ getAusgaben());
	}

	public void setEinnahmen(double umsatz) {
		this.prepareUpdate();
		this.posten = new Posten(umsatz, posten.getAusgaben(),
				typus.toString(), zeitraum.getLast());
		this.meldeUpdate("Umsatz: " + orig.getEinnahmen() + " -> "
				+ getEinnahmen());
	}

	@Override
	public String toString() {
		return typus + ": " + ort + " "
				+ zeitraum.toString(new SimpleDateFormat("dd.MM.yyyy hh:mm"));
	}

	public String toDetailString() {
		return String.format("%s, Kosten: %,.2f, Umsatz: %,.2f", toString(),
				posten.getAusgaben(), posten.getEinnahmen());
	}

	public static enum Typ {
		Probe, Auftritt;
	}

	/**
	 * Selektiert jene Termine in denen ein gegebenes Mitglied auch beteiligt
	 * ist
	 * 
	 * @author VHD
	 * 
	 */
	public static class TeilnehmerSelektor implements Selector<Termin> {
		private Mitglied m;

		public TeilnehmerSelektor(Mitglied m) {
			this.m = m;
		}

		@Override
		public boolean select(Termin item) {
			return item.teilnehmer.contains(m);
		}
	}

	public static class ZeitraumSelektor implements Selector<Termin> {

		private Zeitraum zeitraum;

		public ZeitraumSelektor(Zeitraum zeitraum) {
			this.zeitraum = zeitraum;
		}

		@Override
		public boolean select(Termin item) {
			return this.zeitraum.enthaelt(item.zeitraum);
		}

	}

	public static class TypSelektor implements Selector<Termin> {
		private Typ typus;

		public TypSelektor(Typ typus) {
			this.typus = typus;
		}

		@Override
		public boolean select(Termin item) {
			return this.typus == item.typus;
		}

	}
}
