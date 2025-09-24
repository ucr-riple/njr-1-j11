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

public class Test {

	public static void main(String[] args) {

		IDrivenow driveNowSystem = new DriveNow();
		driveNowSystem.initialisieren();

		IFahrlehrerinBoundary fahrlehrerinBoundary = driveNowSystem
				.getFahrlehrerinBoundary();
		ISekretaerBoundary sekretaerBoundary = driveNowSystem
				.getSekretaerBoundary();

		System.out.println("Beispieltest Gruppe "
				+ driveNowSystem.getGruppenNummer() + "\n");
		
		Calendar kalender[] = new Calendar[36];
		kalender[0] = Calendar.getInstance();
		kalender[0].set(2013, 10, 10, 13, 00);
		kalender[1] = Calendar.getInstance();
		kalender[1].set(2013, 10, 10, 13, 30);
		kalender[2] = Calendar.getInstance();
		kalender[2].set(2013, 10, 10, 15, 20);
		kalender[3] = Calendar.getInstance();
		kalender[3].set(2013, 10, 11,  9, 30);
		kalender[4] = Calendar.getInstance();
		kalender[4].set(2013, 10, 12, 12, 15);
		kalender[5] = Calendar.getInstance();
		kalender[5].set(2013, 10, 12, 13, 00);
		kalender[6] = Calendar.getInstance();
		kalender[6].set(2013, 10, 13,  8, 00);
		kalender[7] = Calendar.getInstance();
		kalender[7].set(2013, 10, 13, 14, 00);
		kalender[8] = Calendar.getInstance();
		kalender[8].set(2013, 9, 13, 20, 00);
		kalender[9] = Calendar.getInstance();
		kalender[9].set(2013, 9, 14, 20, 00);
		kalender[10] = Calendar.getInstance();
		kalender[10].set(2013, 9, 15, 20, 00);
		kalender[11] = Calendar.getInstance();
		kalender[11].set(2013, 9, 16, 20, 00);
		kalender[12] = Calendar.getInstance();
		kalender[12].set(2013, 9, 17, 20, 00);
		kalender[13] = Calendar.getInstance();
		kalender[13].set(2013, 9, 18, 20, 00);
		kalender[14] = Calendar.getInstance();
		kalender[14].set(2013, 9, 19, 20, 00);
		kalender[15] = Calendar.getInstance();
		kalender[15].set(2013, 9, 20, 20, 00);
		kalender[16] = Calendar.getInstance();
		kalender[16].set(2013, 9, 21, 20, 00);
		kalender[17] = Calendar.getInstance();
		kalender[17].set(2013, 9, 22, 20, 00);
		kalender[18] = Calendar.getInstance();
		kalender[18].set(2013, 9, 23, 20, 00);
		kalender[19] = Calendar.getInstance();
		kalender[19].set(2013, 9, 24, 20, 00);
		kalender[20] = Calendar.getInstance();
		kalender[20].set(2013, 9, 25, 20, 00);
		kalender[21] = Calendar.getInstance();
		kalender[21].set(2013, 9, 26, 20, 00);
		kalender[22] = Calendar.getInstance();
		kalender[22].set(2013, 9, 27, 20, 00);
		kalender[23] = Calendar.getInstance();
		kalender[23].set(2013, 9, 28, 20, 00);
		kalender[24] = Calendar.getInstance();
		kalender[24].set(2013, 12, 28, 20, 00);
		kalender[25] = Calendar.getInstance();
		kalender[25].set(2013, 11, 20, 12, 00);
		kalender[26] = Calendar.getInstance();
		kalender[26].set(2013, 11, 21, 12, 00);
		kalender[27] = Calendar.getInstance();
		kalender[27].set(2013, 11, 22, 12, 00);
		kalender[28] = Calendar.getInstance();
		kalender[28].set(2013, 11, 23, 12, 00);
		kalender[29] = Calendar.getInstance();
		kalender[29].set(2013, 11, 24, 12, 00);
		kalender[30] = Calendar.getInstance();
		kalender[30].set(2013, 11, 25, 12, 00);
		kalender[31] = Calendar.getInstance();
		kalender[31].set(2013, 11, 26, 12, 00);
		kalender[32] = Calendar.getInstance();
		kalender[32].set(2013, 11, 27, 12, 00);
		
		
//////////////////////////////////////////////////////////////////////////////////////// TEST 1
		System.out.println("Test 1: Auto hinzufuegen");
		System.out.println();

		System.out.println("1.1 Erwarte "
				+ Nachricht.Fahrschulauto_erfolgreich_hinzugefuegt);
		Systemereignis systemereignis = sekretaerBoundary
				.fahrschulautoEintragen("B-ABC1236");
		System.out.println("1.1 Erhalte " + systemereignis);

		sekretaerBoundary.fahrschulautoEintragen("B-DEF5689");
		sekretaerBoundary.fahrschulautoEintragen("B-GHJ1386");
		sekretaerBoundary.fahrschulautoEintragen("B-KLM9613");
		sekretaerBoundary.fahrschulautoEintragen("B-NOP0517");
		
		System.out.println();
		
		System.out.println("1.2 Erwarte "
				+ Nachricht.Auto_mit_kennzeichen_bereits_vorhanden);
		systemereignis = sekretaerBoundary
				.fahrschulautoEintragen("B-DEF5689");
		System.out.println("1.2 Erhalte " + systemereignis);

		System.out.println();
//////////////////////////////////////////////////////////////////////////////////////// TEST 1
		
//////////////////////////////////////////////////////////////////////////////////////// TEST 2
		System.out.println("Test 2: Fahrlehrerin hinzufuegen");
		System.out.println();
		
		System.out.println("3.1 Erwarte "
				+ Nachricht.Fahrschueler_nicht_hinzugefuegt_keine_Fahrlehrerin_vorhanden);
		systemereignis = sekretaerBoundary.fahrschuelerEintragen("Quark",
				"Am Strand 1, 23456 Stadt");
		System.out.println("3.1 Erhalte " + systemereignis);

		System.out.println();
		
		System.out.println("2.1 Erwarte "
				+ Nachricht.Fahrlehrerin_erfolgreich_hinzugefuegt);
		systemereignis = sekretaerBoundary.fahrlehrerinEintragen("Alexandra",
				"B-DEF5689");
		System.out.println("2.1 Erhalte " + systemereignis);

		System.out.println();

		System.out.println("2.2 Erwarte "
				+ Nachricht.Fahrlehrerin_nicht_hinzugefuegt_Auto_vergeben);
		systemereignis = sekretaerBoundary.fahrlehrerinEintragen("Daniela",
				"B-DEF5689");
		System.out.println("2.2 Erhalte " + systemereignis);

		System.out.println();

		System.out.println("2.3 Erwarte "
				+ Nachricht.Fahrlehrerin_nicht_hinzugefuegt_Auto_unbekannt);
		systemereignis = sekretaerBoundary.fahrlehrerinEintragen("Daniela",
				"B-DEF568");
		System.out.println("2.3 Erhalte " + systemereignis);
		
		sekretaerBoundary.fahrlehrerinEintragen("Daniela",
				"B-ABC1236");
		sekretaerBoundary.fahrlehrerinEintragen("Juice",
				"B-GHJ1386");

		System.out.println();
//////////////////////////////////////////////////////////////////////////////////////// TEST 2
		
//////////////////////////////////////////////////////////////////////////////////////// TEST 3
		System.out.println("Test 3: Fahrschueler hinzufuegen");
		System.out.println();
		
		System.out.println("3.1 in Test 2");
		
		System.out.println();
		
		System.out.println("3.2 Erwarte "
				+ Nachricht.Fahrschueler_erfolgreich_hinzugefuegt);
		systemereignis = sekretaerBoundary.fahrschuelerEintragen("Quark",
				"Am Strand 1, 23456 Stadt");
		String schuelerIDQuark = systemereignis.getID();
		System.out.println("3.2 Erhalte " + systemereignis);
		
		systemereignis = sekretaerBoundary.fahrschuelerEintragen("Ubuntu",
				"Am Strand 2, 34567 Stadt");
		String schuelerIDUbuntu = systemereignis.getID();
		systemereignis = sekretaerBoundary.fahrschuelerEintragen("Gray",
				"Am Strand 3, 24678 Stadt");
		String schuelerIDGray = systemereignis.getID();

		System.out.println();
//////////////////////////////////////////////////////////////////////////////////////// TEST 3
		
//////////////////////////////////////////////////////////////////////////////////////// TEST 4
		System.out.println("Test 4: Fahrstunde eintragen");
		System.out.println();
		
		System.out.println("4.1 Erwarte "
				+ Nachricht.Fahrstunde_erfolgreich_eingetragen);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println("\t Fahrstunde eintragen: "
				+ dateFormat.format(kalender[0].getTime()));
		systemereignis = sekretaerBoundary.fahrstundeEintragen(
				schuelerIDQuark, Stundenart.Uebungsfahrt,
				kalender[0].getTime(), 1);
		System.out.println("4.1 Erhalte " + systemereignis);
		
		System.out.println();
		
		System.out.println("4.2 Erwarte "
				+ Nachricht.Fahrstunde_nicht_eingetragen_Lehrerin_nicht_verfuegbar);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println("\t Fahrstunde eintragen: "
				+ dateFormat.format(kalender[0].getTime()));
		systemereignis = sekretaerBoundary.fahrstundeEintragen(
				schuelerIDQuark, Stundenart.Uebungsfahrt,
				kalender[0].getTime(), 1);
		System.out.println("4.2 Erhalte " + systemereignis);
		
		System.out.println();
		
		
		
		sekretaerBoundary.fahrstundeEintragen(
				schuelerIDUbuntu, Stundenart.Beleuchtungsfahrt,
				kalender[1].getTime(), 1);
		sekretaerBoundary.fahrstundeEintragen(
				schuelerIDUbuntu, Stundenart.Autobahnfahrt,
				kalender[2].getTime(), 2);
		sekretaerBoundary.fahrstundeEintragen(
				schuelerIDGray, Stundenart.Ueberlandfahrt,
				kalender[3].getTime(), 3);
		sekretaerBoundary.fahrstundeEintragen(
				schuelerIDGray, Stundenart.Uebungsfahrt,
				kalender[4].getTime(), 4);
		
		sekretaerBoundary.fahrstundeEintragen(
				schuelerIDGray, Stundenart.Beleuchtungsfahrt,
				kalender[25].getTime(), 3);
		
		sekretaerBoundary.fahrstundeEintragen(
				schuelerIDGray, Stundenart.Autobahnfahrt,
				kalender[26].getTime(), 4);
		
		sekretaerBoundary.fahrstundeEintragen(
				schuelerIDGray, Stundenart.Ueberlandfahrt,
				kalender[27].getTime(), 5);

		System.out.println();
//////////////////////////////////////////////////////////////////////////////////////// TEST 4
		
//////////////////////////////////////////////////////////////////////////////////////// TEST 5
		System.out.println("Test 5: Fahrstunde loeschen");
		System.out.println();
		
		System.out.println("5.1 Erwarte "
				+ Nachricht.Fahrstunde_erfolgreich_geloescht);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println("\t Fahrstunde loeschen: "
				+ dateFormat.format(kalender[4].getTime()));
		systemereignis = sekretaerBoundary.fahrstundeLoeschen(schuelerIDGray, kalender[4].getTime());
		System.out.println("5.1 Erhalte " + systemereignis);
		
		System.out.println();
		
		System.out.println("5.2 Erwarte "
				+ Nachricht.Fahrstunde_nicht_geloescht_Stunde_existiert_nicht);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println("\t Fahrstunde loeschen: "
				+ dateFormat.format(kalender[4].getTime()));
		systemereignis = sekretaerBoundary.fahrstundeLoeschen(schuelerIDGray, kalender[4].getTime());
		System.out.println("5.2 Erhalte " + systemereignis);
		
		System.out.println();
/////////////////////////////////////////////////////////////////////////////////////// TEST 5
		
/////////////////////////////////////////////////////////////////////////////////////// TEST 6
		System.out.println("Test 6: Theoriestunde eintragen");
		System.out.println();
		
		System.out.println("6.1 Erwarte "
				+ Nachricht.Theoriestunde_erfolgreich_hinzugefuegt);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println("\t Theoriestunde eintragen: "
				+ dateFormat.format(kalender[4].getTime()));
		systemereignis = sekretaerBoundary.theoriestundeEintragen(5, kalender[4].getTime());
		System.out.println("6.1 Erhalte " + systemereignis);
		
		System.out.println();
		
		System.out.println("6.2 Erwarte "
				+ Nachricht.Theoriestunde_nicht_hinzugefuegt_Raum_belegt);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println("\t Theoriestunde eintragen: "
				+ dateFormat.format(kalender[5].getTime()));
		systemereignis = sekretaerBoundary.theoriestundeEintragen(2, kalender[5].getTime());
		System.out.println("6.2 Erhalte " + systemereignis);
		
		System.out.println();
		
		sekretaerBoundary.theoriestundeEintragen(1, kalender[6].getTime());
		sekretaerBoundary.theoriestundeEintragen(13, kalender[7].getTime());
		
		System.out.println();
/////////////////////////////////////////////////////////////////////////////////////// TEST 6
		
/////////////////////////////////////////////////////////////////////////////////////// TEST 7
		System.out.println("Test 7: Theoriestunde vermerken");
		System.out.println();
		
		sekretaerBoundary.theoriestundeEintragen(1, kalender[8].getTime());
		sekretaerBoundary.theoriestundeEintragen(2, kalender[9].getTime());
		sekretaerBoundary.theoriestundeEintragen(3, kalender[10].getTime());
		sekretaerBoundary.theoriestundeEintragen(4, kalender[11].getTime());
		sekretaerBoundary.theoriestundeEintragen(5, kalender[12].getTime());
		sekretaerBoundary.theoriestundeEintragen(6, kalender[13].getTime());
		sekretaerBoundary.theoriestundeEintragen(7, kalender[14].getTime());
		sekretaerBoundary.theoriestundeEintragen(8, kalender[15].getTime());
		sekretaerBoundary.theoriestundeEintragen(9, kalender[16].getTime());
		sekretaerBoundary.theoriestundeEintragen(10, kalender[17].getTime());
		sekretaerBoundary.theoriestundeEintragen(11, kalender[18].getTime());
		sekretaerBoundary.theoriestundeEintragen(12, kalender[19].getTime());
		sekretaerBoundary.theoriestundeEintragen(13, kalender[20].getTime());
		sekretaerBoundary.theoriestundeEintragen(13, kalender[21].getTime());
		sekretaerBoundary.theoriestundeEintragen(2, kalender[22].getTime());
		sekretaerBoundary.theoriestundeEintragen(14, kalender[23].getTime());
		
		System.out.println("7.1 Erwarte "
				+ Nachricht.Theoriestunde_erfolgreich_hinzugefuegt);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println("\t Theoriestunde vermerken: "
				+ dateFormat.format(kalender[8].getTime()));
		systemereignis = sekretaerBoundary.theoriestundeVermerken(schuelerIDQuark, kalender[8].getTime());
		System.out.println("7.1 Erhalte " + systemereignis);
		
		System.out.println();
		
		System.out.println("7.2 Erwarte "
				+ Nachricht.Theoriestunde_nicht_vermerkt_bereits_vermerkt);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println("\t Theoriestunde vermerken: "
				+ dateFormat.format(kalender[8].getTime()));
		systemereignis = sekretaerBoundary.theoriestundeVermerken(schuelerIDQuark, kalender[8].getTime());
		System.out.println("7.2 Erhalte " + systemereignis);
		
		System.out.println();
		
		sekretaerBoundary.theoriestundeVermerken(schuelerIDQuark, kalender[9].getTime());
		sekretaerBoundary.theoriestundeVermerken(schuelerIDQuark, kalender[10].getTime());
		sekretaerBoundary.theoriestundeVermerken(schuelerIDQuark, kalender[11].getTime());
		sekretaerBoundary.theoriestundeVermerken(schuelerIDQuark, kalender[12].getTime());
		sekretaerBoundary.theoriestundeVermerken(schuelerIDQuark, kalender[13].getTime());
		sekretaerBoundary.theoriestundeVermerken(schuelerIDQuark, kalender[14].getTime());
		sekretaerBoundary.theoriestundeVermerken(schuelerIDQuark, kalender[15].getTime());
		sekretaerBoundary.theoriestundeVermerken(schuelerIDQuark, kalender[16].getTime());
		sekretaerBoundary.theoriestundeVermerken(schuelerIDQuark, kalender[17].getTime());
		sekretaerBoundary.theoriestundeVermerken(schuelerIDQuark, kalender[18].getTime());
		sekretaerBoundary.theoriestundeVermerken(schuelerIDQuark, kalender[19].getTime());
		
		System.out.println("7.3 Erwarte "
				+ Nachricht.Theoriestunde_nicht_vermerkt_bereits_genug_Grundlagen);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println("\t Theoriestunde vermerken: "
				+ dateFormat.format(kalender[22].getTime()));
		systemereignis = sekretaerBoundary.theoriestundeVermerken(schuelerIDQuark, kalender[22].getTime());
		System.out.println("7.3 Erhalte " + systemereignis);
		
		System.out.println();
		
		sekretaerBoundary.theoriestundeVermerken(schuelerIDQuark, kalender[20].getTime());
		
		System.out.println("7.4 Erwarte "
				+ Nachricht.Theoriestunde_nicht_vermerkt_bereits_Sonderthema);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println("\t Theoriestunde vermerken: "
				+ dateFormat.format(kalender[21].getTime()));
		systemereignis = sekretaerBoundary.theoriestundeVermerken(schuelerIDQuark, kalender[21].getTime());
		System.out.println("7.4 Erhalte " + systemereignis);
		
		System.out.println();
/////////////////////////////////////////////////////////////////////////////////////// TEST 7
		
/////////////////////////////////////////////////////////////////////////////////////// TEST 8
		System.out.println("Test 8: Fahrlehrerin einlogen und Unterricht zurueckgeben");
		System.out.println();
		
		System.out.println("8.1 Erwarte "
				+ Nachricht.Fahrlehrerin_Login_nicht_erfolgreich);
		systemereignis = fahrlehrerinBoundary.einloggenUndUnterrichtsstundenZurueckgeben("Yang", "B-GHJ1386");
		System.out.println("8.1 Erhalte " + systemereignis);
		
		System.out.println();
		
		System.out.println("8.2 Erwarte "
				+ Nachricht.Fahrlehrerin_Login_nicht_erfolgreich);
		systemereignis = fahrlehrerinBoundary.einloggenUndUnterrichtsstundenZurueckgeben("Juice", "B-KLM9613");
		System.out.println("8.2 Erhalte " + systemereignis);
		
		System.out.println();
		
		System.out.println("8.3 Erwarte "
				+ Nachricht.Fahrlehrerin_Login_erfolgreich);
		systemereignis = fahrlehrerinBoundary.einloggenUndUnterrichtsstundenZurueckgeben("Juice", "B-GHJ1386");
		System.out.println("8.3 Erhalte " + systemereignis);
		System.out.println(systemereignis.getText());
		
		System.out.println();

/////////////////////////////////////////////////////////////////////////////////////// TEST 8
		
/////////////////////////////////////////////////////////////////////////////////////// TEST 9
		System.out.println("Test 9: Theoriepruefungzulassung ueberpruefen");
		System.out.println();
		
		System.out.println("9.1 Erwarte "
				+ Nachricht.Theoriepruefungszulassung_nicht_erfuellt_Grundlagen_fehlen);
		systemereignis = sekretaerBoundary.theoriepruefungszulassungUeberpruefen(schuelerIDUbuntu);
		System.out.println("9.1 Erhalte " + systemereignis);
		
		System.out.println();
		
		System.out.println("9.2 Erwarte "
				+ Nachricht.Theoriepruefungszulassung_nicht_erfuellt_Sonderstunden_fehlen);
		systemereignis = sekretaerBoundary.theoriepruefungszulassungUeberpruefen(schuelerIDQuark);
		System.out.println("9.2 Erhalte " + systemereignis);
		
		System.out.println();
		
		sekretaerBoundary.theoriestundeVermerken(schuelerIDQuark, kalender[23].getTime());
		System.out.println("9.3 Erwarte "
				+ Nachricht.Theoriepruefungszulassung_erfolgreich_geprueft);
		systemereignis = sekretaerBoundary.theoriepruefungszulassungUeberpruefen(schuelerIDQuark);
		System.out.println("9.3 Erhalte " + systemereignis);
		
		System.out.println();
/////////////////////////////////////////////////////////////////////////////////////// TEST 9
		
/////////////////////////////////////////////////////////////////////////////////////// TEST 10
		System.out.println("Test 10: Theoriepruefungbestehen eintragen");
		System.out.println();
		
		System.out.println("10.1 Erwarte "
				+ Nachricht.Theoriepruefungsbestehen_erfolgreich_eingetragen);
		systemereignis = sekretaerBoundary.theoriepruefungsbestehenEintragen(schuelerIDQuark);
		System.out.println("10.1 Erhalte " + systemereignis);
		
		System.out.println();
/////////////////////////////////////////////////////////////////////////////////////// TEST 10
		
/////////////////////////////////////////////////////////////////////////////////////// TEST 11
		System.out.println("Test 11: Praxispruefung eintragen");
		System.out.println();
		
		System.out.println("11.1 Erwarte "
				+ Nachricht.Praxispruefung_nicht_eingetragen_Sonderstunden_fehlen);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println("\t Praxispruefung vermerken: "
				+ dateFormat.format(kalender[24].getTime()));
		systemereignis = sekretaerBoundary.praxispruefungEintragen(schuelerIDQuark, kalender[24].getTime());
		System.out.println("11.1 Erhalte " + systemereignis);
		
		System.out.println();
		
		System.out.println("11.2 Erwarte "
				+ Nachricht.Praxispruefung_nicht_eingetragen_Theoriepruefung_fehlt);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println("\t Praxispruefung vermerken: "
				+ dateFormat.format(kalender[24].getTime()));
		systemereignis = sekretaerBoundary.praxispruefungEintragen(schuelerIDGray, kalender[24].getTime());
		System.out.println("11.2 Erhalte " + systemereignis);
		
		System.out.println();
		
		sekretaerBoundary.fahrstundeEintragen(
				schuelerIDQuark, Stundenart.Beleuchtungsfahrt,
				kalender[28].getTime(), 3);
		
		sekretaerBoundary.fahrstundeEintragen(
				schuelerIDQuark, Stundenart.Autobahnfahrt,
				kalender[29].getTime(), 4);
		
		sekretaerBoundary.fahrstundeEintragen(
				schuelerIDQuark, Stundenart.Ueberlandfahrt,
				kalender[30].getTime(), 5);
		
		System.out.println("11.3 Erwarte "
				+ Nachricht.Praxispruefung_erfolgreich_eingtragen);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println("\t Praxispruefung vermerken: "
				+ dateFormat.format(kalender[31].getTime()));
		systemereignis = sekretaerBoundary.praxispruefungEintragen(schuelerIDQuark, kalender[31].getTime());
		System.out.println("11.3 Erhalte " + systemereignis);
		
		System.out.println();
		
		System.out.println("11.4 Erwarte "
				+ Nachricht.Praxispruefung_nicht_eingetragen_Fahrlehrerin_nicht_verfuegbar);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println("\t Praxispruefung vermerken: "
				+ dateFormat.format(kalender[31].getTime()));
		systemereignis = sekretaerBoundary.praxispruefungEintragen(schuelerIDQuark, kalender[31].getTime());
		System.out.println("11.4 Erhalte " + systemereignis);
		
		System.out.println();
/////////////////////////////////////////////////////////////////////////////////////// TEST 11
		
/////////////////////////////////////////////////////////////////////////////////////// Datenbanktest 1	
		System.out.println("Datenbanktest 1: Erwarte Datenbestand von IFahrlehrerinBoundary");
		System.out.println();
		System.out.println(fahrlehrerinBoundary.unterrichtsstundenZurueckgeben());
		System.out.println();
/////////////////////////////////////////////////////////////////////////////////////// Datenbanktest 1
		
/////////////////////////////////////////////////////////////////////////////////////// Datenbanktest 2
		System.out.println("Databanktest 2: Erwarte Datenbestand von ISekretaerBoundary");
		System.out.println();
		System.out.println(sekretaerBoundary.datenbestandZurueckgeben());
/////////////////////////////////////////////////////////////////////////////////////// Datenbanktest 2
	}
}