package drivenow.interfaces;

import drivenow.Systemereignis;

/**
 * Interface fuer die FahrlehrerinBoundary.
 * 
 * Dieses Interface darf nicht veraendert werden.
 * 
 * @author daniel
 * 
 */
public interface IFahrlehrerinBoundary {

	/**
	 * Mit dieser Methode loggt sich die Fahrlehrerin im System ein und ruft
	 * alle fuer sie eingetragenen Unterrichtsstunden ab. Passt der Name der
	 * Fahrlehrerin nicht zum Kennzeichen, wird eine entsprechende Fehlermeldung
	 * ausgegeben. Die gleiche Fehlermeldung wird ausgegeben, wenn es keine
	 * Fahrlehrerin mit dem Namen gibt. Stimmen die Logindaten, wird eine
	 * Erfolgsmeldung ausgegeben und im Systemereignis wird eine leserlich
	 * aufbereitete Uebersicht aller Unterrichtsstunden der Lehrerin
	 * zurueckgegeben. Stimmen die Logindaten, aber existieren keine
	 * Unterrichtsstunden fuer die Fahrlehrerin wird der Text
	 * "Keine Unterrichtsstunden fuer diese Fahrlehrerin vorhanden"
	 * zurueckgeliefert. Der Text wird im Feld Text des Systemerieignisses
	 * transportiert und NICHT direkt ausgegeben.
	 * 
	 * PRE: true.
	 * 
	 * @param name
	 *            Der Name der Fahrlehrerin.
	 * 
	 * @param kennzeichen
	 *            Das Kennzeichen des Autos der Fahrlehrerin.
	 * 
	 * @return {@link Systemereignis} mit Nachricht ueber Erfolg und
	 *         Textdarstellung aller Unterrichtsstunden der Fahrlehrerin oder
	 *         Nachricht ueber den Fehlschlag durch falsche Logindaten.
	 */
	public Systemereignis einloggenUndUnterrichtsstundenZurueckgeben(
			String name, String kennzeichen);

	/**
	 * Diese Methode gibt eine Nachricht ueber das Anzeigen der
	 * Unterrichtsstunden und eine Repraesentation des Datenbestandes aller
	 * Unterrichtsstunden als Text im Systemereignis zurueck. Sie dient dem
	 * Debugging und hilft, den Ueberblick zu behalten. Alle vorhandenen
	 * Unterrichtsstunden mit verantwortlicher Lehrerin, Datum und Art werden
	 * moeglichst leserlich aufbereitet. Sortiert nach Fahrlehrerin. Existieren
	 * keine Unterrichtsstunden wird der Text
	 * "Keine Unterrichtsstunden im System vorhanden" uebermittelt. Der Text
	 * wird zurueckgegeben und NICHT direkt ausgegeben.
	 * 
	 * PRE: true.
	 * 
	 * @return String mit Datenbestand als lesbar formatierter Text
	 */
	public String unterrichtsstundenZurueckgeben();

}
