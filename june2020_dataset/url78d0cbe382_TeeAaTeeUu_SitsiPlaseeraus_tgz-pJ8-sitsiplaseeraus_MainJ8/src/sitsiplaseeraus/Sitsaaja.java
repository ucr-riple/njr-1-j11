package sitsiplaseeraus;

import omatTietorakenteet.Hakemisto;
import omatTietorakenteet.Vektori;

/**
 * Sisältää kaiken mitä sitsaajan tarvitsee tietää itsestään ja
 * mieltymyksistään. Myös paikan johon hänet on laitettu, jos tämä on hänelle
 * kerrottu :)
 */
public class Sitsaaja {

    private String nimi;
    private int mies;
    private Hakemisto<Sitsaaja, Integer> yhteydet;
    private Sitsaaja avec;
    private boolean avecIsSet;
    private boolean puolisoIsSet;
    private Sitsaaja puoliso;
    private Paikka paikka;

    /**
     * Luo sitsaajan annetulla nimellä
     *
     * @param nimi
     */
    public Sitsaaja(String nimi) {
        this.nimi = nimi;

        this.yhteydet = new Hakemisto<Sitsaaja, Integer>();

        this.avecIsSet = false;
        this.puolisoIsSet = false;
        this.mies = -1;
    }

    /**
     * Luo sitsaajan annetulla nimellä ja sukupuolella
     *
     * @param nimi nimi
     * @param mies Jos true, niin mies, jos false niin nainen
     */
    public Sitsaaja(String nimi, boolean mies) {
        this(nimi);
        this.setMies(mies);
    }

    /**
     * Palauttaa nimen
     *
     * @return nimi
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Onko mies vai nainen
     *
     * @return true jos mies, false jos nainen
     */
    public boolean isMies() {
        if (this.mies == 1) {
            return true;
        } else if (this.mies == 0) {
            return false;
        } else {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates. 
        }
    }

    private void setMies(boolean mies) {
        if (mies == true) {
            this.mies = 1;
        } else {
            this.mies = 0;
        }
    }

    /**
     * Asettaa sitsaajalle avecin, voi olla hetero tai homopari
     *
     * @param avec Sitsaaja
     */
    public void setAvec(Sitsaaja avec) {
        this.avec = avec;
        this.setAvecIsSet();
    }

    /**
     * Palauttaa avecin
     *
     * @return Sitsaaja
     */
    public Sitsaaja getAvec() {
        return avec;
    }

    /**
     * Palauttaa puolison
     *
     * @return Sitsaaja
     */
    public Sitsaaja getPuoliso() {
        return puoliso;
    }

    /**
     * Asettaa sitsaajalle puolison, voi olla hetero- tai homopari
     *
     * @param puoliso
     */
    public void setPuoliso(Sitsaaja puoliso) {
        this.puoliso = puoliso;
        this.setPuolisoIsSet();
    }

    /**
     * palauttaa paikan jossa istuu, jos kerrottu
     *
     * @return Paikka luokka
     */
    public Paikka getPaikka() {
        return paikka;
    }

    /**
     * Palauttaa paikan, jossa luulee istuvansa
     *
     * @param paikka Paikka luokka
     */
    public void setPaikka(Paikka paikka) {
        this.paikka = paikka;
    }

    /**
     * onko sitsaajalla avecia
     *
     * @return true tai false
     */
    public boolean avecIsSet() {
        return avecIsSet;
    }

    private void setAvecIsSet() {
        this.avecIsSet = true;
    }

    /**
     * onko sitsaajalla puolisoa
     *
     * @return true tai false
     */
    public boolean puolisoIsSet() {
        return puolisoIsSet;
    }

    private void setPuolisoIsSet() {
        this.puolisoIsSet = true;
    }

    /**
     * Asettaa yhteyden sitsaajalle, eli kenestä toisesta sitsaajasta pitää tai
     * vihaa
     *
     * @param sitsaaja
     * @param arvo kuinka paljon vihaa tai rakastaa, väliltä -5 ja 5
     * @return jos arvo oli huono niin false, muuten true
     */
    public boolean setYhteys(Sitsaaja sitsaaja, int arvo) {
        if (this.equals(sitsaaja) || arvo > 5 || arvo < -5 || this.yhteydet.containsKey(sitsaaja)) {
            return false;
        }
        this.yhteydet.put(sitsaaja, arvo);
        return true;
    }

    /**
     * Poistaa yhteyden sitsaajalta, jos sellainen on olemassa
     *
     * @param sitsaaja
     * @return onnistuiko vai ei
     */
    public boolean deleteYhteys(Sitsaaja sitsaaja) {
        if (this.yhteydet.containsKey(sitsaaja)) {
            this.yhteydet.remove(sitsaaja);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Palauttaa sitsaajan kaikki yhteydet
     *
     * @return yhteydet
     */
    public Hakemisto<Sitsaaja, Integer> palautaYhteydet() {
        return this.yhteydet;
    }

    /**
     * Kuinka paljon sitsaajalla on yhteyksiä muihin sitsaajiin
     *
     * @return määrä
     */
    public int yhteyksienMaara() {
        int yhteyksienMaara = 0;
        for (Vektori<Sitsaaja, Integer> yhteys : this.yhteydet) {
            yhteyksienMaara++;
        }
        return yhteyksienMaara;
    }

    public boolean heteroPari() {
        if (avecIsSet == false && puolisoIsSet == false) {
            return false;
        }
        if (avecIsSet) {
            if (isMies()) {
                if (getAvec().isMies()) {
                    return false;
                } else {
                    return true;
                }
            } else {
                if (getAvec().isMies()) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if (isMies()) {
                if (getAvec().isMies()) {
                    return false;
                } else {
                    return true;
                }
            } else {
                if (getAvec().isMies()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
}
