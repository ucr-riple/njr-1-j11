import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Queue;

public class Test {
	private static Calendar kalender = Calendar.getInstance();

	private static Date toDate(int jahr, int monat, int tag) {
		kalender.set(jahr, monat - 1, tag);
		return kalender.getTime();
	}

	private static Date toDate(int jahr, int monat, int tag, int stunde,
			int minute) {
		kalender.set(jahr, monat - 1, tag, stunde, minute);
		return kalender.getTime();
	}

	public static void main(String[] args) {
		testeBands();

		Band band = new Band("Green Day", "Rock", 2);

		System.out.println();
		System.out.println("erstelle Mitglieder:");
		erstelleMitglieder(band);
		System.out.println();
		System.out.println("teste Mitglieder:");
		testeMitglieder(band);

		System.out.println();
		System.out.println("erstelle Orte:");
		erstelleOrte(band);
		System.out.println();
		System.out.println("teste Orte:");
		testeOrte(band);

		System.out.println();
		System.out.println("erstelle Termine:");
		erstelleTermine(band);
		System.out.println();
		System.out.println("teste Termine (arbeitet mit eigenen Testdaten):");
		testeTermine();

		System.out.println();
		System.out.println("erstelle Posten:");
		erstellePosten(band);
		System.out.println();
		System.out.println("teste GuV:");
		testeGuV(band);

		System.out.println();
		System.out.println("erstelle Repertoire:");
		erstelleRepertoire(band);
		System.out.println();
		System.out.println("teste Repertoire:");
		testeRepertoire(band);

		System.out.println();
		System.out.println("teste Sicherung:");
		testeSicherung(band);

	}

	public static void testeBands() {
		Programm programm = new Programm(false);
		Band band;

		// Testfall: Hinzufuegen von Bands
		//
		// Erwartete Ausgabe:
		// Green Day, Rock
		// Sunrise Avenue, Rock

		band = new Band("Green Day", "Rock", 2);
		programm.addBand(band);

		band = new Band("Sunrise Avenue", "Rock", 2);
		programm.addBand(band);

		System.out.println(programm.getBand("Green Day"));
		System.out.println(programm.getBand("Sunrise Avenue"));
	}

	public static void erstelleMitglieder(Band band) {
		Mitglieder mitglieder = band.getMitglieder();
		Mitglied mitglied;
		Zeitraum zeitraum;

		// Testfall: Hinzufuegen von Mitgliedern
		//
		// Erwartete Ausgabe:
		// Billie Joe Armstrong (Gitarre) [04.03.1989 - ]
		// TelefonNr: 123/45678
		// Mike Dirnt (Bass) [04.03.1989 - ]
		// TelefonNr: 321/12323
		// Al Sobrante (Schlagzeug) [04.03.1989 - 01.01.1990]
		// TelefonNr: 345/54327
		// Tre Cool (Schlagzeug) [01.01.1990 - ]
		// TelefonNr: 943/38321
		// Schlagzeug Tim (Schlagzeug) [01.01.1990 - ]
		// TelefonNr: 453/56233, Ersatzmitglied
		// Gitarren Joe (Gitarre) [01.01.1990 - ]
		// TelefonNr: 233/12437, Ersatzmitglied
		// Bob Bass (Bass) [01.01.1990 - ]
		// TelefonNr: 111/78755, Ersatzmitglied

		zeitraum = new Zeitraum(toDate(1989, 3, 4));
		mitglied = new Mitglied("Billie Joe Armstrong", "123/45678", "Gitarre",
				zeitraum, false);
		mitglieder.add(mitglied);
		System.out.println(mitglied.toDetailString());

		zeitraum = new Zeitraum(toDate(1989, 3, 4));
		mitglied = new Mitglied("Mike Dirnt", "321/12323", "Bass", zeitraum,
				false);
		mitglieder.add(mitglied);
		System.out.println(mitglied.toDetailString());

		zeitraum = new Zeitraum(toDate(1989, 3, 4), toDate(1990, 1, 1));
		mitglied = new Mitglied("Al Sobrante", "345/54327", "Schlagzeug",
				zeitraum, false);
		mitglieder.add(mitglied);
		System.out.println(mitglied.toDetailString());

		zeitraum = new Zeitraum(toDate(1990, 1, 1));
		mitglied = new Mitglied("Tre Cool", "943/38321", "Schlagzeug",
				zeitraum, false);
		mitglieder.add(mitglied);
		System.out.println(mitglied.toDetailString());

		zeitraum = new Zeitraum(toDate(1990, 1, 1));
		mitglied = new Mitglied("Schlagzeug Tim", "453/56233", "Schlagzeug",
				zeitraum, true);
		mitglieder.add(mitglied);
		System.out.println(mitglied.toDetailString());

		zeitraum = new Zeitraum(toDate(1990, 1, 1));
		mitglied = new Mitglied("Gitarren Joe", "233/12437", "Gitarre",
				zeitraum, true);
		mitglieder.add(mitglied);
		System.out.println(mitglied.toDetailString());

		zeitraum = new Zeitraum(toDate(1990, 1, 1));
		mitglied = new Mitglied("Bob Bass", "111/78755", "Bass", zeitraum, true);
		mitglieder.add(mitglied);
		System.out.println(mitglied.toDetailString());
	}

