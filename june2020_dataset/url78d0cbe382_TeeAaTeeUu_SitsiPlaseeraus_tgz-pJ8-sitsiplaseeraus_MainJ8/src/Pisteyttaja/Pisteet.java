package Pisteyttaja;

import omatTietorakenteet.ArrayList;
import sitsiplaseeraus.Paikka;
import sitsiplaseeraus.Sitsit;

/**
 * Laskee ja palauttaa koko sitsit pistemäärän, jota sitten verrataan muihin
 * järjestyksiin.
 */
public class Pisteet {

    private Sitsit sitsit;
    private ArrayList<PaikanPisteet> paikat;
    private double pisteet;
    private boolean onYhteyksia;
    private ArrayList<Paikka> paikkaLista;
    private int avec;
    private int puoliso;
    private Laskin laskin;
    private double pariPisteet;
    private double sukupuoliPisteet;
    private double yhteysPisteet;

    /**
     * Luo ja alustaa olion.
     *
     * @param sitsit
     */
    public Pisteet(Sitsit sitsit) {
        this.sitsit = sitsit;
        this.paikat = new ArrayList<PaikanPisteet>();
        this.laskin = new Laskin();

        this.alustaPaikat();

        this.onYhteyksia = false;
    }

    public double palautaPisteet() {
        nollaaPisteet();

        this.alustaSitsaajat();

        kayLapiKaikkiPaikat(false);

        return pisteet;
    }

    public double palautaSukupuoliJaPariPisteet() {
        nollaaPisteet();

        this.alustaSitsaajat();

        kayLapiKaikkiPaikat(true);

        return pisteet;
    }

    /**
     * Palauttaa mahdolliset avec ja puolisopisteet.
     *
     * @return avec-pisteet
     */
    public double getPariPisteet() {
        return pariPisteet;
    }

    /**
     * Palauttaa "tyttöpoika-järjestyksen" pisteet.
     *
     * @return
     */
    public double getSukupuoliPisteet() {
        return sukupuoliPisteet;
    }

    /**
     * Palauttaa yhteyksien pisteet, eli kuinka hyvin järjestetty tykkäysten ja
     * pienien riitojen maksimoimiseksi.
     *
     * @return yhteyspisteet.
     */
    public double getYhteysPisteet() {
        return yhteysPisteet;
    }

    /**
     * Onko paikan sitsaajalla yhteyksiä muihin sitsaajiin.
     *
     * @return
     */
    protected boolean onkoYhteyksia() {
        return this.onYhteyksia;
    }

    private void alustaPaikat() {
        this.paikkaLista = this.sitsit.getPaikat();

        for (Paikka paikka : this.paikkaLista) {
            PaikanPisteet paikanPisteet = new PaikanPisteet(paikka, this.laskin);
            this.paikat.add(paikanPisteet);
        }
    }

    /**
     * Asettaa sitsaajan tietämään, millä paikalla hän istuu.
     */
    public void alustaSitsaajat() {
        for (Paikka paikka : this.paikkaLista) {
            paikka.getSitsaaja().setPaikka(paikka);
        }

    }

    /**
     * Palauttaa vierekkäin olevien avecien määrän.
     *
     * @return vierekkäin olevien avecien määrä.
     */
    public int getAvecienMaara() {
        return avec;
    }

    /**
     * Palauttaa vierekkäin olevien puolisojen määrän.
     *
     * @return vierekkäin olevien puolisojen määrä.
     */
    public int getPuolisojenMaara() {
        return puoliso;
    }

    private void kayLapiKaikkiPaikat(boolean vainSukupuoliJaPariPisteet) {
        for (PaikanPisteet sitsaajanPisteet : paikat) {
            if (vainSukupuoliJaPariPisteet == false) {
                this.pisteet += sitsaajanPisteet.palautaPisteet();
            } else {
                sitsaajanPisteet.nollaaPisteet();
                this.pisteet += sitsaajanPisteet.tarkistaAvecJaPuoliso();
                this.pisteet += sitsaajanPisteet.tarkistaYmparillaOlevienSukupuolet();
            }

            if (sitsaajanPisteet.isAvec()) {
                this.avec++;
            }
            if (sitsaajanPisteet.isPuoliso()) {
                this.puoliso++;
            }
            this.onYhteyksia = true;

            asetaOsaPisteetOikein(sitsaajanPisteet);
        }
    }

    private void asetaOsaPisteetOikein(PaikanPisteet sitsaajanPisteet) {
        pariPisteet += sitsaajanPisteet.getPariPisteet();
        sukupuoliPisteet += sitsaajanPisteet.getSukupuoliPisteet();
        yhteysPisteet += sitsaajanPisteet.getYhteysPisteet();
    }

    private void nollaaPisteet() {
        this.pisteet = 0.0;
        this.avec = 0;
        this.puoliso = 0;

        pariPisteet = 0.0;
        sukupuoliPisteet = 0.0;
        yhteysPisteet = 0.0;
    }
}
