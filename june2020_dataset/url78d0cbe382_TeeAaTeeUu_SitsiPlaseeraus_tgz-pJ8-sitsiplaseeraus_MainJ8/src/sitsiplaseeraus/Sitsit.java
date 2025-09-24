package sitsiplaseeraus;

import Pisteyttaja.PaikanPisteet;
import omatTietorakenteet.ArrayList;
import omatTietorakenteet.Hakemisto;
import sitsiplaseeraus.random.Random;

/**
 * Hoitaa sitsien ylläpitämistä, pitää kirjaa kaikista paikoista ja pöytienmäärästä. Loput tiedot hakee paikoilta ja niiden sitsaajilta, eli toimii myös käyttöliittymänä
 */
public class Sitsit {

    private int poytienMaara;
    private final ArrayList<Paikka> paikat;
    private final Hakemisto<Paikka, Sitsaaja> sitsaajienPaikat;

    /**
     * Alustaa sitsit pystyyn
     * @param kuinkaMontaPoytaa
     */
    public Sitsit(int kuinkaMontaPoytaa) {
        this.setPoytienMaara(kuinkaMontaPoytaa);

        this.paikat = new ArrayList<Paikka>();

        this.sitsaajienPaikat = new Hakemisto<Paikka, Sitsaaja>();
    }

    /**
     * palauttaa paikat
     * @return lista paikka-olioista
     */
    public ArrayList<Paikka> getPaikat() {
        return paikat;
    }

    private void setPoytienMaara(int kuinkaMontaPoytaa) {
        if (kuinkaMontaPoytaa <= 0) {
            this.poytienMaara = 1;
        } else {
            this.poytienMaara = kuinkaMontaPoytaa;
        }
    }

    /**
     * Sitsaajien määrä
     * @return määrä
     */
    public int sitsaajienMaara() {
        return this.paikat.size();
    }

    /**
     * lisää paikan satunnaiseen pöydään
     * @return
     */
    public Paikka addPaikka() {
        return addPaikka(Random.luo(this.poytienMaara() - 1));
    }

    /**
     * Lisää paikan haluttuun pöytään.
     * @param mikaPoyta
     * @return palauttaa luodun paikka-olion
     */
    public Paikka addPaikka(int mikaPoyta) {
        Paikka paikka = new Paikka(mikaPoyta, this.sitsaajienMaaraPoydassa(mikaPoyta));

        this.paikat.add(paikka);

        return paikka;
    }

    /**
     * Palauttaa paikan
     * @param monesko
     * @return halutun paikan
     */
    public Paikka getPaikka(int monesko) {
        return this.paikat.get(monesko);
    }

    /**
     * Palauttaa kaikki sitsaajat
     * @return lista sitsaajista
     */
    public ArrayList<Sitsaaja> getSitsaajat() {
        ArrayList<Sitsaaja> sitsaajat = new ArrayList<Sitsaaja>();

        for (Paikka paikka : this.paikat) {
            sitsaajat.add(paikka.getSitsaaja());
        }
        return sitsaajat;
    }

    /**
     * Paljonko sitseillä on yhteyksiä
     * @return yhteyksien määrä
     */
    public int yhteyksienMaara() {
        int yhteyksienMaara = 0;

        for (Paikka paikka : paikat) {
            if (paikka.getSitsaaja() != null) {
                yhteyksienMaara += paikka.getSitsaaja().yhteyksienMaara();
            }
        }
        return yhteyksienMaara;
    }

    /**
     * Palauttaa pöytien määrän
     * @return pöytien määrä
     */
    public int poytienMaara() {
        return this.poytienMaara;
    }

    /**
     * Palauttaa jonkin tietyn pöydän sitsaajat oikeassa järjestyksessä
     * @param moneskoPoyta
     * @return listan pöydän sitsaajista
     */
    public ArrayList<Sitsaaja> palautaPoydanSitsaajat(int moneskoPoyta) {
        Hakemisto<Integer, Sitsaaja> poydanSitsaajat = this.palautaPoydanSitsaajatLoop(moneskoPoyta);
        return palautaSitsaajatJarjestyksessa(poydanSitsaajat);
    }
    
    /**
     * Palauttaa tietyn pöydän kaikki paikka-oliot
     * @param moneskoPoyta
     * @return lista paikka-olioista
     */
    public Hakemisto<Integer, Paikka> palautaPoydanPaikat(int moneskoPoyta) {
        Hakemisto<Integer, Paikka> poydanPaikat = new Hakemisto<Integer, Paikka>();

        for (Paikka paikka : this.paikat) {
            if (paikka.getPoyta() == moneskoPoyta) {
                if (paikka.getSitsaaja() != null) {
                    poydanPaikat.put(paikka.getPaikka(), paikka);
                }
            }
        }
        return poydanPaikat;
    }

    /**
     * Kuinka monta sitsaajaa kysytyssä pöydässä on
     * @param mikaPoyta
     * @return sitsaajien määrä pöydässä
     */
    protected int sitsaajienMaaraPoydassa(int mikaPoyta) {
        int maara = 0;

        for (Paikka sitsaaja : paikat) {
            if (sitsaaja.getPoyta() == mikaPoyta) {
                maara++;
            }
        }
        return maara;
    }

