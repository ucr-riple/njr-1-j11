package drivenow.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Fahrlehrerindaten {
	
	private String lehrerinID;
	private String name;
	private Auto zugewiesenes_auto;
	private ArrayList<Theoriestunde> theoriestunde;
	private ArrayList<Fahrstunde> fahrstunde;
	
	/**
	 * Erzeugt einen neue Fahrlehrerin mit gegebenem Name und einem verantwortliche Auto.
	 * Ein LehrerinID wird gleichzeitig gegeben.
	 * @param name
	 * 		Name des Fahrlehrerin.
	 * @param auto
	 * 		Verantwortliches Auto des Fahrlehrerin
	 */
	public void create(String name, Auto auto) {
		this.lehrerinID=UUID.randomUUID().toString();
		this.name=name;
		this.zugewiesenes_auto=auto;
		this.theoriestunde=new ArrayList<Theoriestunde>();
		this.fahrstunde=new ArrayList<Fahrstunde>();
	}
	
	/**
	 * Geben FahrlehrerinID zurueck.
	 * @return
	 * 		ID des Fahrlehrerin.
	 */
	public String get_lehrerinID() {
		return this.lehrerinID;
	}
	
	/**
	 * Geben den Name des Fahrlehrerin zurueck.
	 * @return
	 * 		Name des Fahrlehrerin.
	 */
	public String get_name() {
		return this.name;
	}
	
	/**
	 * Geben das zugewiesene Auto des Fahrlehrerin zurueck.
	 * @return
	 * 		Das zugewiesene Auto.
	 */
	public Auto get_zugewiesenes_auto() {
		return this.zugewiesenes_auto;
	}
	
	/**
	 * Geben alle verantwortliche Theoriestunden fuer den Fahrlehrerin zurueck.
	 * @return
	 * 		Alle theoriestunden.
	 */
	public ArrayList<Theoriestunde> get_theoriestunde() {
		return this.theoriestunde;
	}
	
	/**
	 * Geben alle verantwortliche Fahrstunden fuer den Fahrlehrerin zurueck.
	 * @return
	 * 		Alle Fahrstunden.
	 */
	public ArrayList<Fahrstunde> get_fahrstunde() {
		return this.fahrstunde;
	}
	
	/**
	 * Eine neue verantwortliche Theoriestunde fuer den Fahrlehrerin hinzufuegen.
	 * @param stunde
	 * 		Eine neue Theoriestunde.
	 */
	public void add_Theoriestunde(Theoriestunde stunde) {
		this.theoriestunde.add(stunde);
	}
	
	/**
	 * Eine neue verantwortliche Fahrstunde fuer den Fahrlehrerin hinzufuegen.
	 * @param stunde
	 * 		Eine neue Fahrstunde.
	 */
	public void add_fahrstunde(Fahrstunde stunde) {
		this.fahrstunde.add(stunde);
	}
	
	/**
	 * Ueberpruefen, ob der Fahrlehrerin in die gegebene Zeitraum verfuegbar ist.
	 * @param beginn
	 * 		Die Anfangszeit einer Stunde.
	 * @param end
	 * 		Die Endzeit einer Stunde
	 * @return
	 * 		True, falls verfuegbar; Ansonst False.
	 */
	public boolean verfuegbar(Date beginn, Date end) {
		for (int i=0;i<this.fahrstunde.size();i++) {
			Fahrstunde stunde = this.fahrstunde.get(i);
			if ((stunde.get_Datum().before(beginn) && stunde.get_end().after(beginn))
					||(stunde.get_Datum().before(end) && stunde.get_end().after(end))
					|| stunde.get_Datum().equals(beginn)
					|| stunde.get_end().equals(end)) {
				return false;
			}
		}
		for (int i=0;i<this.theoriestunde.size();i++) {
			Theoriestunde stunde = this.theoriestunde.get(i);
			if ((stunde.get_Datum().before(beginn) && stunde.get_end().after(beginn))
					||(stunde.get_Datum().before(end) && stunde.get_end().after(end))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * print personale Informationen.
	 * @return
	 * 		Name: <>, ID: <>.
	 */
	public String print_self() {
		return "Name: " + this.get_name() + ", ID: " + this.get_lehrerinID() + '\n';
	}
	
	/**
	 * print alle verantwortliche Theoriestunden des Fahrlehrerin.
	 * @return
	 * 		Theoriestunden:
	 * 		Anfangsdatum: <>, Thema: <>, Art: Theoriestunde
	 * 		.
	 * 		.
	 * 		.
	 * 		ODER
	 * 		Keine Theoriestunden!
	 */
	public String print_theoriestunde() {
		String Theoriestunden = "Theoriestunden: " + '\n';
		if (this.theoriestunde.size()!=0) {
			for (int i=0;i<this.theoriestunde.size();i++) {
				Theoriestunden = Theoriestunden + this.theoriestunde.get(i).toString() + '\n';
			}
		}
		else return("Keine Theoriestunden!" + '\n');
		Theoriestunden = Theoriestunden + '\n';
		return Theoriestunden;
	}
	
	/**
	 * print  alle verantwortliche Fahrstunden des Fahrlehrerin.
	 * @return
	 * 		Fahrstunden:
	 * 		Anfangsdatum: <>, Fahrstundenart: <>
	 * 		.
	 * 		.
	 * 		.
	 * 		ODER
	 * 		Keine Fahrstunden!
	 */
	public String print_fahrstunde() {
		String Fahrstunden="Fahrstunden: " + '\n';
		if (this.fahrstunde.size()!=0) {
			for (int i=0;i<this.fahrstunde.size();i++) {
				Fahrstunden = Fahrstunden + this.fahrstunde.get(i).toString() + '\n';
			}
		}
		else return("Keine Fahrstunden!" + '\n');
		Fahrstunden = Fahrstunden + '\n';
		return Fahrstunden;
	}
}