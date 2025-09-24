package sitsiplaseeraus;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SitsaajaTest {

    Sitsaaja sitsaaja;

    public SitsaajaTest() {
    }

    @Before
    public void setUp() {
        sitsaaja = new Sitsaaja("Testi Nukke");
    }

    @Test
    public void nimiToimii() {
        assertEquals("Testi Nukke", sitsaaja.getNimi());
    }
    
    @Test
    public void yhteydenLisaysJaPoistoToimii() {
        assertEquals(0, sitsaaja.palautaYhteydet().size());
        assertEquals(0, sitsaaja.yhteyksienMaara());
        assertEquals(false, sitsaaja.palautaYhteydet().containsValue(-3));
        
        Sitsaaja toinenSitsaaja = new Sitsaaja("Toinen Ukko");
        sitsaaja.setYhteys(toinenSitsaaja, -3);
        
        assertEquals(1, sitsaaja.palautaYhteydet().size());
        assertEquals(1, sitsaaja.yhteyksienMaara());
        assertEquals(true, sitsaaja.palautaYhteydet().containsKey(toinenSitsaaja));
        assertEquals(true, sitsaaja.palautaYhteydet().containsValue(-3));
        
        sitsaaja.deleteYhteys(toinenSitsaaja);
        
        assertEquals(0, sitsaaja.palautaYhteydet().size());
        assertEquals(0, sitsaaja.yhteyksienMaara());
        assertEquals(false, sitsaaja.palautaYhteydet().containsKey(toinenSitsaaja));
        assertEquals(false, sitsaaja.palautaYhteydet().containsValue(-3));
    }
    
    @Test
    public void sukupuoliToimii() {
        sitsaaja = new Sitsaaja("Joku Tyyppi", true);
        assertEquals(true, sitsaaja.isMies());
        
        sitsaaja = new Sitsaaja("Toinen Tyyppi", false);
        assertEquals(false, sitsaaja.isMies());
    }
    
    @Test
    public void AvecToimii() {
        Sitsaaja sitsaaja2 = new Sitsaaja("Joku Tyyppi", true);      
        Sitsaaja sitsaaja3 = new Sitsaaja("Toinen Tyyppi", false);
        
        assertEquals(false, sitsaaja2.avecIsSet());
        assertEquals(null, sitsaaja2.getAvec());
        
        sitsaaja2.setAvec(sitsaaja3);
        
        assertEquals(true, sitsaaja2.avecIsSet());
        assertEquals(sitsaaja3, sitsaaja2.getAvec());
    }
    
    @Test
    public void PuolisoToimii() {
        Sitsaaja sitsaaja2 = new Sitsaaja("Joku Tyyppi", true);      
        Sitsaaja sitsaaja3 = new Sitsaaja("Toinen Tyyppi", false);
        
        assertEquals(false, sitsaaja2.puolisoIsSet());
        assertEquals(null, sitsaaja2.getPuoliso());
        
        sitsaaja2.setPuoliso(sitsaaja3);
        
        assertEquals(true, sitsaaja2.puolisoIsSet());
        assertEquals(sitsaaja3, sitsaaja2.getPuoliso());
    }
    
    @Test
    public void yhteydenAsettaminenJaPoistaminenToimii() {
        Sitsaaja sitsaaja2 = new Sitsaaja("Joku Tyyppi", true);      
        Sitsaaja sitsaaja3 = new Sitsaaja("Toinen Tyyppi", false);
        
        assertEquals(0, sitsaaja2.yhteyksienMaara());
        assertEquals(true, sitsaaja2.palautaYhteydet().isEmpty());
        
        sitsaaja2.setYhteys(sitsaaja3, 3);
        
        assertEquals(1, sitsaaja2.yhteyksienMaara());
        assertEquals(false, sitsaaja2.palautaYhteydet().isEmpty());
        assertEquals(true, sitsaaja2.palautaYhteydet().containsKey(sitsaaja3));
        
        sitsaaja2.deleteYhteys(sitsaaja2);
        
        assertEquals(1, sitsaaja2.yhteyksienMaara());
        assertEquals(false, sitsaaja2.palautaYhteydet().isEmpty());
        assertEquals(true, sitsaaja2.palautaYhteydet().containsKey(sitsaaja3));
        
        sitsaaja2.deleteYhteys(sitsaaja3);
        
        assertEquals(0, sitsaaja2.yhteyksienMaara());
        assertEquals(true, sitsaaja2.palautaYhteydet().isEmpty());
    }
}