	public static void testeMitglieder(Band band) {
		List<Selector<Mitglied>> selektoren;
		Mitglied mitglied0;
		Mitglied mitglied1;

		// Testfall: Ausgabe aller Mitglieder
		//
		// Erwartete Ausgabe:
		// [Billie Joe Armstrong, Mike Dirnt, Al Sobrante, Tre Cool, Schlagzeug
		// Tim, Gitarren Joe, Bob Bass]

		System.out.println();
		System.out.println(band.getMitglieder());

		// Testfall: Gitarren Joe wird permanentes Mitglied
		//
		// Erwartete Ausgabe:
		// Gitarren Joe (Gitarre) [01.01.1990 - ]
		// TelefonNr: 233/12437

		selektoren = new ArrayList<Selector<Mitglied>>();
		selektoren.add(new Mitglied.NameSelektor("Gitarren Joe"));

		mitglied0 = band.getMitglieder(selektoren).getFirst();
		mitglied0.setErsatzmitglied(false);

		System.out.println();
		System.out.println(mitglied0.toDetailString());

		// Testfall: Mike Dirnt wird Ersatzmitglied
		//
		// Erwartete Ausgabe:
		// Mike Dirnt (Bass) [04.03.1989 - ]
		// TelefonNr: 321/12323, Ersatzmitglied

		selektoren = new ArrayList<Selector<Mitglied>>();
		selektoren.add(new Mitglied.NameSelektor("Mike Dirnt"));

		mitglied1 = band.getMitglieder(selektoren).getFirst();
		mitglied1.setErsatzmitglied(true);

		System.out.println();
		System.out.println(mitglied1.toDetailString());

		// Mache Aenderungen rueckgaengig, damit weitere Tests auf der
		// urspruenglichen Datenbasis arbeiten koennen!
		mitglied0.setErsatzmitglied(true);
		mitglied1.setErsatzmitglied(false);
	}

	public static void erstelleOrte(Band band) {
		Orte orte = band.getOrte();
		Ort ort;
		List<String> infrastruktur = new ArrayList<String>();

		// Testfall: Hinzufuegen von Orten
		//
		// Erwartete Ausgabe:
		// St. Poelten [Proberaum, Stadion]
		// Wien [Musikgeschaeft, Gitarren Fachhandel, Proberaum, Stadion]
		// Korneuburg [Proberaum]

		infrastruktur = new ArrayList<String>();
		infrastruktur.add("Proberaum");
		infrastruktur.add("Stadion");
		ort = new Ort("St. Poelten", infrastruktur);
		System.out.println(ort.toDetailString());
		orte.add(ort);

		infrastruktur = new ArrayList<String>();
		infrastruktur.add("Musikgeschaeft");
		infrastruktur.add("Gitarren Fachhandel");
		infrastruktur.add("Proberaum");
		infrastruktur.add("Stadion");
		ort = new Ort("Wien", infrastruktur);
		System.out.println(ort.toDetailString());
		orte.add(ort);

		infrastruktur = new ArrayList<String>();
		infrastruktur.add("Proberaum");
		ort = new Ort("Korneuburg", infrastruktur);
		System.out.println(ort.toDetailString());
		orte.add(ort);
	}

	public static void testeOrte(Band band) {
		List<Selector<Ort>> selektoren;

		// Testfall: Ausgabe aller Orte
		//
		// Erwartete Ausgabe:
		// [St. Poelten, Wien, Korneuburg]

		System.out.println();
		System.out.println(band.getOrte());

		// Testfall: Ausgabe von Wien
		//
		// Erwartete Ausgabe:
		// [Wien]

		selektoren = new ArrayList<Selector<Ort>>();
		selektoren.add(new Ort.BezeichnungSelektor("Wien"));

		System.out.println();
		System.out.println(band.getOrte(selektoren));

		// Testfall: Ausgabe aller Orte mit Stadion
		//
		// Erwartete Ausgabe:
		// [St. Poelten, Wien]

		selektoren = new ArrayList<Selector<Ort>>();
		selektoren.add(new Ort.InfrastrukturSelektor("Stadion"));

		System.out.println();
		System.out.println(band.getOrte(selektoren));

		// Testfall: Ausgabe aller Orte mit Musikgeschaeft
		//
		// Erwartete Ausgabe:
		// [Wien]

		selektoren = new ArrayList<Selector<Ort>>();
		selektoren.add(new Ort.InfrastrukturSelektor("Musikgeschaeft"));

		System.out.println();
		System.out.println(band.getOrte(selektoren));
	}

