package drivenow.controls;

import java.util.ArrayList;
import java.util.Random;

import drivenow.*;
import drivenow.Systemereignis.Nachricht;
import drivenow.entities.*;;

public class Ressourcenverwaltung {

	private static Ressourcenverwaltung instance;
	private ArrayList<Auto> autos;
	private ArrayList<Fahrlehrerindaten> fahrlehrerin;
	private ArrayList<Fahrschueler> fahrschueler;
	
	public Ressourcenverwaltung() {
		this.autos = new ArrayList<Auto>();
		this.fahrlehrerin = new ArrayList<Fahrlehrerindaten>();
		this.fahrschueler = new ArrayList<Fahrschueler>();
	}
	
	public static Ressourcenverwaltung getInstance() {
		if (instance == null) {
			instance = new Ressourcenverwaltung();
		}
		return instance;
	}
	
	public void cleanInstance() {
		instance=null;
	}
	
	/**
	 * Geben alle Autos zurueck
	 * @return
	 * 		Die gesamte Automenge.
	 */
	public ArrayList<Auto> get_auto() {
		return this.autos;
	}
	
	/**
	 * Geben alle Fahrlehrerinen zurueck.
	 * @return
	 * 		Die gesamte Fahrlehrerinmenge.
	 */
	public ArrayList<Fahrlehrerindaten> get_fahrlehrerin() {
		return this.fahrlehrerin;
	}
	
	/**
	 * Geben alle Fahrschueleren zurueck.
	 * @return
	 * 		Die gesamte Fahrschuelermenge.
	 */
	public ArrayList<Fahrschueler> get_fahrschueler() {
		return this.fahrschueler;
	}
	
	/**
	 * Geben ein Auto mit dem gegebene Kennzeichen zurueck. 
	 * @param kennzeichen
	 * 		Kennzeichen eines Autos.
	 * @return
	 * 		Ein Auto mit dem entsprechende Kennzeichen.
	 */
	private Auto find_auto(String kennzeichen) {
		if (this.autos.size()!=0) {
			for (int i=0;i<this.autos.size();i++) {
				if (this.autos.get(i).get_kennzeichen()==kennzeichen) {
					return this.autos.get(i);
				}
			}
		}
		return null;
	}
	
	/**
	 * Geben ein Fahrlehrerin mit dem gegebene Name zurueck.
	 * @param name
	 * 		Name eines Fahrlehrerin.
	 * @return
	 * 		Ein Fahrlehrerin mit dem entsprechende Name. 
	 */
	private Fahrlehrerindaten find_fahrlehrerin(String name) {
		if (this.fahrlehrerin.size()!=0) {
			for (int i=0;i<this.fahrlehrerin.size();i++) {
				if (this.fahrlehrerin.get(i).get_name()==name) {
					return this.fahrlehrerin.get(i);
				}
			}
		}
		return null;
	}
	
