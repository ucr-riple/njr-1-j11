package drivenow.interfaces;

import java.util.Date;

import drivenow.Systemereignis;

/**
 * Interface fuer die SekretaerBoundary.
 * 
 * Dieses Interface darf nicht veraendert werden.
 * 
 * @author daniel
 * 
 */
public interface ISekretaerBoundary {

	/**
	 * Diese Methode erstellt eine neue Fahrlehrerin im System. Eine Nachricht
	 * ueber den Erfolg mit der ID der Fahrlehrerin wird ausgegeben.
	 * 
	 * PRE: Der uebergebene Name ist ein "sinnvoller Wert". Es existiert noch
	 * keine Fahrlehrerin mit dem selben Namen im System.
	 * 
	 * @param name
	 *            Der Name der neuen Fahrlehrerin
	 * 
	 * @param kennzeichen
	 *            Das Kennzeichen des Autos, das der Fahrlehrerin zugewiesen
	 *            werden soll.
	 * 
	 * @return {@link Systemereignis} mit Nachricht ueber Erfolg und eindeutiger
	 *         ID der neuen Fahrlehrerin, Nachricht ueber Fehlschlag durch
	 *         bereits vergebenes Auto oder Nachricht ueber Fehlschlag durch
	 *         nicht vorhandenes Auto.
	 */
	public Systemereignis fahrlehrerinEintragen(String name, String kennzeichen);

	/**
	 * Diese Methode erstellt einen neuen Fahrschueler im System. Existiert
	 * keine Fahrlehrerin im System, kann auch kein Schueler angelegt werden.
	 * Eine entsprechende Fehlermeldung ist auszugeben. Existiert mindestens
	 * eine Fahrlehrerin wird der Fahrschueler angelegt und eine Nachricht ueber
	 * den Erfolg mit der ID des Fahrschuelers wird ausgegeben. Eine beliebige
	 * Fahrlehrerin wird dem Fahrschueler zugewiesen.
	 * 
	 * PRE: Der uebergebene Name und die Anschrift sind "sinnvolle Werte" und
	 * der Fahrschueler existiert noch nicht.
	 * 
	 * @param name
	 *            Der Name des neuen Fahrschuelers.
	 * 
	 * @param anschrift
	 *            Die Anschrift des neuen Fahrschuelers.
	 * 
	 * @return {@link Systemereignis} mit Nachricht ueber Erfolg und eindeutiger
	 *         ID des neuen Fahrschuelers oder Nachricht ueber Fehlschlag durch
	 *         keine verfuegbare Fahrlehrerin.
	 */
	public Systemereignis fahrschuelerEintragen(String name, String anschrift);

	/**
	 * Diese Methode erstellt ein neues Auto im System. Existiert bereits ein
	 * Auto mit dem Kennzeichen im System, wird kein neues angelegt. Stattdessen
	 * wird ein entsprechendes Systemereignis zurueckgegeben. Bei erfolgreichem
	 * Eintragen wird eine Nachricht ausgegeben.
	 * 
	 * PRE: Das uebergebene Kennzeichen ist ein "sinnvoller Wert".
	 * 
	 * @param kennzeichen
	 *            Das Kennzeichen des neuen Autos.
	 * 
	 * @return {@link Systemereignis} mit Nachricht ueber Erfolg oder Nachricht
	 *         ueber Fehlschlag durch bereits vorhandenes Auto mit demselben
	 *         Kennzeichen.
	 */
	public Systemereignis fahrschulautoEintragen(String kennzeichen);