	public static void erstelleTermine(Band band) {
		Termin termin;
		Ort ort;
		Date von;
		Date bis;
		List<Mitglied> mitglieder;

		// Testfall: Hinzufuegen von Terminen
		//
		// Erwartete Ausgabe:
		// Auftritt: Wien [16.10.2012 06:00 - 16.10.2012 11:00], Kosten: 500,00,
		// Umsatz: 10.000,00
		// Probe: St. Poelten [20.10.2012 08:00 - 20.10.2012 04:00], Kosten:
		// 100,00, Umsatz: 0,00
		// Probe: Korneuburg [21.10.2012 07:00 - 21.10.2012 04:00], Kosten:
		// 200,00, Umsatz: 0,00
		// Auftritt: Wien [22.10.2012 08:00 - 23.10.2012 01:00], Kosten: 200,00,
		// Umsatz: 8.000,00

		ort = band.getOrte(new Ort.BezeichnungSelektor("Wien")).getFirst();
		mitglieder = band.getMitglieder(new Mitglied.TypSelector(false))
				.asList(); // alle permanenten Mitglieder
		von = toDate(2012, 10, 16, 18, 0);
		bis = toDate(2012, 10, 16, 23, 0);
		termin = new Termin(Termin.Typ.Auftritt, ort, von, bis, 500, 10000,
				mitglieder);
		System.out.println(termin.toDetailString());
		band.sendeTerminvorschlag(termin);

		ort = band.getOrte(new Ort.BezeichnungSelektor("St. Poelten"))
				.getFirst();
		mitglieder = band
				.getMitglieder(new Mitglied.InstrumentSelektor("Bass"))
				.asList(); // alle Bass-Spieler
		von = toDate(2012, 10, 20, 8, 0);
		bis = toDate(2012, 10, 20, 16, 0);
		termin = new Termin(Termin.Typ.Probe, ort, von, bis, 100, 0, mitglieder);
		System.out.println(termin.toDetailString());
		band.sendeTerminvorschlag(termin);

		ort = band.getOrte(new Ort.BezeichnungSelektor("Korneuburg"))
				.getFirst();
		mitglieder = band.getMitglieder(new Mitglied.TypSelector(true))
				.asList(); // alle Ersatzmitglieder
		von = toDate(2012, 10, 21, 7, 0);
		bis = toDate(2012, 10, 21, 16, 0);
		termin = new Termin(Termin.Typ.Probe, ort, von, bis, 200, 0, mitglieder);
		System.out.println(termin.toDetailString());
		band.sendeTerminvorschlag(termin);

		for (Mitglied mitglied : band.getMitglieder()) {
			Queue<Terminvorschlag> vorschlaege = mitglied
					.getTerminvorschlaege();
			while (!vorschlaege.isEmpty()) {
				vorschlaege.poll().accept(mitglied);
			}
		}

		ort = band.getOrte(new Ort.BezeichnungSelektor("Wien")).getFirst();
		mitglieder = band.getMitglieder(
				new Mitglied.NameSelektor("Billie Joe Armstrong", "Bob Bass",
						"Al Sobrante", "Tre Cool")).asList();
		von = toDate(2012, 10, 22, 20, 0);
		bis = toDate(2012, 10, 23, 1, 0);
		termin = new Termin(Termin.Typ.Auftritt, ort, von, bis, 200, 8000,
				mitglieder);
		System.out.println(termin.toDetailString());
		band.sendeTerminvorschlag(termin);

		for (Mitglied mitglied : band.getMitglieder()) {
			Queue<Terminvorschlag> vorschlaege = mitglied
					.getTerminvorschlaege();
			while (!vorschlaege.isEmpty()) {
				vorschlaege.poll().accept(mitglied);
			}
		}
	}

