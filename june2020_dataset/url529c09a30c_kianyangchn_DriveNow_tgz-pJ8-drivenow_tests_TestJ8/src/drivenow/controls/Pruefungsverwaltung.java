package drivenow.controls;

import java.util.ArrayList;
import java.util.Date;

import drivenow.*;
import drivenow.Systemereignis.Nachricht;
import drivenow.entities.*;
import drivenow.interfaces.ISekretaerBoundary.Stundenart;

public class Pruefungsverwaltung {

	private static Pruefungsverwaltung instance;
	private ArrayList<Fahrschueler> theoriepruefung_bestanden_fahrschueler;
	private ArrayList<Fahrschueler> fahrpruefung_bestanden_fahrschueler;
	private Unterrichtsverwaltung unterrichtsverwaltung;
	
	public Pruefungsverwaltung() {
		this.theoriepruefung_bestanden_fahrschueler = new ArrayList<Fahrschueler>();
		this.unterrichtsverwaltung = Unterrichtsverwaltung.getInstance();
	}
	
	public static Pruefungsverwaltung getInstance() {
		if (instance == null) {
			instance = new Pruefungsverwaltung();
		}
		return instance;
	}
	
	public void cleanInstance() {
		instance=null;
	}
	
	/**
	 * Diese Methode ueberprueft, ob ein Fahrschueler zur Theoriepruefung
	 * zugelassen ist. Hat der Schueler nicht genug Grundlagenstunden besucht,
	 * wird eine entsprechende Fehlermeldung ausgegeben. Hat der Schueler genug
	 * Grundlagenstunden besucht, aber nicht alle erforderlichen Sonderthemen,
	 * wird eine entsprechende Fehlermeldung ausgegeben. Hat der Schueler genug
	 * Grundlagenstunden und alle Sonderthemen besucht, wird die Zulassung durch
	 * eine Nachricht bestaetigt.
	 * 
	 * PRE: Die ID gehoert zu einem existierenden Schueler.
	 * 
	 * @param schuelerID
	 *            Die ID des Schuelers, fuer den die Theoriepruefungszulassung
	 *            geprueft wird.
	 * 
	 * @return {@link Systemereignis} mit Nachricht ueber Erfolg oder Nachricht
	 *         ueber Fehlschlag durch nicht genug besuchte Grundlagenstunden
	 *         oder nicht genug besuchte Sonderstunden.
	 */
	public Systemereignis theoriepruefungszulassungUeberpruefen(String schuelerID) {
		Fahrschueler fahrschueler = Ressourcenverwaltung.getInstance().find_fahrschueler(schuelerID);
		if (fahrschueler.get_anzahl_grundlagenstunden()<12) {
			return(new Systemereignis(Nachricht.Theoriepruefungszulassung_nicht_erfuellt_Grundlagen_fehlen));
		}
		else {
			if (fahrschueler.get_anzahl_sonderstunden()<2) {
				return(new Systemereignis(Nachricht.Theoriepruefungszulassung_nicht_erfuellt_Sonderstunden_fehlen));
			}
			else {
				return(new Systemereignis(Nachricht.Theoriepruefungszulassung_erfolgreich_geprueft));
			}
		}
	}
	
	/**
	 * Diese Methode traegt ein, dass ein Fahrschueler die Theoriepruefung
	 * bestanden hat. Eine Nachricht ueber den Erfolg wird ausgegeben.
	 * 
	 * PRE: Die ID gehoert zu einem existierenden, zur Pruefung zugelassenen
	 * Schueler. Das Bestehen wurde noch nicht eingetragen.
	 * 
	 * 
	 * @param schuelerID
	 *            Die ID des Schuelers, fuer den die bestandene Theoriepruefung
	 *            eingetragen wird.
	 * 
	 * 
	 * 
	 * @return {@link Systemereignis} mit Nachricht ueber Erfolg.
	 */
	public Systemereignis theoriepruefungsbestehenEintragen(String schuelerID) {
		Fahrschueler fahrschueler = Ressourcenverwaltung.getInstance().find_fahrschueler(schuelerID);
		if (!this.theoriepruefung_bestanden_fahrschueler.contains(fahrschueler)) {
			fahrschueler.set_theoriepruefungsstatus("Bestanden!");
			this.theoriepruefung_bestanden_fahrschueler.add(fahrschueler);
			return(new Systemereignis(Nachricht.Theoriepruefungsbestehen_erfolgreich_eingetragen));
		}
		else return null;
	}
	
