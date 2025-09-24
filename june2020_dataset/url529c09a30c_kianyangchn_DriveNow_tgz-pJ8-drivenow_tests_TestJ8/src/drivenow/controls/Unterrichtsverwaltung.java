package drivenow.controls;

import java.util.ArrayList;
import java.util.Date;

import drivenow.Systemereignis;
import drivenow.Systemereignis.Nachricht;
import drivenow.entities.*;
import drivenow.interfaces.ISekretaerBoundary.Stundenart;

public class Unterrichtsverwaltung {

	private static Unterrichtsverwaltung instance;
	private ArrayList<Theoriestunde> theoriestunde;
	private ArrayList<Fahrstunde> fahrstunde;
	private Ressourcenverwaltung ressourcenverwaltung;
	
	public Unterrichtsverwaltung() {
		this.theoriestunde = new ArrayList<Theoriestunde>();
		this.fahrstunde = new ArrayList<Fahrstunde>();
		this.ressourcenverwaltung = Ressourcenverwaltung.getInstance();
	}
	
	public static Unterrichtsverwaltung getInstance() {
		if (instance == null) {
			instance = new Unterrichtsverwaltung();
		}
		return instance;
	}
	
	public void cleanInstance() {
		instance=null;
	}
	
	private Fahrschueler find_fahrschueler(String schuelerID) {
		ArrayList<Fahrschueler> fahrschueler = this.ressourcenverwaltung.get_fahrschueler();
		if (fahrschueler.size()!=0) {
			for (int i=0;i<fahrschueler.size();i++) {
				if (fahrschueler.get(i).get_ID()==schuelerID) {
					return fahrschueler.get(i);
				}
			}
		}
		return null;
	}
	