	/**
	 * Diese Methode erstellt eine neue Theoriestunde im System. Existiert
	 * bereits eine andere Theoriestunde mit ueberschneidener Zeit im einzigen
	 * vorhandenen Raum, wird eine entsprechende Fehlermeldung ausgegeben. Jede
	 * Fahrlehrerin kann jedes Thema unterrichten. Ist der Raum frei, aber keine
	 * Fahrlehrerin verfuegbar, wird eine entsprechende Fehlermeldung
	 * ausgegeben. Sind alle Bedingungen erfuellt, wird eine Nachricht ueber den
	 * Erfolg ausgegeben. Eine Theoriestunde dauert immer 90 minuten.
	 * 
	 * PRE: Das uebergebene Thema und das Datum sind "sinnvolle Werte". Das
	 * Datum liegt in der Zukunft.
	 * 
	 * @param thema
	 *            Die Nummer des Themas der Theoriestunde
	 * 
	 * @param beginn
	 *            Das Datum, an dem die Theoriestunde stattfinden soll.
	 * 
	 * @return {@link Systemereignis} mit Nachricht ueber Erfolg oder Nachricht
	 *         ueber Fehlschlag durch belegten Raum oder Nachricht ueber
	 *         Fehlschlag durch keine verfuegbare Fahrlehrerin.
	 */
	public Systemereignis theoriestundeEintragen(int thema, Date beginn);

	/**
	 * Diese Methode erstellt eine neue Fahrstunde im System. Existiert bereits
	 * eine andere Fahrstunde fuer die Fahrlehrerin des Fahrschuelers, wird eine
	 * entsprechende Fehlermeldung ausgegeben. Sind der Schueler und die
	 * Lehrerin verfuegbar im betreffenden Zeitraum, wird die Fahrstunde
	 * angelegt und eine Nachricht ausgegeben. Ein Fahrschueler kann beliebig
	 * viele Fahrstunden von jeder Art absolvieren. Die nachricht an die
	 * Fahrlehrerin wird vernachlaessigt. Fahrpruefungen werden nicht ueber diese
	 * Methode eingetragen.
	 * 
	 * PRE: Die uebergebene ID, die Stundenart, das Datum und die Anzahl sind
	 * "sinnvolle Werte". Die ID gehoert zu einem existierenden Schueler, der
	 * Beginn liegt in der Zukunft und die Anzahl der Stunden ist groesser 0 und
	 * kleiner 10. Die Stundenart und die Anzahl der dafuer benoetigten Stunden
	 * passen sinnvoll zueinander. Die Stundenart ist nicht Pruefung. Trotzdem
	 * muessen Pruefungsfahrten bei der Verfuegbarkeitspruefung der Fahrlehrerin
	 * beruecksichtigt werden.
	 * 
	 * @param schuelerID
	 *            Die ID des Schuelers, der die Fahrstunde nimmt.
	 * 
	 * @param art
	 *            Die Stundenart der Fahrstunde.
	 * 
	 * @param beginn
	 *            Das Datum, an dem die Fahrstunde stattfinden soll.
	 * 
	 * @param anzahl
	 *            Die Anzahl der hintereinander stattfindenden Fahrstunden mit
	 *            je 45 min Dauer.
	 * 
	 * @return {@link Systemereignis} mit Nachricht ueber Erfolg oder Nachricht
	 *         ueber Fehlschlag durch nicht verfuegbare Fahrlehrerin.
	 */
	public Systemereignis fahrstundeEintragen(String schuelerID,
			Stundenart art, Date beginn, int anzahl);

	/**
	 * Diese Methode vermerkt den Besuch einer Theoriestunde fuer einen
	 * Schueler. Ist bereits vermerkt, dass der Schueler diese Theoriestunde
	 * besucht hat, wird eine entsprechende Fehlermeldung ausgegeben. Hat der
	 * Schueler noch keinen Vermerk zum Besuch dieser Fahrstunde, wird weiter
	 * unterschieden: Die zu vermerkende Theoriestunde ist eine Grundlagenstunde
	 * und er hat bereits zwoelf beliebige Grundlagenstunden besucht, wird eine
	 * entsprechende Fehlermeldung ausgegeben. Oder die zu vermerkende
	 * Theoriestunde ist ein Sonderthema und er hat bereits dieses Sonderthema
	 * vermerkt, wird entsprechende Fehlermeldung ausgegeben. Sind alle diese
	 * Fehlerbedingungen nicht erfuellt, kann der Vermerk eingetragen werden.
	 * 
	 * PRE: Die uebergebene ID und das Datum sind "sinnvolle Werte". Die ID
	 * gehoert zu einem existierenden Schueler und der Beginn passt du einer
	 * vorhandenen Theoriestunde in der Vergangenheit.
	 * 
	 * @param schuelerID
	 *            Die ID des Schuelers, fuer den die Theoriestunde vermerkt
	 *            wird.
	 * 
	 * @param beginn
	 *            Das Datum, an dem die Theoriestunde stattfand.
	 * 
	 * @return {@link Systemereignis} mit Nachricht ueber Erfolg oder Nachricht
	 *         ueber Fehlschlag durch bereits vermerkte Theoriestunde, durch
	 *         bereits besuchte Grundlagenstunden oder durch bereits besuchte
	 *         Sonderstunde.
	 */
	public Systemereignis theoriestundeVermerken(String schuelerID, Date beginn);

