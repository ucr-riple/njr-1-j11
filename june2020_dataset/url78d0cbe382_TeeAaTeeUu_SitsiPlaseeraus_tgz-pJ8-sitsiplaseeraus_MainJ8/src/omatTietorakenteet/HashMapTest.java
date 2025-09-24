package omatTietorakenteet;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.random.Random;

public class HashMapTest {
    
    public HashMapTest() {
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void tiedonTallentaminenToimii() {
        Hakemisto<String, Double> varasto = new Hakemisto<String, Double>();
        
        varasto.put("jotain soopaa", 14.646);
       
        
        System.out.println(varasto.get("jotain soopaa"));
        
        assertEquals(14.646, varasto.get("jotain soopaa"), 0.01);
    }
    
    @Test
    public void kokoOnOikea() {
        Hakemisto<String, Double> varasto = new Hakemisto<String, Double>();

        assertEquals(0, varasto.size());

        varasto.put("moikka", 0.564);

        assertEquals(1, varasto.size());
    }
    
    @Test
    public void forEachToimii() {
        Hakemisto<Integer, String> hakemisto = new Hakemisto<Integer, String> ();

        hakemisto.put(7, "moikka");
        hakemisto.put(4, "heippa");
        hakemisto.put(65, "maara");
        hakemisto.put(-8, "joku juttu");
        
        for (Vektori<Integer,String> vektori : hakemisto) {
            System.out.println(vektori.getKey() + " " + vektori.getValue());
        }

        int i = 0;
        String oikea = "";
        for (Vektori<Integer, String> vektori : hakemisto) {
            if (i == 0) {
                oikea = "moikka";
            }
            if (i == 1) {
                oikea = "heippa";
            }
            if (i == 2) {
                oikea = "maara";
            }
            if (i == 3) {
                oikea = "joku juttu";
            }
            assertEquals(oikea, vektori.getValue());
            i++;
        }
        assertEquals(4, i);
    }
    
    @Test
    public void forEachToimiiTyhjana() {
        Hakemisto<Sitsaaja, Integer> luvut = new Hakemisto<Sitsaaja, Integer>();

        int i = 0;
        for (Vektori<Sitsaaja, Integer> vektori : luvut) {
            i++;
        }
        assertEquals(i, 0);
        
        luvut.put(new Sitsaaja("joku Tyyppi", true), 36);
        assertEquals("joku Tyyppi", luvut.getAvain(36).getNimi());
        
        i = 0;
        for (Vektori<Sitsaaja, Integer> vektori : luvut) {
            i++;
        }
        assertEquals(i, 1);
    }
    
    @Test
    public void suurenDataMaaranLisaaminenOnnistuu() {
        Hakemisto<Integer, String> hakemisto = new Hakemisto<Integer, String> ();
        
        for (int i = 0; i < 2000; i++) {
            hakemisto.put(Random.luo(5000), "moo");
        }
        
        System.out.println(hakemisto.size());
        
        assertTrue(hakemisto.size() < 2000);
        int monesko = 0;
        do {
            monesko = Random.luo(5000);
        } while (hakemisto.containsKey(monesko) == false);
        
        assertTrue(hakemisto.get(monesko).equals("moo"));
    }

}