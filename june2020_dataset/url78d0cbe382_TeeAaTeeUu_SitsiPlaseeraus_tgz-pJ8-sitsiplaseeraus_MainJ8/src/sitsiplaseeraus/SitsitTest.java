package sitsiplaseeraus;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SitsitTest {
    private Sitsit sitsit;
    
    public SitsitTest() {
    }
    
    @Before
    public void setUp() {
        sitsit = new Sitsit(3);
    }
    
    @Test
    public void paikkojenMaaraOnOikea() {
        assertEquals(0, sitsit.sitsaajienMaara());
        assertEquals(0, sitsit.yhteyksienMaara());
        
        sitsit.addPaikka(1);
        
        assertEquals(1, sitsit.sitsaajienMaara());
        assertEquals(0, sitsit.yhteyksienMaara());
        
        sitsit.addPaikka(1);
        
        assertEquals(2, sitsit.sitsaajienMaara());
        assertEquals(0, sitsit.yhteyksienMaara());
        
        sitsit.addPaikka(0);
        
        assertEquals(3, sitsit.sitsaajienMaara());
        assertEquals(0, sitsit.yhteyksienMaara());
    }
    
    @Test
    public void getPaikkaToimii() {
        assertEquals(true, sitsit.getPaikat().isEmpty());
        
        sitsit.addPaikka(2);
        
        assertEquals(false, sitsit.getPaikat().isEmpty());
        assertEquals(1, sitsit.getPaikat().size());
    }
    
    @Test
    public void poytienMaaraOnOikea() {
        assertEquals(3, sitsit.poytienMaara());

        sitsit = new Sitsit(2);
        
        sitsit.addPaikka(0);
        
        assertEquals(2, sitsit.poytienMaara());
        assertEquals(0, sitsit.sitsaajienMaaraPoydassa(1));
        assertEquals(0, sitsit.palautaPoydanSitsaajat(0).size());
        assertEquals(true, sitsit.palautaPoydanSitsaajat(1).isEmpty());
        
        Sitsaaja erkki = new Sitsaaja("Joku Juttu", true);
        sitsit.getPaikka(0).setSitsaaja(erkki);
        
        assertEquals(1, sitsit.sitsaajienMaaraPoydassa(0));
        assertEquals(0, sitsit.sitsaajienMaaraPoydassa(1));
        assertEquals(false, sitsit.palautaPoydanSitsaajat(0).isEmpty());
        assertEquals(true, sitsit.palautaPoydanSitsaajat(1).isEmpty());

        assertEquals(1, sitsit.palautaPoydanSitsaajat(0).size());
        assertEquals("Joku Juttu", sitsit.palautaPoydanSitsaajat(0).get(0).getNimi());
    }
    
    @Test
    public void yhteyksienMaaraTasmaa() {
        assertEquals(0, sitsit.yhteyksienMaara());
        
        for (int i = 0; i < 10; i++) {
            sitsit.addPaikka();
        }
        sitsit.getPaikka(3).setSitsaaja(new Sitsaaja("Joku Tuttu", false));
        sitsit.getPaikka(6).setSitsaaja(new Sitsaaja("Toinen Tuttu", false));
        sitsit.getPaikka(1).setSitsaaja(new Sitsaaja("Kolmas Tuttu", false));
        
        assertEquals(0, sitsit.yhteyksienMaara());
        
        sitsit.getPaikka(3).getSitsaaja().setYhteys(sitsit.getPaikka(6).getSitsaaja(), -2);
        
        assertEquals(1, sitsit.yhteyksienMaara());
        
        sitsit.getPaikka(3).getSitsaaja().setYhteys(sitsit.getPaikka(1).getSitsaaja(), 1);
        
        assertEquals(2, sitsit.yhteyksienMaara());
        
        sitsit.getPaikka(6).getSitsaaja().setYhteys(sitsit.getPaikka(1).getSitsaaja(), 4);
        
        assertEquals(3, sitsit.yhteyksienMaara());
    }
}