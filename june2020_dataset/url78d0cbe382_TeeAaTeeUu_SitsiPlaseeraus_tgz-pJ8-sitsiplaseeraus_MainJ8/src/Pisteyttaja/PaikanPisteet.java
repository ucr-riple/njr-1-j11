package Pisteyttaja;

import omatTietorakenteet.Hakemisto;
import omatTietorakenteet.Vektori;
import sitsiplaseeraus.Paikka;
import sitsiplaseeraus.Sitsaaja;

/**
 * Laskee ja palauttaa paikan pisteet, jossa sitsaajat istuvat.
 */
public class PaikanPisteet {

    private Paikka paikka;
    private Hakemisto<Sitsaaja, Integer> yhteydet;
    private Paikka kohdePaikka;
    private int kohteidenErotus;
    private boolean onYhteyksia;
    private int arvo;
    private Sitsaaja kohdeSitsaaja;
    private final Laskin laskin;
    private boolean avec;
    private boolean puoliso;
    private double pariPisteet;
    private double sukupuoliPisteet;
    private double yhteysPisteet;

    /**
     * Alustaa pisteiden laskurin käyttöön.
     *
     * @param paikka
     * @param laskin
     */
    protected PaikanPisteet(Paikka paikka, Laskin laskin) {
        this.paikka = paikka;
        this.onYhteyksia = false;
        this.laskin = laskin;
    }

