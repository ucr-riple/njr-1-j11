package jarjestelija;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import sitsiplaseeraus.Sitsit;
import sitsiplaseeraus.random.RandomGenerator;

public class OptimoijaTest {
    private Sitsit sitsit;
    private Optimoija optimoija;
    private RandomGenerator random;
    
    public OptimoijaTest() {
    }
    
    @Before
    public void setUp() {
        this.sitsit = new Sitsit(3);
        
        this.random = new RandomGenerator();
        this.random.taytaRandomDatalla(80, 80*5, this.sitsit);
        
        this.optimoija = new Optimoija(this.sitsit);
    }

    @Test
    public void testOptimoiIstumapaikat() {
        double pisteet = this.optimoija.getPisteet().palautaPisteet();
        
        System.out.println("random alun pisteet: " + pisteet);
        
        RandomGenerator.tulostaSitsaajat(sitsit);
        RandomGenerator.tulostaYhteydet(sitsit);
        
        this.optimoija.optimoiIstumapaikat(2, System.currentTimeMillis(), 0.0);
        
        assertTrue(this.optimoija.getPisteet().palautaPisteet() > pisteet);
        
        RandomGenerator.tulostaYhteydet(sitsit);
    }
}
