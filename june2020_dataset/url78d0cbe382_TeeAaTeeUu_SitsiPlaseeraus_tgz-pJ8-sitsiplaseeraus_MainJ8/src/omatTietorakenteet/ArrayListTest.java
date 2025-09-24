package omatTietorakenteet;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.random.Random;

public class ArrayListTest {

    public ArrayListTest() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void lisaaminenOnnistuu() {
        ArrayList<Integer> luvut = new ArrayList<Integer>();

        luvut.add(7);

        int luku = luvut.get(0);

        assertEquals(7, luku);
    }
    
    @Test
    public void lisaaminenOnnistuuStringeilla() {
        ArrayList<String> luvut = new ArrayList<String>();

        luvut.add("moi");

        String teksti = luvut.get(0);

        assertEquals("moi", teksti);
    }
    
    @Test
    public void lisaaminenOnnistuuSitsaajilla() {
        ArrayList<Sitsaaja> luvut = new ArrayList<Sitsaaja>();

        luvut.add(new Sitsaaja("Testi Nimi", true));


        assertEquals("Testi Nimi", luvut.get(0).getNimi());
    }

    @Test
    public void kokoOnOikea() {
        ArrayList<Integer> luvut = new ArrayList<Integer>();

        assertEquals(0, luvut.size());

        luvut.add(7);

        assertEquals(1, luvut.size());
    }

    @Test
    public void forEachToimii() {
        ArrayList<Integer> luvut = new ArrayList<Integer>();

        luvut.add(7);
        luvut.add(4);
        luvut.add(65);
        luvut.add(-8);
        
        System.out.println(luvut.get(0));
        System.out.println(luvut.get(1));
        System.out.println(luvut.get(2));
        System.out.println(luvut.get(3));

        int i = 0;
        int oikea = 0;
        for (int luku : luvut) {
            if (i == 0) {
                oikea = 7;
            }
            if (i == 1) {
                oikea = 4;
            }
            if (i == 2) {
                oikea = 65;
            }
            if (i == 3) {
                oikea = -8;
            }
            assertEquals(oikea, (int) luku);
            i++;
        }
        assertEquals(4, i);
    }
    
    @Test
    public void forEachToimiiTyhjana() {
        ArrayList<Integer> luvut = new ArrayList<Integer>();

        int i = 0;
        for (int luku : luvut) {
            i++;
        }
        assertEquals(i, 0);
        
        luvut.add(7);
        
        i = 0;
        for (int luku : luvut) {
            i++;
        }
        assertEquals(i, 1);
    }
    
    @Test
    public void suurenDataMaaranLisaaminenOnnistuu() {
        ArrayList<Integer> luvut = new ArrayList<Integer>();
        
        for (int i = 0; i < 20000; i++) {
            luvut.add(Random.luo(100000));
        }
        assertEquals(20000, luvut.size());
        assertTrue(luvut.get(Random.luo(20000)) > 0);
    }
    
    @Test
    public void loytaaJosSisaltaaJotain() {
        ArrayList<Integer> luvut = new ArrayList<Integer>();
        
        for (int i = 0; i < 20000; i++) {
            if(i == 16347) {
                luvut.add(234589675);
            }
            luvut.add(Random.luo(100000));
            
        }
        assertEquals(234589675, (int) luvut.get(16347));
        assertEquals(20001, luvut.size());
    }
}