package sitsiplaseeraus;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PaikkaTest {

    Paikka paikka;
    private Sitsaaja testSitsaaja;
    private Sitsaaja testSitsaaja2;

    public PaikkaTest() {
    }

    @Before
    public void setUp() {
        paikka = new Paikka(1, 7);
        testSitsaaja = new Sitsaaja("Testi Nimi");
        testSitsaaja2 = new Sitsaaja("Toinen Nimi");
    }

    @Test
    public void asettaminenToimii() {
        assertEquals(null, this.paikka.getSitsaaja());

        this.paikka.setSitsaaja(testSitsaaja);
        assertEquals(testSitsaaja, this.paikka.getSitsaaja());

        this.paikka.setSitsaaja(testSitsaaja2);
        assertEquals(testSitsaaja2, this.paikka.getSitsaaja());
    }

    @Test
    public void avecinJaPuolisonPaikatToimivat() {
        Sitsit sitsit = new Sitsit(2);

        for (int i = 0; i < 10; i++) {
            sitsit.addPaikka(1);
        }

        sitsit.lisaaPaikoilleTiedotAvecinJaPuolisonPaikoista();

        assertEquals(-2, sitsit.getPaikka(3).getPaikka() - sitsit.getPaikka(3).getMiehenAvecinPaikka().getPaikka());
        assertEquals(sitsit.getPaikka(3), sitsit.getPaikka(3).getMiehenAvecinPaikka().getNaisenAvecinPaikka());
        assertEquals(false, sitsit.getPaikka(3).getMiehenAvecinPaikka().equals(sitsit.getPaikka(3)));

        assertEquals(2, sitsit.getPaikka(3).getPaikka() - sitsit.getPaikka(3).getNaisenAvecinPaikka().getPaikka());
        assertEquals(sitsit.getPaikka(3), sitsit.getPaikka(3).getNaisenAvecinPaikka().getMiehenAvecinPaikka());
        assertEquals(false, sitsit.getPaikka(3).getNaisenAvecinPaikka().equals(sitsit.getPaikka(3)));

        assertEquals(1, Math.abs(sitsit.getPaikka(3).getPuolisonPaikka().getPaikka() - sitsit.getPaikka(3).getPaikka()));
        assertEquals(sitsit.getPaikka(3), sitsit.getPaikka(3).getPuolisonPaikka().getPuolisonPaikka());
        assertEquals(false, sitsit.getPaikka(3).getPuolisonPaikka().equals(sitsit.getPaikka(3)));



        for (Paikka paikka1 : sitsit.getPaikat()) {
            assertTrue(paikka1.getPuolisonPaikka() != null);
            
            System.out.println(paikka1.getPaikka() + " " + paikka1.getMiehenAvecinPaikka() + " " + paikka1.getNaisenAvecinPaikka());

            if (paikka1.getPaikka() % 2 == 0) {
                if (paikka1.getPaikka() > 0) {
                    System.out.println("1");
                    assertTrue(paikka1.getMiehenAvecinPaikka() != null);
                }
                if (paikka1.getPaikka() < 8) {
                    System.out.println("2");
                    assertTrue(paikka1.getNaisenAvecinPaikka() != null);
                }
            } else {
                if (paikka1.getPaikka() > 1) {
                    System.out.println("3");
                    assertTrue(paikka1.getNaisenAvecinPaikka() != null);
                }
                if (paikka1.getPaikka() < 9) {
                    System.out.println("4");
                    assertTrue(paikka1.getMiehenAvecinPaikka() != null);
                }
            }

        }
    }

    @Test
    public void poydanAsettaminenToimii() {
        assertEquals(1, paikka.getPoyta());
    }

    @Test
    public void paikanAsettaminenToimii() {
        assertEquals(7, paikka.getPaikka());
    }

    @Test
    public void sitsaajanAsettaminenToimii() {
        assertEquals(null, paikka.getSitsaaja());

        paikka.setSitsaaja(testSitsaaja);

        assertEquals(false, paikka.getSitsaaja().equals(testSitsaaja2));
        assertEquals(testSitsaaja, paikka.getSitsaaja());

        paikka.setSitsaaja(testSitsaaja2);

        assertEquals(false, paikka.getSitsaaja().equals(testSitsaaja));
        assertEquals(testSitsaaja2, paikka.getSitsaaja());
    }
    
    @Test
    public void miehenPaikanAsettaminenToimii() {
        paikka.setMiehenPaikka(true);
        assertEquals(true, paikka.isMiehenPaikka());
        
        paikka.setMiehenPaikka(false);
        assertEquals(false, paikka.isMiehenPaikka());
    }
}