	private static void testeTermine() {
		Band band = new Band("Sido", "Berlin-Rap", 4);
		Mitglieder mg = band.getMitglieder();
		;
		List<String> infrastruktur;
		Mitglieder teilnehmer;
		Zeitraum zeitraum;
		Termin termin;
		Ort ort;
		Date von;
		Date bis;
		boolean erfolgreich;

		// Erstelle Mitglieder
		zeitraum = new Zeitraum(toDate(1999, 5, 10));
		mg.add(new Mitglied("Sido", "12345", "Gesang", zeitraum, false));

		zeitraum = new Zeitraum(toDate(1999, 10, 10));
		mg.add(new Mitglied("Bg Girl1", "123765", "Getanze", zeitraum, false));

		zeitraum = new Zeitraum(toDate(1999, 10, 10));
		mg.add(new Mitglied("Bg Girl2", "3412345", "Getanze", zeitraum, false));

		zeitraum = new Zeitraum(toDate(2002, 1, 25));
		mg.add(new Mitglied("Bg Girl3", "1234475", "Getanze", zeitraum, true));

		zeitraum = new Zeitraum(toDate(2002, 1, 25));
		mg.add(new Mitglied("Bg Girl4", "12342315", "Getanze", zeitraum, true));

		zeitraum = new Zeitraum(toDate(2012, 25, 8));
		mg.add(new Mitglied("Heinzl", "12345", "Gesang", zeitraum, true));

		zeitraum = new Zeitraum(toDate(2010, 9, 1));
		mg.add(new Mitglied("Sidos Double", "1234778", "Gesang", zeitraum, true));

		// Erstelle Orte
		infrastruktur = new ArrayList<String>();
		infrastruktur.add("Umkleideraum");
		infrastruktur.add("Presseraum");
		infrastruktur.add("Proberaum");
		infrastruktur.add("Buehne");
		infrastruktur.add("10000Watt Sound");
		band.getOrte().add(new Ort("Kueniglberg", infrastruktur));

		infrastruktur = new ArrayList<String>();
		infrastruktur.add("Umkleideraum");
		infrastruktur.add("Proberaum");
		infrastruktur.add("Buehne");
		infrastruktur.add("5000Watt Sound");
		band.getOrte().add(new Ort("Szene Wien", infrastruktur));

		// Testfall: Termin hinzufuegen mit allen Mitgliedern
		// Schlaegt fehl, da Ersatzmitglieder noch keine Proben haben
		//
		// Erwartete Ausgabe:
		// Termin erstellen erfolgreich = false
		// Ersatzmitglieder haben noch keine Probe

		ort = band.getOrte(new Ort.BezeichnungSelektor("Kueniglberg"))
				.getFirst();
		von = toDate(2012, 4, 1);
		bis = toDate(2012, 4, 1);
		teilnehmer = band.getMitglieder();
		termin = new Termin(Termin.Typ.Auftritt, ort, von, bis, 2500.99, .0,
				teilnehmer.asList());
		erfolgreich = band.sendeTerminvorschlag(termin);

		System.out.println("Termin erstellen erfolgreich = " + erfolgreich);
		System.out.println("Ersatzmitglieder haben noch keine Proben");
		System.out.println();

		// Testfall: Termin hinzufuegen mit regulaeren Mitgliedern
		//
		// Erwartete Ausgabe:
		// Termin erstellen erfolgreich = true

		// ort wie beim Vorherigen
		von = toDate(2012, 4, 1);
		bis = toDate(2012, 4, 1);
		teilnehmer = band.getMitglieder(new Mitglied.TypSelector(false));
		termin = new Termin(Termin.Typ.Auftritt, ort, von, bis, 2500.99, .0,
				teilnehmer.asList());
		erfolgreich = band.sendeTerminvorschlag(termin);
		System.out.println("Termin erstellen erfolgreich = " + erfolgreich);
		System.out.println();

		// Testfall: Terminvorschlaege anzeigen und akzeptieren
		//
		// Erwartete Ausgabe:
		// Sido: Auftritt: Kueniglberg [01.04.2012 10:25 - 01.04.2012 10:25]
		// Bg Girl1: Auftritt: Kueniglberg [01.04.2012 10:25 - 01.04.2012 10:25]
		// Bg Girl2: Auftritt: Kueniglberg [01.04.2012 10:25 - 01.04.2012 10:25]
		// Bg Girl3: null
		// Bg Girl4: null
		// Heinzl: null
		// Sidos Double: null
		//
		// Akzeptierte Termine: [Auftritt: Kueniglberg [01.04.2012 10:25 -
		// 01.04.2012 10:25]]

		for (Mitglied mitglied : band.getMitglieder()) {
			Terminvorschlag vorschlag = mitglied.getTerminvorschlaege().poll();

			System.out.println(mitglied + ": " + vorschlag);

			if (vorschlag != null) {
				vorschlag.accept(mitglied);
			}
		}

		System.out.println();
		System.out.println("Akzeptierte Termine: " + band.getTermine());
		System.out.println();

		// Testfall: Termin veraendern und Nachrichten ausgeben
		//
		// Erwartete Ausgabe:
		// Geaendert:
		// Auftritt: Kueniglberg [02.04.2012 11:40 - 02.04.2012 11:40], Kosten:
		// 0,00, Umsatz: 2.500,00
		//
		// Sido: Auftritt: Kueniglberg [01.04.2012 11:35 - 01.04.2012 11:35]
		// wurde geaendert: Umsatz: 0.0 -> 2500.0
		// Sido: Auftritt: Kueniglberg [01.04.2012 11:35 - 01.04.2012 11:35]
		// wurde geaendert: Kosten: 2500.99 -> 0.0
		// Sido: Auftritt: Kueniglberg [01.04.2012 11:35 - 01.04.2012 11:35]
		// wurde geaendert: [01.04.2012 - 01.04.2012] -> [02.04.2012 -
		// 02.04.2012]
		// Bg Girl1: Auftritt: Kueniglberg [01.04.2012 11:35 - 01.04.2012 11:35]
		// wurde geaendert: Umsatz: 0.0 -> 2500.0
		// Bg Girl1: Auftritt: Kueniglberg [01.04.2012 11:35 - 01.04.2012 11:35]
		// wurde geaendert: Kosten: 2500.99 -> 0.0
		// Bg Girl1: Auftritt: Kueniglberg [01.04.2012 11:35 - 01.04.2012 11:35]
		// wurde geaendert: [01.04.2012 - 01.04.2012] -> [02.04.2012 -
		// 02.04.2012]
		// Bg Girl2: Auftritt: Kueniglberg [01.04.2012 11:35 - 01.04.2012 11:35]
		// wurde geaendert: Umsatz: 0.0 -> 2500.0
		// Bg Girl2: Auftritt: Kueniglberg [01.04.2012 11:35 - 01.04.2012 11:35]
		// wurde geaendert: Kosten: 2500.99 -> 0.0
		// Bg Girl2: Auftritt: Kueniglberg [01.04.2012 11:35 - 01.04.2012 11:35]
		// wurde geaendert: [01.04.2012 - 01.04.2012] -> [02.04.2012 -
		// 02.04.2012]

		termin = band.getTermine().getFirst();
		termin.setEinnahmen(2500);
		termin.setAusgaben(0);
		termin.setZeitraum(toDate(2012, 4, 2), toDate(2012, 4, 2));

		System.out.println("Geaendert:");
		System.out.println(termin.toDetailString());
		System.out.println();

		for (Mitglied mitglied : band.getMitglieder()) {
			Queue<String> nachrichten = mitglied.getNachrichten();
			while (!nachrichten.isEmpty()) {
				System.out.println(mitglied + ": " + nachrichten.poll());
			}
		}
		System.out.println();

		// Testfall: Letzte veraenderung rueckgaengig machen und Nachrichten
		// ausgeben
		//
		// Erwartete Ausgabe:
		// 1x Undo:
		// Auftritt: Kueniglberg [01.04.2012 11:40 - 01.04.2012 11:40], Kosten:
		// 0,00, Umsatz: 2.500,00
		//
		// Sido: Auftritt: Kueniglberg [01.04.2012 11:35 - 01.04.2012 11:35]
		// wurde geaendert: Umsatz: 0.0 -> 2500.0
		// Sido: Auftritt: Kueniglberg [01.04.2012 11:35 - 01.04.2012 11:35]
		// wurde geaendert: Kosten: 2500.99 -> 0.0
		// Sido: Auftritt: Kueniglberg [01.04.2012 11:35 - 01.04.2012 11:35]
		// wurde geaendert: [01.04.2012 - 01.04.2012] -> [02.04.2012 -
		// 02.04.2012]
		// Bg Girl1: Auftritt: Kueniglberg [01.04.2012 11:35 - 01.04.2012 11:35]
		// wurde geaendert: Umsatz: 0.0 -> 2500.0
		// Bg Girl1: Auftritt: Kueniglberg [01.04.2012 11:35 - 01.04.2012 11:35]
		// wurde geaendert: Kosten: 2500.99 -> 0.0
		// Bg Girl1: Auftritt: Kueniglberg [01.04.2012 11:35 - 01.04.2012 11:35]
		// wurde geaendert: [01.04.2012 - 01.04.2012] -> [02.04.2012 -
		// 02.04.2012]
		// Bg Girl2: Auftritt: Kueniglberg [01.04.2012 11:35 - 01.04.2012 11:35]
		// wurde geaendert: Umsatz: 0.0 -> 2500.0
		// Bg Girl2: Auftritt: Kueniglberg [01.04.2012 11:35 - 01.04.2012 11:35]
		// wurde geaendert: Kosten: 2500.99 -> 0.0
		// Bg Girl2: Auftritt: Kueniglberg [01.04.2012 11:35 - 01.04.2012 11:35]
		// wurde geaendert: [01.04.2012 - 01.04.2012] -> [02.04.2012 -
		// 02.04.2012]

		termin.undo();

		System.out.println("1x Undo:");
		System.out.println(termin.toDetailString());
		System.out.println();

		for (Mitglied mitglied : band.getMitglieder()) {
			Queue<String> nachrichten = mitglied.getNachrichten();
			while (!nachrichten.isEmpty()) {
				System.out.println(mitglied + ": " + nachrichten.poll());
			}
		}
		System.out.println();

		// Testfall: Termin entfernen und Nachrichten ausgeben
		//
		// Erwartete Ausgabe:
		// Anzahl entfernter Termine: 1
		//
		// Sido: Auftritt: Kueniglberg [01.04.2012 10:49 - 01.04.2012 10:49]
		// wurde entfernt!
		// Bg Girl1: Auftritt: Kueniglberg [01.04.2012 10:49 - 01.04.2012 10:49]
		// wurde entfernt!
		// Bg Girl2: Auftritt: Kueniglberg [01.04.2012 10:49 - 01.04.2012 10:49]
		// wurde entfernt!

		int entfernt = band.getTermine().remove();
		System.out.println("Anzahl entfernter Termine: " + entfernt);
		System.out.println();

		for (Mitglied mitglied : band.getMitglieder()) {
			Queue<String> nachrichten = mitglied.getNachrichten();
			while (!nachrichten.isEmpty()) {
				System.out.println(mitglied + ": " + nachrichten.poll());
			}
		}
		System.out.println();

		// Testfall: geloeschten Termin wiederherstellen
		//
		// Erwartete Ausgabe:
		// Wiederhergestellte Termine: [Auftritt: Kueniglberg [01.04.2012 10:56
		// - 01.04.2012 10:56]]

		Termine termine = band.getTermine(new Termin.TypSelektor(
				Termin.Typ.Auftritt));
		termine.restore();

		for (Mitglied m : teilnehmer) {
			Terminvorschlag tv = m.getTerminvorschlaege().poll();
			if (tv != null) {
				tv.accept(m);
			}
		}
		System.out.println("Wiederhergestellte Termine: " + termine);

		// Testfall: 5 Proben erstellen
		//
		// Erwartete Ausgabe:
		// Termin erstellen erfolgreich = true
		// Termin erstellen erfolgreich = true
		// Termin erstellen erfolgreich = true
		// Termin erstellen erfolgreich = true
		// Termin erstellen erfolgreich = true

		ort = band.getOrte(new Ort.BezeichnungSelektor("Kueniglberg"))
				.getFirst();
		teilnehmer = band.getMitglieder();

		von = toDate(2012, 6, 15);
		bis = toDate(2012, 6, 16);
		termin = new Termin(Termin.Typ.Probe, ort, von, bis, 2600.99, .0,
				teilnehmer.asList());
		System.out.println("Termin erstellen erfolgreich = "
				+ band.sendeTerminvorschlag(termin));

		von = toDate(2012, 6, 6);
		bis = toDate(2012, 6, 7);
		termin = new Termin(Termin.Typ.Probe, band.getOrte().getFirst(), von,
				bis, 2600.99, .0, teilnehmer.asList());
		System.out.println("Termin erstellen erfolgreich = "
				+ band.sendeTerminvorschlag(termin));

		von = toDate(2012, 6, 7);
		bis = toDate(2012, 6, 8);
		termin = new Termin(Termin.Typ.Probe, ort, von, bis, 2700.99, .0,
				teilnehmer.asList());
		System.out.println("Termin erstellen erfolgreich = "
				+ band.sendeTerminvorschlag(termin));

		von = toDate(2012, 6, 8);
		bis = toDate(2012, 6, 9);
		termin = new Termin(Termin.Typ.Probe, band.getOrte().getFirst(), von,
				bis, 2800.99, .0, teilnehmer.asList());
		System.out.println("Termin erstellen erfolgreich = "
				+ band.sendeTerminvorschlag(termin));

		von = toDate(2012, 6, 9);
		bis = toDate(2012, 6, 10);
		termin = new Termin(Termin.Typ.Probe, ort, von, bis, 2900.99, .0,
				teilnehmer.asList());
		System.out.println("Termin erstellen erfolgreich = "
				+ band.sendeTerminvorschlag(termin));
		System.out.println();

		// Testfall: 4 Proben aktzeptieren, eine Probe wird abgelehnt
		//
		// Erwartete Ausgabe:
		// Bg Girl1:
		// "Sido: Ne, nicht mit mir!!! - Probe: Kueniglberg [15.06.2012 11:16 - 16.06.2012 11:16]"
		// Bg Girl2:
		// "Sido: Ne, nicht mit mir!!! - Probe: Kueniglberg [15.06.2012 11:16 - 16.06.2012 11:16]"
		// Bg Girl3:
		// "Sido: Ne, nicht mit mir!!! - Probe: Kueniglberg [15.06.2012 11:16 - 16.06.2012 11:16]"
		// Bg Girl4:
		// "Sido: Ne, nicht mit mir!!! - Probe: Kueniglberg [15.06.2012 11:16 - 16.06.2012 11:16]"
		// Heinzl:
		// "Sido: Ne, nicht mit mir!!! - Probe: Kueniglberg [15.06.2012 11:16 - 16.06.2012 11:16]"
		// Sidos Double:
		// "Sido: Ne, nicht mit mir!!! - Probe: Kueniglberg [15.06.2012 11:16 - 16.06.2012 11:16]"

		Mitglied m = band.getMitglieder(new Mitglied.NameSelektor("Sido"))
				.getFirst();
		m.getTerminvorschlaege().poll().decline(m, "Ne, nicht mit mir!!!");

		for (Mitglied mitglied : band.getMitglieder()) {
			Queue<Terminvorschlag> vorschlaege = mitglied
					.getTerminvorschlaege();
			Queue<String> nachrichten = mitglied.getNachrichten();

			while (!vorschlaege.isEmpty()) {
				vorschlaege.poll().accept(mitglied);
			}
			while (!nachrichten.isEmpty()) {
				System.out.println(mitglied + ": \"" + nachrichten.poll()
						+ "\"");
			}
		}
		System.out.println();

		// Testfall: Auftritt anlegen an dem alle Mitglieder teilnehmen
		// Da alle Ersatzmitglieder mehr als 4 Proben im letzten Jahr absolviert
		// haben, funktioniert das Erstellen.
		//
		// Erwartete Ausgabe:
		// Auftritt erstellen erfolgreich = true

		von = toDate(2012, 7, 20);
		bis = toDate(2012, 7, 20);
		teilnehmer = band.getMitglieder();
		termin = new Termin(Termin.Typ.Auftritt, ort, von, bis, 17999.98,
				57025.11, teilnehmer.asList());

		erfolgreich = band.sendeTerminvorschlag(termin);
		System.out.println("Auftritt erstellen erfolgreich = " + erfolgreich);
		System.out.println();

		for (Mitglied mitglieder : band.getMitglieder()) {
			Terminvorschlag tv = mitglieder.getTerminvorschlaege().poll();
			while (tv != null) {
				tv.accept(mitglieder);
				tv = mitglieder.getTerminvorschlaege().poll();
			}
		}

		// Testfall: Termine anzeigen (mit verschiedenen Abfragen)
		//
		// Erwartete Ausgabe:
		// Alle Termine:
		// [Auftritt: Kueniglberg [01.04.2012 11:28 - 01.04.2012 11:28],
		// Probe: Kueniglberg [06.06.2012 11:28 - 07.06.2012 11:28],
		// Probe: Kueniglberg [07.06.2012 11:28 - 08.06.2012 11:28],
		// Probe: Kueniglberg [08.06.2012 11:28 - 09.06.2012 11:28],
		// Probe: Kueniglberg [09.06.2012 11:28 - 10.06.2012 11:28],
		// Auftritt: Kueniglberg [20.07.2012 11:28 - 20.07.2012 11:28]]
		//
		// Alle Termine ab 8.6.2012:
		// [Probe: Kueniglberg [08.06.2012 11:28 - 09.06.2012 11:28],
		// Probe: Kueniglberg [09.06.2012 11:28 - 10.06.2012 11:28],
		// Auftritt: Kueniglberg [20.07.2012 11:28 - 20.07.2012 11:28]]
		//
		// Alle Proben:
		// [Probe: Kueniglberg [06.06.2012 11:28 - 07.06.2012 11:28],
		// Probe: Kueniglberg [07.06.2012 11:28 - 08.06.2012 11:28],
		// Probe: Kueniglberg [08.06.2012 11:28 - 09.06.2012 11:28],
		// Probe: Kueniglberg [09.06.2012 11:28 - 10.06.2012 11:28]]
		//
		// Alle Proben ab 8.6.2012:
		// [Probe: Kueniglberg [08.06.2012 11:28 - 09.06.2012 11:28],
		// Probe: Kueniglberg [09.06.2012 11:28 - 10.06.2012 11:28]]
		//
		// Alle Auftritte:
		// [Auftritt: Kueniglberg [01.04.2012 11:28 - 01.04.2012 11:28],
		// Auftritt: Kueniglberg [20.07.2012 11:28 - 20.07.2012 11:28]]
		//
		// Alle Auftritte ab 8.6.2012:
		// [Auftritt: Kueniglberg [20.07.2012 11:28 - 20.07.2012 11:28]]

		zeitraum = new Zeitraum(toDate(2012, 6, 8));
		List<Selector<Termin>> selektoren = new ArrayList<Selector<Termin>>();
		selektoren.add(new Termin.ZeitraumSelektor(zeitraum));

		System.out.println("Alle Termine: " + band.getTermine());
		System.out.println();

		System.out.println("Alle Termine ab 8.6.2012: "
				+ band.getTermine(selektoren));
		System.out.println();

		selektoren = new ArrayList<Selector<Termin>>();
		selektoren.add(new Termin.TypSelektor(Termin.Typ.Probe));
		System.out.println("Alle Proben: " + band.getTermine(selektoren));
		System.out.println();

		selektoren.add(new Termin.ZeitraumSelektor(zeitraum));
		System.out.println("Alle Proben ab 8.6.2012: "
				+ band.getTermine(selektoren));
		System.out.println();

		selektoren = new ArrayList<Selector<Termin>>();
		selektoren.add(new Termin.TypSelektor(Termin.Typ.Auftritt));
		System.out.println("Alle Auftritte: " + band.getTermine(selektoren));
		System.out.println();

		selektoren.add(new Termin.ZeitraumSelektor(zeitraum));
		System.out.println("Alle Auftritte ab 8.6.2012: "
				+ band.getTermine(selektoren));
		System.out.println();
	}

