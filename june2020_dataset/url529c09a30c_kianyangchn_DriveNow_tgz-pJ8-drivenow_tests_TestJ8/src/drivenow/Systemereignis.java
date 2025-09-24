package drivenow;


/**
 * Klasse fuer die Systemereignisse.
 * 
 * Diese Klasse darf nicht veraendert werden.
 * 
 * @author daniel
 * 
 */
public class Systemereignis {

	/**
	 * Jedes Systemereignis enthaelt eine Nachricht.
	 */
	private Nachricht nachricht;

	/**
	 * Ist das Systemereignis eine Antwort auf das Erstellen eines ID
	 * tragenden Objektes, wird die zugewiesene ID in diesem Feld zurueckgegeben.
	 */
	private String ID;

	/**
	 * Ist das Systemereignis eine Antwort auf die Anfrage nach einem
	 * Datenbestand, wird dieser als Textdarstellung in diesem Feld
	 * transportiert.
	 */
	private String text;

	public Systemereignis(Nachricht nachricht) {
		this.nachricht = nachricht;
	}

	public Nachricht getNachricht() {
		return nachricht;
	}

	public String getID() {
		return ID;
	}

	public String getText() {
		return text;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return nachricht.name();
	}

	public enum Nachricht {
		Fahrschulauto_erfolgreich_hinzugefuegt, 
		Auto_mit_kennzeichen_bereits_vorhanden, 
		Fahrlehrerin_erfolgreich_hinzugefuegt, 
		Fahrlehrerin_nicht_hinzugefuegt_Auto_unbekannt, 
		Fahrlehrerin_nicht_hinzugefuegt_Auto_vergeben, 
		Fahrschueler_erfolgreich_hinzugefuegt, 
		Fahrschueler_nicht_hinzugefuegt_keine_Fahrlehrerin_vorhanden, 
		Theoriestunde_erfolgreich_hinzugefuegt, 
		Theoriestunde_nicht_hinzugefuegt_Raum_belegt, 
		Theoriestunde_nicht_hinzugefuegt_keine_Lehrerin_verfuegbar, 
		Fahrstunde_erfolgreich_eingetragen, 
		Fahrstunde_nicht_eingetragen_Lehrerin_nicht_verfuegbar, 
		Theoriestunde_erfolgreich_vermerkt, 
		Theoriestunde_nicht_vermerkt_bereits_vermerkt, 
		Theoriestunde_nicht_vermerkt_bereits_genug_Grundlagen, 
		Theoriestunde_nicht_vermerkt_bereits_Sonderthema, 
		Fahrstunde_erfolgreich_geloescht, 
		Fahrstunde_nicht_geloescht_Stunde_existiert_nicht, 
		Theoriepruefungszulassung_erfolgreich_geprueft, 
		Theoriepruefungszulassung_nicht_erfuellt_Grundlagen_fehlen, 
		Theoriepruefungszulassung_nicht_erfuellt_Sonderstunden_fehlen,
		Theoriepruefungsbestehen_erfolgreich_eingetragen,
		Praxispruefung_erfolgreich_eingtragen, 
		Praxispruefung_nicht_eingetragen_Sonderstunden_fehlen, 
		Praxispruefung_nicht_eingetragen_Theoriepruefung_fehlt, 
		Praxispruefung_nicht_eingetragen_Fahrlehrerin_nicht_verfuegbar, 
		Fahrlehrerin_Login_erfolgreich, 
		Fahrlehrerin_Login_nicht_erfolgreich
	}

}