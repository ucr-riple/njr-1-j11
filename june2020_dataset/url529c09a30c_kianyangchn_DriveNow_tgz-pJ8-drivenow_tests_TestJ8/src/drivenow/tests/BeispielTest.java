package drivenow.tests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import drivenow.DriveNow;
import drivenow.Systemereignis;
import drivenow.Systemereignis.Nachricht;
import drivenow.interfaces.IDrivenow;
import drivenow.interfaces.IFahrlehrerinBoundary;
import drivenow.interfaces.ISekretaerBoundary;
import drivenow.interfaces.ISekretaerBoundary.Stundenart;

public class BeispielTest {

	public static void main(String[] args) {

		IDrivenow driveNowSystem = new DriveNow(); // Hier Ihr Drivenow System
		driveNowSystem.initialisieren();

		IFahrlehrerinBoundary fahrlehrerinBoundary = driveNowSystem
				.getFahrlehrerinBoundary();
		ISekretaerBoundary sekretaerBoundary = driveNowSystem
				.getSekretaerBoundary();

		System.out.println("Beispieltest Gruppe "
				+ driveNowSystem.getGruppenNummer() + "\n");
		System.out.println("Tests 1: Ressourcen hinzufuegen");
		System.out.println();

		System.out.println("1.0 Erwarte "
				+ Nachricht.Fahrschulauto_erfolgreich_hinzugefuegt);
		Systemereignis systemereignis = sekretaerBoundary
				.fahrschulautoEintragen("B-ABC1234");
		System.out.println("1.0 Erhalte " + systemereignis);

		sekretaerBoundary.fahrschulautoEintragen("B-DEF5689");
		sekretaerBoundary.fahrschulautoEintragen("B-GHJ1384");

		System.out.println();

		System.out.println("1.1 Erwarte "
				+ Nachricht.Fahrlehrerin_erfolgreich_hinzugefuegt);
		systemereignis = sekretaerBoundary.fahrlehrerinEintragen("Alexandra",
				"B-DEF5689");
		System.out.println("1.1 Erhalte " + systemereignis);

		System.out.println();

		System.out.println("1.2 Erwarte "
				+ Nachricht.Fahrlehrerin_nicht_hinzugefuegt_Auto_vergeben);
		systemereignis = sekretaerBoundary.fahrlehrerinEintragen("Daniela",
				"B-DEF5689");
		System.out.println("1.2 Erhalte " + systemereignis);

		System.out.println();

		System.out.println("1.3 Erwarte "
				+ Nachricht.Fahrlehrerin_nicht_hinzugefuegt_Auto_vergeben);
		systemereignis = sekretaerBoundary.fahrlehrerinEintragen("Daniela",
				"B-DEF5689");
		System.out.println("1.3 Erhalte " + systemereignis);

		System.out.println();

		System.out.println("1.4 Erwarte "
				+ Nachricht.Fahrschueler_erfolgreich_hinzugefuegt);
		systemereignis = sekretaerBoundary.fahrschuelerEintragen("Daniel",
				"Am Strand 1, 23456 Stadt");
		String schuelerIDDaniel = systemereignis.getID();
		System.out.println("1.4 Erhalte " + systemereignis);

		System.out.println();

		
		System.out.println("1.5 Erwarte "
				+ Nachricht.Fahrstunde_erfolgreich_eingetragen);
		Calendar kalender[] = new Calendar[5];
		kalender[0] = Calendar.getInstance();
		kalender[0].set(2013, 10, 10, 13, 00);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println("\t Fahrstunde eintragen: "
				+ dateFormat.format(kalender[0].getTime()));
		systemereignis = sekretaerBoundary.fahrstundeEintragen(
				schuelerIDDaniel, Stundenart.Uebungsfahrt,
				kalender[0].getTime(), 1);
		System.out.println("1.5 Erhalte " + systemereignis);

		System.out.println();
		
		
		System.out.println("1.6 Erwarte Datenbestand von ISekretaerBoundary");
		System.out.println(sekretaerBoundary.datenbestandZurueckgeben());

		System.out
				.println("1.7 Erwarte Datenbestand von IFahrlehrerinBoundary");
		System.out.println(fahrlehrerinBoundary
				.unterrichtsstundenZurueckgeben());
	}
}