	/**
	 * Diese Methode loescht eine Fahrstunde. Existiert zu dem Schueler keine
	 * Fahrstunde am uebergebenen Datum im System, wird eine entsprechende
	 * Fehlermeldung ausgegeben. Existiert eine solche Fahrstunde, wird sie
	 * geloescht und eine Nachricht wird an den Sekretaer ausgegeben. Die
	 * Nachricht an die Fahrlehrerin wird hier vernachlaessigt. Auch wenn
	 * mehrere Fahrstunden hintereinander fuer den Fahrschueler eingetragen sind,
	 * wird nur die mit dem uebergebenen Beginn geloescht. Fahrpruefungen weren
	 * nicht ueber diese Methode geloescht.
	 * 
	 * PRE: Die uebergebene ID und das Datum sind "sinnvolle Werte". Die ID
	 * gehoert zu einem existierenden Schueler und der Beginn liegt in der
	 * Zukunft. Der Beginn stellt nicht den Beginn einer Fahrstunde vom Typ
	 * Pruefung dar.
	 * 
	 * @param schuelerID
	 *            Die ID des Schuelers, fuer den die Fahrstunde geloescht wird.
	 * 
	 * 
	 * @param beginn
	 *            Das Datum, an dem die Fahrstunde stattfinden sollte.
	 * 
	 * @return {@link Systemereignis} mit Nachricht ueber Erfolg oder Nachricht
	 *         ueber Fehlschlag durch nicht existierende Fahrstunde am gegebenen
	 *         Datum fuer den Schueler.
	 */
	public Systemereignis fahrstundeLoeschen(String schuelerID, Date beginn);

	/**
	 * Diese Methode gibt den Datenbestand als Text zurueck. Sie dient dem
	 * Debugging und hilft, den Ueberblick ueber vohandene Daten zu behalten. Es
	 * werden moeglichst leserlich aufbereitet die folgenden Daten
	 * zurueckgegeben: - alle vorhandenen Fahrschulautos mit Kennzeichen und der
	 * zugeordneten Fahrlehrerin, - alle Fahrlehrerinnen mit ID, geplanten
	 * Fahrstunden und geplanten Theoriestunden, - alle Fahrschueler mit ID,
	 * zugehoeriger Lehrerin, vermerkten Theoriestunden, geplanten Fahrstunden,
	 * Status der Theorieprueung (bestanden oder nicht bestanden) Der Text wird
	 * zurueckgegeben und NICHT direkt ausgegeben. Sind keine Daten im System
	 * wird der Text "Keine Daten im System" uebermittelt.
	 * 
	 * PRE: true.
	 * 
	 * @return String mit Datenbestand (leserlich formatierter Text)
	 */
	public String datenbestandZurueckgeben();

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
	public Systemereignis theoriepruefungszulassungUeberpruefen(
			String schuelerID);

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
	public Systemereignis theoriepruefungsbestehenEintragen(String schuelerID);

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
	public Systemereignis praxispruefungEintragen(String schuelerID, Date beginn);

	public enum Stundenart {
		Uebungsfahrt, Ueberlandfahrt, Autobahnfahrt, Beleuchtungsfahrt, Pruefung
	}

}
