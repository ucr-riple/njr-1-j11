package drivenow.entities;

import java.util.Date;
import drivenow.interfaces.ISekretaerBoundary.Stundenart;


public class Fahrstunde implements Unterricht {
	
	private Fahrlehrerindaten fahrlehrerin;
	private Date beginn;
	private Date end;
	private Stundenart art;
	private int anzahl;
	
	public Fahrstunde(Fahrlehrerindaten fahrlehrerin, Date beginn, Stundenart art, int anzahl) {
		this.fahrlehrerin = fahrlehrerin;
		this.beginn = beginn;
		this.art = art;
		this.anzahl=anzahl;
		this.end = (Date) beginn.clone();
		if (art==Stundenart.Pruefung) {
			this.end.setMinutes(beginn.getMinutes()+180);
		}
		else {
			this.end.setMinutes(beginn.getMinutes()+anzahl*45);
		}
	}
	
	@Override
	public Date get_Datum() {
		return this.beginn;
	}
	
	/**
	 * Geben die Endzeit einer gesamte Fahrstunde zurueck.
	 * Jede Fahrstunde dauert 45 Min und die gesamte Fahrstunde erhalten ANZAHL-mal Fahrstunden.
	 * Die Endzeit ist dann gleich Anfangszeit + (Anzahl * 45 Min).
	 * @return
	 */
	public Date get_end() {
		return this.end;
	}
	
	@Override
	public Fahrlehrerindaten get_fahrlehrerindaten() {
		return this.fahrlehrerin;
	}
	
	/**
	 * Geben die Fahrstundenart zurueck.
	 * Fahrstundenart: Uebungsfahrt, Ueberlandfahrt, Autobahnfahrt, Beleuchtungsfahrt, Pruefung.
	 * Definiert in drivenow.interfaces.ISekretaerBoundary
	 * @return
	 * 		Fahrstundenart der Fahrstunde.
	 */
	public Stundenart get_Art() {
		return this.art;
	}
	
	/**
	 * Geben die Anzahl der Fahrstunde zureuck.
	 * @return
	 */
	public int get_anzahl() {
		return this.anzahl;
	}
	
	/**
	 * print Informationen einer Fahrstunde.
	 * @return 
	 * 		Anfangsdatum: <>, Fahrstundenart: <>
	 */
	@Override
	public String toString() {
		return "Anfangsdatum: " + this.get_Datum().toString() 
				+ ", Fahrstundenart: " + this.get_Art().toString();
	}
	
}