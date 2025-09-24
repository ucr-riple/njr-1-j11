package drivenow;

import java.util.Date;

import drivenow.interfaces.*;
import drivenow.boundaries.*;
import drivenow.controls.*;

public class DriveNow implements IDrivenow {
	
	private IFahrlehrerinBoundary fahrlehrerinBoundary;
	private ISekretaerBoundary sekretaerBoundary;
	private Date aktuellesDatum;
	
	/**
	 * Diese Methode liefert Ihre Gruppennummer (inkl. Tutoriumsnummer) in der
	 * Form "Txx-yy" zurueck.
	 * 
	 * @return Ihre Gruppennummer (inkl. Tutoriumsnummer) in der Form "Txx-yy"
	 */
	@Override
	public String getGruppenNummer() {
		return "T16-05";
	}

	/**
	 * Diese Methode liefert das Objekt der FahrlehrerinBoundary.
	 * 
	 * @return Die IFahrlehrerinBoundary Ihrer Implementierung
	 */
	@Override
	public IFahrlehrerinBoundary getFahrlehrerinBoundary() {
		return fahrlehrerinBoundary;
	}
	

	/**
	 * Diese Methode liefert das Objekt der SekretaerBoundary.
	 * 
	 * @return Die ISekretaerBoundary Ihrer Implementierung
	 */
	@Override
	public ISekretaerBoundary getSekretaerBoundary(){
		return sekretaerBoundary;
	}

	/**
	 * Bevor die ersten Daten in das System uebertragen werden, wird diese
	 * Methode ausgefuehrt um das System in einen startbereiten zustand zu
	 * versetzen.
	 */
	@Override
	public void initialisieren(){
		fahrlehrerinBoundary = new FahrlehrerinBoundary();
		sekretaerBoundary = new SekretaerBoundary();
	}

	public void setDatum(Date datum) {
		this.aktuellesDatum=datum;
	}
	
	public Date getDatum() {
		return this.aktuellesDatum;
	}
	
	/**
	 * Diese Methode wird vor dem Beenden der Applikation ausgefuehrt. Bei
	 * erneutem Initialisieren duerfen keine Daten eines vorherigen Durchlaufs
	 * mehr vorhanden sein.
	 */
	public void shutDown() {
		Ressourcenverwaltung.getInstance().cleanInstance();
		Unterrichtsverwaltung.getInstance().cleanInstance();
		Pruefungsverwaltung.getInstance().cleanInstance();
	}

}