    /**
     * Palauttaa kaikkien sitsaajien yhteydet
     * @return kaikki yhteydet mukavassa sisäkkäisessä HashMapissa
     */
    public Hakemisto<Sitsaaja, Hakemisto> palautaYhteydet() {
        Hakemisto<Sitsaaja, Hakemisto> kaikkiYhteydet = new Hakemisto<Sitsaaja, Hakemisto>();

        for (Paikka paikka : paikat) {
            kaikkiYhteydet.put(paikka.getSitsaaja(), paikka.getSitsaaja().palautaYhteydet());
        }
        return kaikkiYhteydet;
    }

    /**
     * Palauttaa lsitan paikoista ja niissä olevista sitsaajista
     * @return listan paikoista ja sitsaajista
     */
    public Hakemisto<Paikka, Sitsaaja> palautaPaikkaSitsaajaParit() {
        this.sitsaajienPaikat.clear();

        for (Paikka paikka : paikat) {
            this.sitsaajienPaikat.put(paikka, paikka.getSitsaaja());
        }
        return this.sitsaajienPaikat;
    }

    /**
     * Kerrotaan paikoille eli tuoleille, mikä tuoli vieressäsi on. Tarvitsee tehdä vain kerran.
     */
    public void lisaaPaikoilleTiedotAvecinJaPuolisonPaikoista() {
        for (Paikka paikka : paikat) {
            for (Paikka kohdePaikka : paikat) {
                if (paikka.getPoyta() == kohdePaikka.getPoyta()) {
                    kokeillaanKoskaOllaanSamassaPoydassa(paikka, kohdePaikka);
                }
            }
        }
    }
    
    private void kokeillaanKoskaOllaanSamassaPoydassa(Paikka paikka, Paikka kohdePaikka) {
        if (paikka.getPaikka() % 2 == 0) {
            lisaaParinPaikkaKunOllaanVasemmallaPuolella(paikka, kohdePaikka);
        } else {
            lisaaParinPaikkaKunOllaanOikealla(paikka, kohdePaikka);
        }
        if (Math.abs(paikka.getPaikka() - kohdePaikka.getPaikka()) == 1) {
            kokeillaanVastapaataOlevanAsettamista(paikka, kohdePaikka);
        }
    }

    private void lisaaParinPaikkaKunOllaanVasemmallaPuolella(Paikka paikka, Paikka kohdePaikka) {
        if (paikka.getPaikka() - kohdePaikka.getPaikka() == -2) {
            paikka.setNaisenAvecinPaikka(kohdePaikka);
        } else if (paikka.getPaikka() - kohdePaikka.getPaikka() == 2) {
            paikka.setMiehenAvecinPaikka(kohdePaikka);
        }
    }

    private void lisaaParinPaikkaKunOllaanOikealla(Paikka paikka, Paikka kohdePaikka) {
        if (paikka.getPaikka() - kohdePaikka.getPaikka() == 2) {
            paikka.setNaisenAvecinPaikka(kohdePaikka);
        } else if (paikka.getPaikka() - kohdePaikka.getPaikka() == -2) {
            paikka.setMiehenAvecinPaikka(kohdePaikka);
        }
    }

    private void kokeillaanVastapaataOlevanAsettamista(Paikka paikka, Paikka kohdePaikka) {
        if (PaikanPisteet.paikkaOnVasemmalla(paikka.getPaikka()) == true && (paikka.getPaikka() - kohdePaikka.getPaikka()) == -1) {
            paikka.setPuolisonPaikka(kohdePaikka);
        } else if (PaikanPisteet.paikkaOnVasemmalla(paikka.getPaikka()) == false && (paikka.getPaikka() - kohdePaikka.getPaikka()) == 1) {
            paikka.setPuolisonPaikka(kohdePaikka);
        }
    }

    private Hakemisto<Integer, Sitsaaja> palautaPoydanSitsaajatLoop(int moneskoPoyta) {
         Hakemisto<Integer, Sitsaaja> poydanSitsaajat = new Hakemisto<Integer, Sitsaaja>();
         
        for (Paikka paikka : this.paikat) {
            if (paikka.getPoyta() == moneskoPoyta) {
                if (paikka.getSitsaaja() != null) {
                    poydanSitsaajat.put(paikka.getPaikka(), paikka.getSitsaaja());
                }
            }
        }
        return poydanSitsaajat;
    }

    private ArrayList<Sitsaaja> palautaSitsaajatJarjestyksessa(Hakemisto<Integer, Sitsaaja> poydanSitsaajat) {
        ArrayList<Sitsaaja> poydanSitsaajatJarjestyksessa = new ArrayList<Sitsaaja>();
        
        for (int i = 0; i < poydanSitsaajat.size(); i++) {
            poydanSitsaajatJarjestyksessa.add(poydanSitsaajat.get(i));
        }
        return poydanSitsaajatJarjestyksessa;
    }
}
