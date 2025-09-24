package Pisteyttaja;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sitsiplaseeraus.Paikka;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.Sitsit;
import sitsiplaseeraus.random.Random;
import sitsiplaseeraus.random.RandomGenerator;

public class PaikanPisteetTest {

    private RandomGenerator random;
    private Sitsit sitsit;
    private Paikka paikka;
    private PaikanPisteet pisteet;
    private Laskin laskin;

    public PaikanPisteetTest() {
    }

    @Before
    public void setUp() {
        this.laskin = new Laskin();
        this.random = new RandomGenerator();
        this.sitsit = new Sitsit(1);
    }

    @Test
    public void testPalautaPisteet() {
        double luku = 0;

        for (int i = 0; i < 1000; i++) {
            this.sitsit = new Sitsit(2);
            random.taytaRandomDatalla(10, 30, this.sitsit);

            for (Paikka paikka1 : sitsit.getPaikat()) {
                paikka1.getSitsaaja().setPaikka(paikka1);
            }
            
            sitsit.lisaaPaikoilleTiedotAvecinJaPuolisonPaikoista();

            for (int j = 0; j < 10; j++) {
                this.paikka = palautaYhteydellinenSitsaaja();
                this.pisteet = new PaikanPisteet(this.paikka, this.laskin);

                double pointsit = this.pisteet.palautaPisteet();
                if (pointsit != 0) {
                    luku = pointsit;
                }
                assertTrue(this.pisteet.onkoYhteyksia());
            }
            assertTrue(luku != 0);
        }
        

        RandomGenerator.tulostaSitsaajat(sitsit);
        RandomGenerator.tulostaYhteydet(sitsit);
        System.out.println("\n" + "pisteet " + this.pisteet.palautaPisteet() + " sitsaajalta " + this.paikka.getSitsaaja().getNimi() + "\n");
    }

    @Test
    public void pisteidenMuodostusOnKunnollinen() {
        System.out.println("\n\n\nväli\n\n");
        
        
        Sitsaaja tero = new Sitsaaja("Tero Merkki");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(0).setSitsaaja(tero);

        Sitsaaja heikki = new Sitsaaja("Toinen Heikki");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(1).setSitsaaja(heikki);

        Sitsaaja milla = new Sitsaaja("Toinen Milla");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(2).setSitsaaja(milla);

        Sitsaaja hilla = new Sitsaaja("Nelos hilla");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(3).setSitsaaja(hilla);

        Sitsaaja nukke = new Sitsaaja("TestiNukke");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(4).setSitsaaja(nukke);

        Sitsaaja testi = new Sitsaaja("Nukke Testi");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(5).setSitsaaja(testi);

        Sitsaaja tyyppi = new Sitsaaja("Joku Tyyppi");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(6).setSitsaaja(tyyppi);

        Sitsaaja vaan = new Sitsaaja("Ihan Vaan");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(7).setSitsaaja(vaan);

        for (Paikka paikka1 : sitsit.getPaikat()) {
            paikka1.getSitsaaja().setPaikka(paikka1);
        }
        
        sitsit.lisaaPaikoilleTiedotAvecinJaPuolisonPaikoista();

        heikki.setYhteys(tero, 4);

        this.pisteet = new PaikanPisteet(this.sitsit.getPaikka(1), this.laskin);
        assertEquals(4, this.pisteet.palautaPisteet(), 0.01);

        heikki.setYhteys(milla, 1);
        
        System.out.println("kaksi");

        this.pisteet = new PaikanPisteet(this.sitsit.getPaikka(1), this.laskin);
        assertEquals(4 + 1 / Math.hypot(1, 1), this.pisteet.palautaPisteet(), 0.01);

        heikki.setYhteys(hilla, 4);
        
        System.out.println("kolme");

        this.pisteet = new PaikanPisteet(this.sitsit.getPaikka(1), this.laskin);
        assertEquals(1.0 * 4 + 1.0 / Math.hypot(1, 1) + 4, this.pisteet.palautaPisteet(), 0.01);

        heikki.setYhteys(nukke, 3);
        
        System.out.println("4");

        this.pisteet = new PaikanPisteet(this.sitsit.getPaikka(1), this.laskin);
        assertEquals(1.0 * 4 + 1.0 / Math.hypot(1, 1) + 4 + 3.0 / Math.hypot(1, 2), this.pisteet.palautaPisteet(), 0.01);

        heikki.setYhteys(testi, -5);

        System.out.println("5");
        
        this.pisteet = new PaikanPisteet(this.sitsit.getPaikka(1), this.laskin);
        assertEquals(1.0 * 4 + 1 / Math.hypot(1, 1) + 4 + 1.0 * 3 / Math.hypot(1, 2) - 1.0 * 5 / 2, this.pisteet.palautaPisteet(), 0.01);

        heikki.setYhteys(tyyppi, -3);
        
        System.out.println("6");

        this.pisteet = new PaikanPisteet(this.sitsit.getPaikka(1), this.laskin);
        assertEquals(1.0 * 4 + 1 / Math.hypot(1, 1) + 4 + 3 / Math.hypot(1, 2) - 1.0 * 5 / 2 - 1.0 * 3 / Math.hypot(1, 3), this.pisteet.palautaPisteet(), 0.01);

        heikki.setYhteys(vaan, 2);
        
        System.out.println("7");

        this.pisteet = new PaikanPisteet(this.sitsit.getPaikka(1), this.laskin);
        assertEquals(1.0 * 4 + 1 / Math.hypot(1, 1) + 4 + 3 / Math.hypot(1, 2) - 1.0 * 5 / 2 - 1.0 * 3 / Math.hypot(1, 3) + 1.0 * 2 / 3, this.pisteet.palautaPisteet(), 0.01);

        this.pisteet = new PaikanPisteet(this.sitsit.getPaikka(0), this.laskin);
        assertTrue(this.pisteet.palautaPisteet() == 0);
    }

