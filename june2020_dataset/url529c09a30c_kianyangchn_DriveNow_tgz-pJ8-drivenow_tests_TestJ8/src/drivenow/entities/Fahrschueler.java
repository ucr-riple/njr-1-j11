package drivenow.entities;

import java.util.Date;
import java.util.UUID;

import java.util.ArrayList;

public class Fahrschueler {

	private String schuelerID;
	private String name;
	private String anschrift;
	private String theoriepruefungsstatus;
	private Fahrlehrerindaten fahrlehrerindaten;
	private ArrayList<Theoriestunde> bestanden_theoriestunde;
	private ArrayList<Fahrstunde> bestanden_fahrstunde;
	private ArrayList<Theoriestunde> theoriestunde;
	private ArrayList<Fahrstunde> fahrstunde;
	
	public Fahrschueler(String name, String anschrift) {
		this.schuelerID = UUID.randomUUID().toString();
		this.name = name;
		this.anschrift = anschrift;
		this.theoriepruefungsstatus="Nicht bestanden!";
		this.bestanden_theoriestunde = new ArrayList<Theoriestunde>();
		this.bestanden_fahrstunde = new ArrayList<Fahrstunde>();
		this.theoriestunde = new ArrayList<Theoriestunde>();
		this.fahrstunde = new ArrayList<Fahrstunde>();
	}
	
	public void set_fahrlehrerin(Fahrlehrerindaten fl) {
		this.fahrlehrerindaten = fl;
	}
	
	public void set_theoriepruefungsstatus(String status) {
		this.theoriepruefungsstatus=status;
	}
	
	public String get_theoriepruefungsstatus() {
		return this.theoriepruefungsstatus;
	}
	
	public String get_ID() {
		return this.schuelerID;
	}
	
	public String get_name() {
		return this.name;
	}
	
	public String get_anschrift() {
		return this.anschrift;
	}
	
	public Fahrlehrerindaten get_fahrlehrerindaten() {
		return this.fahrlehrerindaten;
	}
	
	public void add_theoriestunde(Theoriestunde ts) {
		this.theoriestunde.add(ts);
	}
	
	public void add_bestanden_theoriestunde(Theoriestunde ts) {
		this.bestanden_theoriestunde.add(ts);
	}
	
	public boolean find_theoriestunde(Theoriestunde ts) {
		return this.bestanden_theoriestunde.contains(ts);
	}
	
	public void add_fahrstunde(Fahrstunde fs) {
		this.fahrstunde.add(fs);
	}
	
	public void add_bestanden_fahrstunde(Fahrstunde fs) {
		this.bestanden_fahrstunde.add(fs);
	}
	
	public boolean find_fahrstunde(Fahrstunde fs) {
		return this.bestanden_fahrstunde.contains(fs);
	}
	
	public ArrayList<Fahrstunde> get_fahrstunde() {
		return this.fahrstunde;
	}
	
	public boolean sonderthema_besucht(int thema) {
		if((thema == 13 || thema == 14) && this.bestanden_theoriestunde.size()!=0) {
			for (int i=0;i<this.bestanden_theoriestunde.size();i++) {
				if (this.bestanden_theoriestunde.get(i).get_thema()==thema) {
					return true;
				}
			}
		}
		return false;
	}
	
	public int get_anzahl_grundlagenstunden() {
		int count=0;
		if(this.bestanden_theoriestunde.size()!=0) {
			for (int i=0;i<this.bestanden_theoriestunde.size();i++) {
				if (this.bestanden_theoriestunde.get(i).get_thema()<=12) {
					count++;
				}
			}
		}
		return count;
	}
	
	public int get_anzahl_sonderstunden() {
		int count=0;
		if(this.bestanden_theoriestunde.size()!=0) {
			for (int i=0;i<this.bestanden_theoriestunde.size();i++) {
				if (this.bestanden_theoriestunde.get(i).get_thema()==13 || this.bestanden_theoriestunde.get(i).get_thema()==14) {
					count++;
				}
			}
		}
		return count;
	}
	
	public int[] get_anzahl_fahrstunden() {
		int[] count = {0,0,0,0};
		for (int i=0;i<this.bestanden_fahrstunde.size();i++) {
			Fahrstunde stunde = this.bestanden_fahrstunde.get(i);
			switch (stunde.get_Art()) {
			case Uebungsfahrt: 
				count[0] = count[0] + stunde.get_anzahl();
				break;
			case Ueberlandfahrt : 
				count[1] = count[1] + stunde.get_anzahl();
				break;
			case Autobahnfahrt :
				count[2] = count[2] + stunde.get_anzahl();
				break;
			case Beleuchtungsfahrt :
				count[3] = count[3] + stunde.get_anzahl();
				break;
			case Pruefung:
				break;
			default:
				break;
			}
		}
		return count;
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
	
	public String print_self() {
		return "Name: " + this.get_name() 
				+ ", ID: " + this.get_ID()
				+ ", Fahrlehrerin: " + this.get_fahrlehrerindaten().get_name()
				+ ", Theoriepruefung Status: "  + this.get_theoriepruefungsstatus()
				+ '\n';
	}
	
	public String print_bestanden_theoriestunde() {
		String Theoriestunden = "Vermerkte Theoriestunden: " + '\n';
		if (this.bestanden_theoriestunde.size()!=0) {
			for (int i=0;i<this.bestanden_theoriestunde.size();i++) {
				Theoriestunden = Theoriestunden + this.bestanden_theoriestunde.get(i).toString() + '\n';
			}
		}
		else return("Keine vermerkten Theoriestunden!" + '\n');
		return Theoriestunden;
	}
	
	public String print_geplante_fahrstunde() {
		String Fahrstunden="Geplante Fahrstunden: " + '\n';
		if (this.fahrstunde.size()!=0) {
			for (int i=0;i<this.fahrstunde.size();i++) {
				Fahrstunden = Fahrstunden + this.fahrstunde.get(i).toString() + '\n';
			}
		}
		else return("Keine geplanten Fahrstunden!" + '\n');
		Fahrstunden = Fahrstunden + '\n';
		return Fahrstunden;
	}
}