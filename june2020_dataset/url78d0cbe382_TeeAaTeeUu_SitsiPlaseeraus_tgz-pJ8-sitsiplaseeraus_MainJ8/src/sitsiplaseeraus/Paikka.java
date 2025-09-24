package sitsiplaseeraus;

/**
 * Paikan kaikki tiedot, eli missä pöydässä kyseinen "tuoli" on, millä paikalla ja mitkä tuolit ovat sen vieressä.
 */
public class Paikka {

    private int paikka;
    private int poyta;
    private Sitsaaja sitsaaja;
    private Paikka naisenAvecinPaikka;
    private Paikka puolisonPaikka;
    private Paikka miehenAvecinPaikka;
    private boolean miehenPaikka;

    /**
     * Alustaa pöydän paikoilleen
     * @param poyta missä pöydässä
     * @param paikka millä paikalla
     */
    public Paikka(int poyta, int paikka) {
        this.setPoyta(poyta);
        this.setPaikka(paikka);
    }
    
    /**
     * Palauttaa millä paikalla
     * @return paikan luku
     */
    public int getPaikka() {
        return paikka;
    }

    private void setPaikka(int paikka) {
        this.paikka = paikka;
    }

    /**
     * Palauttaa monenessako pöydässä
     * @return pöydän numero
     */
    public int getPoyta() {
        return poyta;
    }

    private void setPoyta(int poyta) {
        this.poyta = poyta;
    }

    /**
     * Asettaa paikalle sitsaajan
     * @param sitsaaja
     */
    public void setSitsaaja(Sitsaaja sitsaaja) {
        this.sitsaaja = sitsaaja;
    }

    /**
     * Palauttaa paikan sitsaajan
     * @return Sitsaaja
     */
    public Sitsaaja getSitsaaja() {
        return sitsaaja;
    }

    /**
     * Palauttaa naisen avecin paikka-olion
     * @return
     */
    public Paikka getNaisenAvecinPaikka() {
        return naisenAvecinPaikka;
    }

    /**
     * Asettaa naisen avecin paikan (tarvitsee tehdä vain kerran, kun tuolit eivät vaihda paikkaa)
     * @param avecinPaikka
     */
    public void setNaisenAvecinPaikka(Paikka avecinPaikka) {
        this.naisenAvecinPaikka = avecinPaikka;
    }

    /**
     * Palauttaa puolison paikka-olion, eli vastapäätä olevan
     * @return
     */
    public Paikka getPuolisonPaikka() {
        return puolisonPaikka;
    }

    /**
     * Asettaa puolison paikan (tarvitsee tehdä vain kerran, kun tuolit eivät vaihda paikkaa)
     * @param puolisonPaikka
     */
    public void setPuolisonPaikka(Paikka puolisonPaikka) {
        this.puolisonPaikka = puolisonPaikka;
    }

    /**
     * Asettaa miehen avecin paikan (tarvitsee tehdä vain kerran, kun tuolit eivät vaihda paikkaa)
     * @param avecinPaikka
     */
    public void setMiehenAvecinPaikka(Paikka avecinPaikka) {
        this.miehenAvecinPaikka = avecinPaikka;
    }
    
    /**
     * Palauttaa miehen avecin paikka-olion
     * @return
     */
    public Paikka getMiehenAvecinPaikka() {
        return miehenAvecinPaikka;
    }

    public boolean isMiehenPaikka() {
        return miehenPaikka;
    }

    public void setMiehenPaikka(boolean miehenPaikka) {
        this.miehenPaikka = miehenPaikka;
    }
    
}