	public static void erstellePosten(Band band) {
		GuV guv = band.getGuV();
		Posten posten;

		// Testfall: Hinzufuegen von Posten
		//
		// Erwartete Ausgabe:
		// 20.10.2012: Kuenstlerfoerderung (+5.000,00/-0,00)
		// 18.10.2012: Schlagzeugreperatur (+0,00/-500,00)
		// 21.10.2012: Studiozubehoer (+0,00/-3.000,00)

		posten = new Posten(5000, 0, "Kuenstlerfoerderung",
				toDate(2012, 10, 20));
		guv.add(posten);
		System.out.println(posten.toDetailString());

		posten = new Posten(0, 500, "Schlagzeugreperatur", toDate(2012, 10, 18));
		guv.add(posten);
		System.out.println(posten.toDetailString());

		posten = new Posten(0, 3000, "Studiozubehoer", toDate(2012, 10, 21));
		guv.add(posten);
		System.out.println(posten.toDetailString());
	}

	public static void testeGuV(Band band) {
		GuV guv;
		List<Selector<Posten>> selektoren;
		Zeitraum zeitraum;

		// Testfall: Ausgabe aller GuV-Posten (ohne Termine)
		//
		// Erwartete Ausgabe:
		// [Kuenstlerfoerderung: +5.000,00, Schlagzeugreperatur: -500,00,
		// Studiozubehoer: -3.000,00]

		System.out.println();
		System.out.println(band.getGuV());

		// Testfall: Ausgabe von Einnahmen, Ausgaben und Gewinn aller Posten.
		// (incl. Termine)
		//
		// Erwartete Ausgabe:
		// Einnahmen: 23000.0
		// Ausgaben: 4500.0
		// Gewinn: 18500.0

		guv = band.getGuV();

		System.out.println();
		System.out.println("Einnahmen: " + guv.getEinnahmen());
		System.out.println("Ausgaben: " + guv.getAusgaben());
		System.out.println("Gewinn: " + guv.getGewinn());

		// Testfall: Ausgabe aller GuV-Posten (ohne Termine)
		// seit 19.10.2012
		//
		// Erwartete Ausgabe:
		// [Kuenstlerfoerderung: +5.000,00, Studiozubehoer: -3.000,00]

		zeitraum = new Zeitraum(toDate(2012, 10, 19));
		selektoren = new ArrayList<Selector<Posten>>();
		selektoren.add(new Posten.ZeitraumSelektor(zeitraum));
		guv = band.getGuV(selektoren);

		System.out.println();
		System.out.println(guv);

		// Testfall: Ausgabe von Einnahmen, Ausgaben und Gewinn aller Posten.
		// (incl. Termine)
		// seit 19.10.2012
		//
		// Erwartete Ausgabe:
		// Einnahmen: 13000.0
		// Ausgaben: 3500.0
		// Gewinn: 9500.0

		System.out.println();
		System.out.println("Einnahmen: " + guv.getEinnahmen());
		System.out.println("Ausgaben: " + guv.getAusgaben());
		System.out.println("Gewinn: " + guv.getGewinn());
	}

