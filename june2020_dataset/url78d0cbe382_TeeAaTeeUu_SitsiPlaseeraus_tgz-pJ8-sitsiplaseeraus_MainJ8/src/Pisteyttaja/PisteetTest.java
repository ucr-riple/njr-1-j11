package Pisteyttaja;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sitsiplaseeraus.Sitsit;
import sitsiplaseeraus.PaikkaTest;
import sitsiplaseeraus.random.RandomGenerator;

public class PisteetTest {

    private RandomGenerator random;
    private Sitsit sitsit;
    private PaikkaTest sitsaaja;
    private Pisteet pisteet;

    public PisteetTest() {
    }

    @Before
    public void setUp() {
        this.random = new RandomGenerator();
        this.sitsit = new Sitsit(1);
    }

    @Test
    public void testPalautaPisteetToimii() {
        for (int i = 0; i < 1000; i++) {
            double luku = 0.0;
            
            this.sitsit = new Sitsit(3);
            random.taytaRandomDatalla(30, 30*10, this.sitsit);

            this.pisteet = new Pisteet(this.sitsit);
            double pisteet = this.pisteet.palautaPisteet();
            if (pisteet != 0) {
                luku = pisteet;
            }

            assertTrue(this.pisteet.onkoYhteyksia());
            assertTrue(luku != 0.0);
        }
    }
}