    /**
     * Palauttaa kaikki pisteet.
     *
     * @return Paikan pisteet.
     */
    protected double palautaPisteet() {
        this.yhteydet = this.paikka.getSitsaaja().palautaYhteydet();

        alustaLuvut();

        pariPisteet = this.tarkistaAvecJaPuoliso();
        sukupuoliPisteet = this.tarkistaYmparillaOlevienSukupuolet();

        yhteysPisteet = kayKaikkiYhteydetLapi();

        return pariPisteet + sukupuoliPisteet + yhteysPisteet;
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

    private double palautaPisteet(int arvo, int kohteidenErotus, int paikka) {
        if (paikkaOnVasemmalla(paikka) == true) {
            return pisteetKunPaikkaOnVasemmalla(kohteidenErotus, arvo);
        } else {
            return pisteetKunPaikkaOnOikealla(kohteidenErotus, arvo);
        }

    }

    /**
     * Palauttaa true, jos annettu paikka on vasemmalla puolella pöytää.
     *
     * @param paikka
     * @return true, jos vasemmalla puolella.
     */
    public static boolean paikkaOnVasemmalla(int paikka) {
        return paikka % 2 == 0;
    }

    private static boolean kohdeOnSamallaPuolella(int kohteidenErotus) {
        return Math.abs(kohteidenErotus) % 2 == 0;
    }

    private double pisteetKunPaikkaOnVasemmalla(int kohteidenErotus, int arvo) {
        if (kohdeOnSamallaPuolella(kohteidenErotus) == true) {
            return 1.0 * arvo / Math.abs(kohteidenErotus / 2);
        } else {
            if (kohteidenErotus > 0) {
                return 1.0 * arvo / this.laskin.hypot(1, 1.0 * (kohteidenErotus + 1) / 2);
            } else {
                return 1.0 * arvo / this.laskin.hypot(1, 1.0 * (Math.abs(kohteidenErotus) - 1) / 2);
            }
        }
    }

    private double pisteetKunPaikkaOnOikealla(int kohteidenErotus, int arvo) {
        if (kohdeOnSamallaPuolella(kohteidenErotus) == true) {
            return 1.0 * arvo / Math.abs(kohteidenErotus / 2);
        } else {
            if (kohteidenErotus > 0) {
                return 1.0 * arvo / this.laskin.hypot(1, 1.0 * (kohteidenErotus - 1) / 2);
            } else {
                return 1.0 * arvo / this.laskin.hypot(1, 1.0 * Math.abs((kohteidenErotus) / 2 - 1));
            }
        }
    }

    public double tarkistaAvecJaPuoliso() {       
        pariPisteet = this.tarkistaAvec(this.paikka);
        if (pariPisteet == 0.0) {
            pariPisteet = this.tarkistaPuoliso(this.paikka);
            if (pariPisteet > 0.0) {
                setPuoliso();
            }
            return pariPisteet;
        } else {
            if (pariPisteet > 0.0) {
                setAvec();
            }
            return pariPisteet;
        }
    }

    /**
     * Tarkistaa onko paikan sitsaajalla avec tai puoliso.
     *
     * @param paikka
     * @return true, jos avec tai puoliso.
     */
    public static double tarkistaAvecJaPuoliso(Paikka paikka) {
        double pisteet = tarkistaAvec(paikka);
        if (pisteet == 0.0) {
            pisteet = tarkistaPuoliso(paikka);
            return pisteet;
        } else {
            return pisteet;
        }
    }

    private static double tarkistaAvec(Paikka paikka) {
        if (paikka != null) {
            if (paikka.getSitsaaja().avecIsSet() == false) {
                return 0.0;
            } else {
                int mikaPari = onkoMillainenPari(paikka.getSitsaaja());
                if (mikaPari == 1) {
                    if (josSitsaajaOnMiesJaAvecNainen(paikka)) {
                        return 10000.1;
                    }
                } else if (mikaPari == -1) {
                    if (josSitsaajaOnNainenJaAvecMies(paikka)) {
                        return 10000.2;
                    }
                } else if (mikaPari == 0) {
                    if (josSitsaajaOnNainenJaAvecMies(paikka)) {
                        return 10000.3;
                    } else if (josSitsaajaOnMiesJaAvecNainen(paikka)) {
                        return 10000.3;
                    }
                }
            }
        }

        return 0.0;
    }

    private static double tarkistaPuoliso(Paikka paikka) {
        if (paikka != null) {
            if (paikka.getSitsaaja().puolisoIsSet() == false) {
                return 0.0;
            } else {
                if (paikka.getPuolisonPaikka() != null) {
                    if (paikka.getSitsaaja().getPuoliso().equals(paikka.getPuolisonPaikka().getSitsaaja())) {

                        return 10000.0;
                    }
                }
            }
        }
        return 0.0;
    }

    /**
     * Onko paikan sitsaajalla avec.
     *
     * @return true, jos avec
     */
    public boolean isAvec() {
        return avec;
    }

    private void setAvec() {
        this.avec = true;
    }

    /**
     * Palauttaa tiedon siitä, onko paikan sitsaajalla puolisoa.
     *
     * @return true, jos puoliso.
     */
    public boolean isPuoliso() {
        return puoliso;
    }

    private void setPuoliso() {
        this.puoliso = true;
    }

    private static int onkoMillainenPari(Sitsaaja sitsaaja) {
        if (sitsaaja.avecIsSet()) {
            if (sitsaaja.isMies() && sitsaaja.getAvec().isMies() == false) {
                return 1;
            } else if (sitsaaja.isMies() == false && sitsaaja.getAvec().isMies()) {
                return -1;
            } else {
                return 0;
            }
        }
        return -2;
    }

    public double tarkistaYmparillaOlevienSukupuolet() {
        int pisteita = 0;
        boolean mies;

        mies = onkoMiesVaiNainen();

        pisteita += miehenAvecinPaikallaToistaSukupuolta(mies);
        pisteita += naisenAvecinPaikallaToistaSukupuolta(mies);
        pisteita += puolisonPaikallaToistaSukupuolta(mies);
        
        this.sukupuoliPisteet = pisteita;

        return pisteita;
    }

    private double kayKaikkiYhteydetLapi() {
        for (Vektori<Sitsaaja, Integer> yhteys : yhteydet) {
            this.onYhteyksia = true;

            this.kohdeSitsaaja = (Sitsaaja) yhteys.getKey();
            this.kohdePaikka = this.kohdeSitsaaja.getPaikka();

            yhteysPisteet += josPoytaOnSama(yhteys);
        }
        return yhteysPisteet;
    }

    private void alustaLuvut() {
        this.avec = false;
        this.puoliso = false;

        pariPisteet = 0.0;
        sukupuoliPisteet = 0.0;
        yhteysPisteet = 0.0;
    }

    private static boolean josSitsaajaOnMiesJaAvecNainen(Paikka paikka) {
        if (paikka.getMiehenAvecinPaikka() != null) {
            if (paikka.getSitsaaja().getAvec().equals(paikka.getMiehenAvecinPaikka().getSitsaaja())) {
                return true;
            }
        }
        return false;
    }

    private static boolean josSitsaajaOnNainenJaAvecMies(Paikka paikka) {
        if (paikka.getNaisenAvecinPaikka() != null) {
            if (paikka.getSitsaaja().getAvec().equals(paikka.getNaisenAvecinPaikka().getSitsaaja())) {
                return true;
            }
        }
        return false;
    }

    private boolean onkoMiesVaiNainen() {
        boolean mies = false;
        try {
            if (paikka.getSitsaaja().isMies()) {
                mies = true;
            } else {
                mies = false;
            }
        } catch (UnsupportedOperationException e) {
        }
        return mies;
    }

    private int miehenAvecinPaikallaToistaSukupuolta(boolean mies) {
        int pisteita = 0;
        try {
            if (paikka.getMiehenAvecinPaikka() != null) {
                if (paikka.getMiehenAvecinPaikka().getSitsaaja().isMies()) {
                    if (mies == false) {
                        pisteita += 500;
                    }
                } else {
                    if (mies == true) {
                        pisteita += 500;
                    }
                }
            }
        } catch (UnsupportedOperationException e) {
        }
        return pisteita;
    }

    private int naisenAvecinPaikallaToistaSukupuolta(boolean mies) {
        int pisteita = 0;
        try {
            if (paikka.getNaisenAvecinPaikka() != null) {
                if (paikka.getNaisenAvecinPaikka().getSitsaaja().isMies()) {
                    if (mies == false) {
                        pisteita += 500;
                    }
                } else {
                    if (mies == true) {
                        pisteita += 500;
                    }
                }
            }
        } catch (UnsupportedOperationException e) {
        }
        return pisteita;
    }

    private int puolisonPaikallaToistaSukupuolta(boolean mies) {
        int pisteita = 0;
        try {
            if (paikka.getPuolisonPaikka() != null) {
                if (paikka.getPuolisonPaikka().getSitsaaja().isMies()) {
                    if (mies == false) {
                        pisteita += 500;
                    }
                } else {
                    if (mies == true) {
                        pisteita += 500;
                    }
                }
            }
        } catch (UnsupportedOperationException e) {
        }
        return pisteita;
    }

    private double josPoytaOnSama(Vektori<Sitsaaja, Integer> yhteys) {
        if (this.paikka.getPoyta() == this.kohdePaikka.getPoyta()) {
            this.arvo = (Integer) yhteys.getValue();

            this.kohteidenErotus = this.paikka.getPaikka() - this.kohdePaikka.getPaikka();

            return this.palautaPisteet(arvo, kohteidenErotus, this.paikka.getPaikka());
        }
        return 0.0;
    }

    void nollaaPisteet() {
        alustaLuvut();
    }
}