	public static void erstelleRepertoire(Band band) {
		Songs songs = band.getRepertoire();
		Song song;
		Zeitraum zeitraum;
		List<Variante> varianten;

		// Testfall: Hinzufuegen von Mitgliedern
		//
		// Erwartete Ausgabe:
		// Holiday [06.03.2005 - ] [Normal: 3:20, Acoustic: 3:30]
		// Basketcase [03.02.1994 - 03.05.2004] [Normal: 3:15]
		// American Idiot [04.04.2004 - ] [Normal: 3:15, Acoustic: 3:20]

		zeitraum = new Zeitraum(toDate(2005, 3, 6));
		varianten = new ArrayList<Variante>();
		varianten.add(new Variante("Normal", 200));
		varianten.add(new Variante("Acoustic", 210));
		song = new Song("Holiday", zeitraum, varianten);
		songs.add(song);
		System.out.println(song.toDetailString());

		zeitraum = new Zeitraum(toDate(1994, 2, 3), toDate(2004, 5, 3));
		varianten = new ArrayList<Variante>();
		varianten.add(new Variante("Normal", 195));
		song = new Song("Basketcase", zeitraum, varianten);
		songs.add(song);
		System.out.println(song.toDetailString());

		zeitraum = new Zeitraum(toDate(2004, 4, 4));
		varianten = new ArrayList<Variante>();
		varianten.add(new Variante("Normal", 195));
		varianten.add(new Variante("Acoustic", 200));
		song = new Song("American Idiot", zeitraum, varianten);
		songs.add(song);
		System.out.println(song.toDetailString());
	}

