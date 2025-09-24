package drivenow.entities;

import java.util.Date;

public class Theoriestunde implements Unterricht {
	
	private Date beginn;
	private Date end;
	private Fahrlehrerindaten fahrlehrerin;
	private int thema; //1 - 14
	
	public Theoriestunde(int thema, Date beginn) {
		this.thema=thema;
		this.beginn=beginn;
		this.end=(Date)beginn.clone();
		this.end.setMinutes(beginn.getMinutes()+90);
	}

	public void set_fahrlehrerin(Fahrlehrerindaten fahrlehrerin) {
		this.fahrlehrerin = fahrlehrerin;
	}
	
	@Override
	public Date get_Datum() {
		return this.beginn;
	}
	
	/**
	 * Geben die Endzeit einer Theoriestunde zurueck.
	 * Die Endzeit ist immer gleich Anfangszeit + 90 Min.
	 * @return
	 * 		Endzeit einer Theoriestunde.
	 */
	public Date get_end() {
		return this.end;
	}

	@Override
	public Fahrlehrerindaten get_fahrlehrerindaten() {
		return this.fahrlehrerin;
	}
	
	/**
	 * Geben das Thema der Theoriestunde zurueck.
	 * @return
	 * 		Thema Nummer(1 - 14). Thema 1 - 12 sind Grundlagenthema, 13 - 14 sind Sonderthema.
	 */
	public int get_thema() {
		return this.thema;
	}
	
	/**
	 * print Informationen einer Theoriestunde.
	 * @return
	 * 		Anfangsdatum: <>, Thema: <>, Art: Theoriestunde
	 */
	@Override
	public String toString() {
		return "Anfangsdatum: " + this.get_Datum() 
				+ ", Thema: " + this.get_thema() 
				+ ", Art: Theoriestunde";
	}
}