	private Theoriestunde find_theoriestunde(Date beginn) {
		if (this.theoriestunde.size()!=0) {
			for (int i=0;i<this.theoriestunde.size();i++) {
				if (this.theoriestunde.get(i).get_Datum().equals(beginn)) {
					return this.theoriestunde.get(i);
				}
			}
		}
		return null;
	}
	
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
	public Systemereignis unterrichtsstunden_zurueckgeben() {
		Systemereignis systemereignis = new Systemereignis(null);
		if (this.theoriestunde.size()==0 && this.fahrstunde.size()==0) {
			systemereignis.setText("Keine Unterrichtsstunden im System vorhanden");
			return systemereignis;
		}
		else {
			String text="Theoriestunden: \n";
			if (this.theoriestunde.size()!=0) {
				for (int i=0;i<this.theoriestunde.size();i++) {
					text = text + "Lehrerin: " + this.theoriestunde.get(i).get_fahrlehrerindaten().get_name() 
							+ ", ID: "+ this.theoriestunde.get(i).get_fahrlehrerindaten().get_lehrerinID()
							+ '\n'
							+ this.theoriestunde.get(i).toString()
							+ '\n';
				}
			}
			else {
				text = text + "Keine Theoriestunden!" + '\n';
			}
			text = text + '\n' + "Fahrstunden: \n";
			if (this.fahrstunde.size()!=0) {
				for (int i=0;i<this.fahrstunde.size();i++) {
					text =  text +  "Lehrerin: " + this.fahrstunde.get(i).get_fahrlehrerindaten().get_name() 
							+ ", ID: "+ this.fahrstunde.get(i).get_fahrlehrerindaten().get_lehrerinID()
							+ '\n'
							+ this.fahrstunde.get(i).toString()
							+ '\n';
				}
			}
			else {
				text = text + "Keine Fahrstunden!" + '\n';
			}
			systemereignis.setText(text);
			return systemereignis;
		}
	}
	
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
	public Systemereignis theoriestundeEintragen(int thema, Date beginn) {
		ArrayList<Fahrlehrerindaten> fahrlehrerin = ressourcenverwaltung.get_fahrlehrerin();
		Date end = (Date) beginn.clone();
		end.setMinutes(beginn.getMinutes()+90);
		for (int i=0;i<this.theoriestunde.size();i++) {
			Theoriestunde stunde = this.theoriestunde.get(i);
			if ((stunde.get_Datum().before(beginn) && stunde.get_end().after(beginn))
					|| (stunde.get_Datum().before(end) && stunde.get_end().after(end))
					|| stunde.get_Datum().equals(beginn)
					|| stunde.get_end().equals(end)) {
				return(new Systemereignis(Nachricht.Theoriestunde_nicht_hinzugefuegt_Raum_belegt));
			}
		}
		for (int i=0;i<fahrlehrerin.size();i++) {
			if (fahrlehrerin.get(i).verfuegbar(beginn,end)) {
				Theoriestunde new_ts = new Theoriestunde(thema,beginn);
				new_ts.set_fahrlehrerin(fahrlehrerin.get(i));
				fahrlehrerin.get(i).add_Theoriestunde(new_ts);
				this.theoriestunde.add(new_ts);
				return(new Systemereignis(Nachricht.Theoriestunde_erfolgreich_hinzugefuegt));
			}
		}
		return(new Systemereignis(Nachricht.Theoriestunde_nicht_hinzugefuegt_keine_Lehrerin_verfuegbar));
	}
	
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
			Stundenart art, Date beginn, int anzahl) {
		Date end = (Date) beginn.clone();
		if (art==Stundenart.Pruefung) {
			end.setMinutes(beginn.getMinutes()+180);
		}
		else {
			end.setMinutes(beginn.getMinutes()+45*anzahl);
		}
		if (!this.find_fahrschueler(schuelerID).get_fahrlehrerindaten().verfuegbar(beginn, end)
				|| !this.find_fahrschueler(schuelerID).verfuegbar(beginn, end)) {
			return(new Systemereignis(Nachricht.Fahrstunde_nicht_eingetragen_Lehrerin_nicht_verfuegbar));
		}
		else {
			Fahrstunde new_fs = new Fahrstunde(this.find_fahrschueler(schuelerID).get_fahrlehrerindaten(),beginn,art,anzahl);
			this.find_fahrschueler(schuelerID).add_fahrstunde(new_fs);
			this.find_fahrschueler(schuelerID).add_bestanden_fahrstunde(new_fs);
			this.find_fahrschueler(schuelerID).get_fahrlehrerindaten().add_fahrstunde(new_fs);
			this.fahrstunde.add(new_fs);
			Systemereignis systemereignis = new Systemereignis(Nachricht.Fahrstunde_erfolgreich_eingetragen);
			return(systemereignis);
		}
	}
	
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
	public Systemereignis fahrstundeLoeschen(String schuelerID, Date beginn) {
		Fahrschueler fahrschueler = this.find_fahrschueler(schuelerID);
		for (int i=0;i<fahrschueler.get_fahrstunde().size();i++) {
			if (fahrschueler.get_fahrstunde().get(i).get_Datum().equals(beginn)) {
				Fahrstunde fs = fahrschueler.get_fahrstunde().get(i);
				this.fahrstunde.remove(fs);
				fahrschueler.get_fahrlehrerindaten().get_fahrstunde().remove(fs);
				fahrschueler.get_fahrstunde().remove(i);
				return(new Systemereignis(Nachricht.Fahrstunde_erfolgreich_geloescht));
			}
		}
		return(new Systemereignis(Nachricht.Fahrstunde_nicht_geloescht_Stunde_existiert_nicht));
	}
	
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
	public Systemereignis theoriestunde_vermerken(String schuelerID, Date beginn) {
		Fahrschueler schueler = this.find_fahrschueler(schuelerID);
		Theoriestunde stunde = this.find_theoriestunde(beginn);
		boolean bereits_besucht = schueler.find_theoriestunde(stunde);
		if (bereits_besucht) {
			return(new Systemereignis(Nachricht.Theoriestunde_nicht_vermerkt_bereits_vermerkt));
		} 
		else {
			int anzahl_grundlagen = schueler.get_anzahl_grundlagenstunden();
			int thema = stunde.get_thema();
			if (thema<=12 && anzahl_grundlagen >= 12) {
				return(new Systemereignis(Nachricht.Theoriestunde_nicht_vermerkt_bereits_genug_Grundlagen));
			}
			else {
				boolean sonderthema_vermerken = schueler.sonderthema_besucht(thema);
				if (sonderthema_vermerken) {
					return(new Systemereignis(Nachricht.Theoriestunde_nicht_vermerkt_bereits_Sonderthema));
				}
				else {
					schueler.add_bestanden_theoriestunde(stunde);
					return(new Systemereignis(Nachricht.Theoriestunde_erfolgreich_vermerkt));
				}
			}
		}
	}
}