	private static void testeRepertoire(Band band) {
		List<Selector<Song>> songSelector;
		List<Selector<Variante>> variantenSelector;

		System.out.println("Repertoire: ");
		variantenSelector = new ArrayList<Selector<Variante>>();
		variantenSelector.add(new Variante.BezeichnungSelektor("Acoustic"));

		// Testfall: Zeige das gesamte Repertoire an
		//
		// Erwartete Ausgabe:
		// [Holiday, Basketcase, American Idiot]
		System.out.println(band.getRepertoire());

		// Testfall: Zeige die Varianten des gesamten Repertoire an
		//
		// Erwartete Ausgabe:
		// [Holiday (Normal, 3:20), Holiday (Acoustic, 3:30), Basketcase
		// (Normal, 3:15), American Idiot (Normal, 3:15), American Idiot
		// (Acoustic, 3:20)]
		System.out.println(band.getRepertoire().getSongVarianten());

		// Testfall: Zeige die Varianten des gesamten Repertoire an (nur die
		// "Acoustic" Varianten)
		//
		// Erwartete Ausgabe:
		// [Holiday (Acoustic, 3:30), American Idiot (Acoustic, 3:20)]
		System.out.println(band.getRepertoire().getSongVarianten(
				variantenSelector));

		System.out.println();
		System.out.println("Repertoire am 1.1.2012: ");
		songSelector = new ArrayList<Selector<Song>>();
		songSelector.add(new Song.ZeitpunktSelektor(toDate(2012, 1, 1)));

		// Testfall: Zeige die Songs vom 1.1.2012 an
		//
		// Erwartete Ausgabe:
		// [Holiday, American Idiot]
		System.out.println(band.getRepertoire(songSelector));

		// Testfall: Zeige die Varianten der Songs vom 1.1.2012 an
		//
		// Erwartete Ausgabe:
		// [Holiday (Normal, 3:20), Holiday (Acoustic, 3:30), American Idiot
		// (Normal, 3:15), American Idiot (Acoustic, 3:20)]
		System.out.println(band.getRepertoire(songSelector).getSongVarianten());

		// Testfall: Zeige die Varianten der Songs vom 1.1.2012 an (nur
		// "Acoustic" Varianten)
		//
		// Erwartete Ausgabe:
		// [Holiday (Acoustic, 3:30), American Idiot (Acoustic, 3:20)]
		System.out.println(band.getRepertoire(songSelector).getSongVarianten(
				variantenSelector));

		// Testfall: Zeige das Repertoire zu einem Termin am 2.3.2012
		//
		// Erwartete Ausgabe:
		// Repertoire zum Termin
		// "Auftritt: St. Poelten [02.03.2012 08:00 - 02.03.2012 11:00]":
		// [Holiday, American Idiot]
		Date von = toDate(2012, 3, 2, 20, 0);
		Date bis = toDate(2012, 3, 2, 23, 0);
		Termin termin = new Termin(Termin.Typ.Auftritt, band.getOrte()
				.getFirst(), von, bis, 1000, 10000, new ArrayList<Mitglied>());
		System.out.println();
		System.out.println("Repertoire zum Termin \"" + termin + "\":");
		System.out.println(band.getRepertoire(new Song.TerminSelector(termin)));
	}

