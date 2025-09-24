package sitsiplaseeraus.random;

import jarjestelija.Jarjestaja;
import java.io.FileNotFoundException;
import java.io.IOException;
import omatTietorakenteet.Hakemisto;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sitsiplaseeraus.PaikkaTest;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.Sitsit;

public class RandomGeneratorTest {

    private Jarjestaja jarjestaja;
    private RandomGenerator random;
    private Sitsit sitsit;

    public RandomGeneratorTest() {
    }

    @Before
    public void setUp() {
        this.sitsit = new Sitsit(3);
        this.jarjestaja = new Jarjestaja(sitsit);
        this.random = new RandomGenerator();
    }

    @Test
    public void testPalauttaaEriNimia() throws FileNotFoundException, IOException {
        RandomGenerator instance = new RandomGenerator();

        String result1 = instance.randomNimi();
        System.out.println(result1);

        String result2 = instance.randomNimi();
        System.out.println(result2);

        assertEquals(false, result1.equals(result2));
    }

    @Test
    public void randomDatanLisaysTuottaaJotain() {
        this.random.taytaRandomDatalla(16, 40, this.sitsit);

        assertTrue(sitsit.getPaikka(7).getSitsaaja().getNimi().length() >= 3);
    }

    @Test
    public void randomDatanLisaysTuottaaOikeanMaaranSitsaajia() {
        this.random.taytaRandomDatalla(50, 300, sitsit);

        assertEquals(50, sitsit.sitsaajienMaara());
    }

    @Test
    public void randomDatanLisaysToimiiMyosSarjatuotantonaSuuressaMittakaavassa() {
        int monesko = 0;
        for (int i = 0; i < 1000; i++) {
            this.sitsit = new Sitsit(3);
            this.jarjestaja = new Jarjestaja(sitsit);
            
            this.random = new RandomGenerator();
            this.random.taytaRandomDatalla(200, 5000, sitsit);

            assertEquals(5000, sitsit.yhteyksienMaara());
            assertEquals(200, sitsit.sitsaajienMaara());
        }
    }
    @Test
    public void puolisojaJaAvecejaOnOikeaMaara() {
        int avecMaara = 0;
        int pareja = 0;
        int puolisoMaara = 0;
        int puolisoja = 0;
        for (Sitsaaja sitsaaja : sitsit.getSitsaajat()) {
            if(sitsaaja.avecIsSet()) {
                avecMaara++;
                if(sitsaaja.getAvec().equals(sitsaaja.getAvec().getAvec().getAvec())) {
                    pareja++;
                }
            }
            if(sitsaaja.puolisoIsSet()) {
                puolisoMaara++;
                if(sitsaaja.getPuoliso().equals(sitsaaja.getPuoliso().getPuoliso().getPuoliso())) {
                    puolisoja++;
                }
            }
        }
        assertEquals(avecMaara, pareja);
        assertEquals(puolisoMaara, puolisoja);
    }
}