    @Test
    public void pisteidenMuodostusOnKunnollinen2() {
    
    Sitsaaja ukko = new Sitsaaja("Terminen Ukko");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(0).setSitsaaja(ukko);

        Sitsaaja vaan = new Sitsaaja("Ihan Vaan");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(1).setSitsaaja(vaan);

        Sitsaaja tero = new Sitsaaja("Tero Merkki");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(2).setSitsaaja(tero);

        Sitsaaja heikki = new Sitsaaja("Toinen Heikki");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(3).setSitsaaja(heikki);

        Sitsaaja milla = new Sitsaaja("Toinen Milla");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(4).setSitsaaja(milla);

        Sitsaaja hilla = new Sitsaaja("Nelos hilla");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(5).setSitsaaja(hilla);

        Sitsaaja nukke = new Sitsaaja("TestiNukke");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(6).setSitsaaja(nukke);

        Sitsaaja testi = new Sitsaaja("Nukke Testi");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(7).setSitsaaja(testi);
        
        Sitsaaja tyyppi = new Sitsaaja("Joku Tyyppi");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(8).setSitsaaja(tyyppi);

        Sitsaaja pallo = new Sitsaaja("Härö Pallo");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(9).setSitsaaja(pallo);

        Sitsaaja juttu = new Sitsaaja("Pallo juttu");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(10).setSitsaaja(juttu);

        Sitsaaja piilo = new Sitsaaja("Piilo Pallo");
        this.sitsit.addPaikka(1);
        this.sitsit.getPaikka(11).setSitsaaja(piilo);
        
        for (Paikka paikka1 : sitsit.getPaikat()) {
            paikka1.getSitsaaja().setPaikka(paikka1);
        }
        
        sitsit.lisaaPaikoilleTiedotAvecinJaPuolisonPaikoista();
        
        nukke.setYhteys(tero, 4);
        
        this.pisteet = new PaikanPisteet(this.sitsit.getPaikka(6), this.laskin);
        assertEquals(2, this.pisteet.palautaPisteet(), 0.01);
        
        nukke.setYhteys(milla, 1);
        
        this.pisteet = new PaikanPisteet(this.sitsit.getPaikka(6), this.laskin);
        assertEquals(2 + 1, this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(hilla, 4);
        
        this.pisteet = new PaikanPisteet(this.sitsit.getPaikka(6), this.laskin);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1), this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(heikki, 3);
        
        this.pisteet = new PaikanPisteet(this.sitsit.getPaikka(6), this.laskin);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1) + 1.0 * 3 / Math.hypot(1, 2), this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(testi, -5);
        
        this.pisteet = new PaikanPisteet(this.sitsit.getPaikka(6), this.laskin);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1) + 1.0 * 3 / Math.hypot(1, 2) - 5, this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(tyyppi, -3);
        
        this.pisteet = new PaikanPisteet(this.sitsit.getPaikka(6), this.laskin);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1) + 1.0 * 3 / Math.hypot(1, 2) - 5 - 3.0, this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(vaan, -2);
        
        this.pisteet = new PaikanPisteet(this.sitsit.getPaikka(6), this.laskin);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1) + 1.0 * 3 / Math.hypot(1, 2) - 5 - 3.0 - 2.0 / Math.hypot(1, 3), this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(pallo, -5);
        
        this.pisteet = new PaikanPisteet(this.sitsit.getPaikka(6), this.laskin);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1) + 1.0 * 3 / Math.hypot(1, 2) - 5 - 3.0 - 2.0 / Math.hypot(1, 3) - 5.0 / Math.hypot(1, 1), this.pisteet.palautaPisteet() , 0.01);
        
        nukke.setYhteys(piilo, 3);
        
        this.pisteet = new PaikanPisteet(this.sitsit.getPaikka(6), this.laskin);
        assertEquals(2 + 1 + 4 / Math.hypot(1, 1) + 1.0 * 3 / Math.hypot(1, 2) - 5 - 3.0 - 2.0 / Math.hypot(1, 3) - 5.0 / Math.hypot(1, 1) + 3.0 / Math.hypot(1, 2), this.pisteet.palautaPisteet() , 0.01);
                
        this.pisteet = new PaikanPisteet(this.sitsit.getPaikka(2), this.laskin);
        assertTrue(this.pisteet.palautaPisteet() == 0);
    }
    private Paikka palautaYhteydellinenSitsaaja() {
        this.paikka = this.sitsit.getPaikka(Random.luo(this.sitsit.sitsaajienMaara() - 1));

        if (this.paikka.getSitsaaja().yhteyksienMaara() == 0) {
            return palautaYhteydellinenSitsaaja();
        } else {
            return this.paikka;
        }
    }
}