	public static void testeSicherung(Band band) {
		Programm prog;

		// Testfall: Sichern und Laden des Programmzustandes
		//
		// Erwartete Ausgabe:
		// Green Day, Rock
		// Mitglieder: [Billie Joe Armstrong, Mike Dirnt, Al Sobrante, Tre Cool,
		// Schlagzeug Tim, Gitarren Joe, Bob Bass]
		// Termine: [Auftritt: Wien [16.10.2012 06:00 - 16.10.2012 11:00],
		// Probe: St. Poelten [20.10.2012 08:00 - 20.10.2012 04:00],
		// Probe: Korneuburg [21.10.2012 07:00 - 21.10.2012 04:00],
		// Auftritt: Wien [22.10.2012 08:00 - 23.10.2012 01:00]]
		// Repertoire: [Holiday, Basketcase, American Idiot]
		// Orte: [St. Poelten, Wien, Korneuburg]
		// GuV: [Kuenstlerfoerderung: +5.000,00, Schlagzeugreperatur: -500,00,
		// Studiozubehoer: -3.000,00]

		prog = new Programm(false);
		prog.addBand(band);
		prog.quit();

		prog = new Programm();
		band = prog.getBand("Green Day");

		System.out.println(band);
		System.out.println("Mitglieder: " + band.getMitglieder());
		System.out.println("Termine: " + band.getTermine());
		System.out.println("Repertoire: " + band.getRepertoire());
		System.out.println("Orte: " + band.getOrte());
		System.out.println("GuV: " + band.getGuV());
	}
}