	/**
	 * Geben ein Fahrschueler mit dem gegebene ID zurueck.
	 * @param schuelerID
	 * 		ID eines Fahrschueler.
	 * @return
	 * 		Ein Fahrschueler mit dem entsprechende ID.
	 */
	public Fahrschueler find_fahrschueler(String schuelerID) {
		for (int i=0;i<this.fahrschueler.size();i++) {
			if (this.fahrschueler.get(i).get_ID()==schuelerID) {
				return this.fahrschueler.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Ein neue Fahrlehrerin im System hinzufuegen.
	 * @param new_fl
	 * 		Ein neue Fahrlehrerin.
	 */
	private void add_fahrlehrerin(Fahrlehrerindaten new_fl) {
		this.fahrlehrerin.add(new_fl);
	}
	
	private void add_auto(Auto auto) {
		this.autos.add(auto);
	}
	
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
	public Systemereignis auto_hinzufuegen(String kennzeichen) {
		if (this.find_auto(kennzeichen)!=null) {
			return(new Systemereignis(Nachricht.Auto_mit_kennzeichen_bereits_vorhanden));
		}
		else {
			this.add_auto(new Auto(kennzeichen));
			return(new Systemereignis(Nachricht.Fahrschulauto_erfolgreich_hinzugefuegt));
		}
	}
	
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
	public Systemereignis fahrschueler_hinzufuegen(String name, String anschrift) {
		if (this.fahrlehrerin.size()==0) {
			return(new Systemereignis(Nachricht.Fahrschueler_nicht_hinzugefuegt_keine_Fahrlehrerin_vorhanden));
		}
		else {
			Fahrschueler new_fs = new Fahrschueler(name,anschrift);
			Random index = new Random();
			Fahrlehrerindaten fl = this.fahrlehrerin.get(index.nextInt(this.fahrlehrerin.size()));
			new_fs.set_fahrlehrerin(fl);
			this.fahrschueler.add(new_fs);
			Systemereignis systemereignis = new Systemereignis(Nachricht.Fahrschueler_erfolgreich_hinzugefuegt);
			systemereignis.setID(new_fs.get_ID());
			return systemereignis;
		}
	}
	
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
	public Systemereignis fahrlehrerin_einloggene_und_unterrichtsstunden_zurueckgeben(String name, String kennzeichen) {
		if (this.find_fahrlehrerin(name)==null || this.find_fahrlehrerin(name).get_zugewiesenes_auto().get_kennzeichen()!=kennzeichen) {
			return(new Systemereignis(Nachricht.Fahrlehrerin_Login_nicht_erfolgreich));
		}
		else {
			Systemereignis systemereignis = new Systemereignis(Nachricht.Fahrlehrerin_Login_erfolgreich);
			Fahrlehrerindaten fahrlehrerin = this.find_fahrlehrerin(name);
			if (fahrlehrerin.get_fahrstunde().size()==0) {
				systemereignis.setText("Keine Unterrichtsstunden fuer diese Fahrlehrerin vorhanden");
			}
			else {
				systemereignis.setText(fahrlehrerin.print_fahrstunde());
			}
			return systemereignis;
		}
	}
	
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
	public Systemereignis fahrlehrerin_hinzufuegen(String name, String kennzeichen) {
		Auto auto=this.find_auto(kennzeichen);
		if (auto == null) {
			return(new Systemereignis(Nachricht.Fahrlehrerin_nicht_hinzugefuegt_Auto_unbekannt));
		}
		else {
			if (auto.get_fahrlehrerin()!=null) {
				return(new Systemereignis(Nachricht.Fahrlehrerin_nicht_hinzugefuegt_Auto_vergeben));
			}
			else {
				Fahrlehrerindaten new_fl = new Fahrlehrerindaten();
				new_fl.create(name, auto);
				this.add_fahrlehrerin(new_fl);
				auto.set_fahrlehrerin(new_fl);
				Systemereignis systemereignis = new Systemereignis(Nachricht.Fahrlehrerin_erfolgreich_hinzugefuegt);
				systemereignis.setID(new_fl.get_lehrerinID());
				return(systemereignis);
			}
		}
	}
	
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
	public Systemereignis datenbestandZurueckgeben() {
		Systemereignis systemereignis = new Systemereignis(null);
		String Text = "";
		//alle vorhandenen Fahrschulautos mit Kennzeichen und der zugeordneten Fahrlehrerin
		String AutoText = "";
		for (int i=0;i<this.autos.size();i++) {
			AutoText = AutoText + this.autos.get(i).toString()
					+ '\n';
		}
		// alle Fahrlehrerinnen mit ID, geplanten Fahrstunden und geplanten Theoriestunden
		String FahrlehrerinText = "";
		for (int i=0;i<this.fahrlehrerin.size();i++) {
			FahrlehrerinText = FahrlehrerinText 
					+ this.fahrlehrerin.get(i).print_self()
					+ this.fahrlehrerin.get(i).print_theoriestunde()
					+ this.fahrlehrerin.get(i).print_fahrstunde()
					+ '\n'; 
		}
		// alle Fahrschueler mit ID, zugehoeriger Lehrerin, vermerkten Theoriestunden, geplanten Fahrstunden,
		// Status der Theorieprueung (bestanden oder nicht bestanden)
		String FahrschuelerText = "";
		for (int i=0;i<this.fahrschueler.size();i++) {
			FahrschuelerText = FahrschuelerText
					+ this.fahrschueler.get(i).print_self()
					+ this.fahrschueler.get(i).print_bestanden_theoriestunde()
					+ this.fahrschueler.get(i).print_geplante_fahrstunde()
					+ '\n';
		}
	
		Text = Text 
				+ "===================== \n"
				+ "Auto Datenbank: \n" + AutoText + '\n'
				+ "===================== \n"
				+ "Fahrlehrerin Datenbank: \n" + FahrlehrerinText
				+ "===================== \n"
				+ "Fahrschueler Datenbank: \n" + FahrschuelerText;
		systemereignis.setText(Text);
		return systemereignis;
	}
}