	/**
	 * Diese Methode traegt eine Praxispruefung fuer einen Fahrschueler ein.
	 * Zunaechst wird dabei die Zulassung geprueft: Hat der Schueler weniger als
	 * 4 Autobahnfahrstunden oder weniger als 5 Ueberlandfahrstunden oder
	 * weniger als 3 Beleuchtungsfahrstunden, wird eine entsprechende
	 * Fehlermeldung ausgegeben. Hat der Schueler zwar alle Sonderfahrstunden
	 * absolviert, aber die bestandene Theoriepruefung ist noch nicht
	 * eingetragen, wird eine entsprechende Fehlermeldung ausgegeben. Hat der
	 * Schueler alle Sonderfahrstunden absolviert und die bestandene
	 * Theoriepruefung ist eingetragen, aber seine Fahrlehrerin ist nicht
	 * verfuegbar, wird eine entsprechende Fehlermeldung ausgegeben. Sind alle
	 * Bedingungen erfuellt, werden Fahrstunden mit der Stundenart Pruefung im
	 * System angelegt und eine Nachricht ueber den Erfolg ausgegeben. Eine
	 * Fahrpruefung dauert 180 Minuten.
	 * 
	 * PRE: Das Datum ist ein "sinnvoller Wert" und liegt in der Zukunft. Die ID
	 * gehoert zu einem existierenden Schueler. Alle eingetragenen Fahrstunden
	 * des Fahrschuelers bis zum uebergebenen Datum koennen als absolviert
	 * angesehen werden.
	 * 
	 * @param schuelerID
	 *            Die ID des Schuelers, fuer den die Zulassung geprueft wird.
	 * 
	 * @param beginn
	 *            Das Datum, an dem die Prxispruefung stattfinden soll.
	 * 
	 * @return {@link Systemereignis} mit Nachricht ueber Erfolg oder Nachricht
	 *         ueber Fehlschlag durch nicht genug absolvierte Sonderfahrstunden
	 *         oder Nachricht ueber Fehlschlag durch nicht bestandene
	 *         Theoriepruefung oder Nachricht ueber Fehlschlag durch nicht
	 *         verfuegbare Fahrlehrerin.
	 */
	public Systemereignis praxispruefungEintragen(String schuelerID, Date beginn) {
		Fahrschueler fahrschueler = Ressourcenverwaltung.getInstance().find_fahrschueler(schuelerID);
		int[] besucht_fahrstunden = new int[4];
		besucht_fahrstunden = fahrschueler.get_anzahl_fahrstunden();
		if (besucht_fahrstunden[1]<5 || besucht_fahrstunden[2]<4 || besucht_fahrstunden[3]<3) {
			return(new Systemereignis(Nachricht.Praxispruefung_nicht_eingetragen_Sonderstunden_fehlen));
		}
		else {
			if (!this.theoriepruefung_bestanden_fahrschueler.contains(fahrschueler)) {
				return (new Systemereignis(Nachricht.Praxispruefung_nicht_eingetragen_Theoriepruefung_fehlt));
			}
			else {
				Date end = (Date) beginn.clone();
				end.setMinutes(beginn.getMinutes()+180);
				if (!fahrschueler.get_fahrlehrerindaten().verfuegbar(beginn,end)) {
					return(new Systemereignis(Nachricht.Praxispruefung_nicht_eingetragen_Fahrlehrerin_nicht_verfuegbar));
				}
				else {
					this.unterrichtsverwaltung.fahrstundeEintragen(schuelerID, Stundenart.Pruefung, beginn, 1);
					return(new Systemereignis(Nachricht.Praxispruefung_erfolgreich_eingtragen));
				}
			}
		}
	}
}
