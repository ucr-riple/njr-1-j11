package drivenow.boundaries;

import java.util.Date;

import drivenow.Systemereignis;
import drivenow.interfaces.ISekretaerBoundary;
import drivenow.controls.*;

public class SekretaerBoundary implements ISekretaerBoundary {

	private Ressourcenverwaltung ressourcenverwaltung;
	private Unterrichtsverwaltung unterrichtsverwaltung;
	private Pruefungsverwaltung pruefungsverwaltung;
	
	public SekretaerBoundary() {
		ressourcenverwaltung = Ressourcenverwaltung.getInstance();
		unterrichtsverwaltung = Unterrichtsverwaltung.getInstance();
		pruefungsverwaltung = Pruefungsverwaltung.getInstance();
	} 
	
	@Override
	public Systemereignis fahrlehrerinEintragen(String name, String kennzeichen) {
		return ressourcenverwaltung.fahrlehrerin_hinzufuegen(name, kennzeichen);
		
	}

	@Override
	public Systemereignis fahrschuelerEintragen(String name, String anschrift) {
		return ressourcenverwaltung.fahrschueler_hinzufuegen(name, anschrift);
	}

	@Override
	public Systemereignis fahrschulautoEintragen(String kennzeichen) {
		return ressourcenverwaltung.auto_hinzufuegen(kennzeichen);
	}

	@Override
	public Systemereignis theoriestundeEintragen(int thema, Date beginn) {
		return unterrichtsverwaltung.theoriestundeEintragen(thema, beginn);
	}

	@Override
	public Systemereignis fahrstundeEintragen(String schuelerID,
			Stundenart art, Date beginn, int anzahl) {
		return unterrichtsverwaltung.fahrstundeEintragen(schuelerID, art, beginn, anzahl);
	}

	@Override
	public Systemereignis theoriestundeVermerken(String schuelerID, Date beginn) {
		return unterrichtsverwaltung.theoriestunde_vermerken(schuelerID, beginn);
	}

	@Override
	public Systemereignis fahrstundeLoeschen(String schuelerID, Date beginn) {
		return unterrichtsverwaltung.fahrstundeLoeschen(schuelerID, beginn);
	}

	@Override
	public String datenbestandZurueckgeben() {
		return ressourcenverwaltung.datenbestandZurueckgeben().getText();
	}

	@Override
	public Systemereignis theoriepruefungszulassungUeberpruefen(
			String schuelerID) {
		return pruefungsverwaltung.theoriepruefungszulassungUeberpruefen(schuelerID);
	}

	@Override
	public Systemereignis theoriepruefungsbestehenEintragen(String schuelerID) {
		return pruefungsverwaltung.theoriepruefungsbestehenEintragen(schuelerID);
	}

	@Override
	public Systemereignis praxispruefungEintragen(String schuelerID, Date beginn) {
		return pruefungsverwaltung.praxispruefungEintragen(schuelerID, beginn);
	}

}