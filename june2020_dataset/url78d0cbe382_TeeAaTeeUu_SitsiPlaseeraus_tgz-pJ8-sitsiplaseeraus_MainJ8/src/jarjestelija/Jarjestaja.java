package jarjestelija;

//import Pisteyttaja.PaikanPisteet;
import sitsiplaseeraus.Paikka;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.Sitsit;
import sitsiplaseeraus.random.Random;

/**
 * Vaihtaa paikkojen sitsaajien päittäin.
 */
public class Jarjestaja {

    private Sitsit sitsit;
    private Paikka tokaPaikka;
    private Paikka tokaKohdePaikka;
    private Paikka ekaPaikka;
    private Paikka ekaKohdePaikka;
    private Sitsaaja ekaSitsaaja;
    private Sitsaaja ekaKohdeSitsaaja;
    private Sitsaaja tokaSitsaaja;
    private Sitsaaja tokaKohdeSitsaaja;

    /**
     * Alustaa olion käyttöön.
     * @param sitsit
     */
    public Jarjestaja(Sitsit sitsit) {
        this.sitsit = sitsit;
    }

    /**
     * Vaihtaa kahden satunnaisen paikan sitsaajien paikkoja keskenään.
     * @return onnistuiko
     */
    protected boolean vaihdaRandom() {
        ekaKohdePaikka = null;
        ekaPaikka = sitsit.getPaikka(Random.luo(sitsit.sitsaajienMaara() - 1));

//        double pisteet = PaikanPisteet.tarkistaAvecJaPuoliso(ekaPaikka);
//        if (pisteet > 0) {
//            if (pisteet == 10000.1) {
//                return this.vaihdaParinPaikka(1);
//            } else if (pisteet == 10000.2) {
//                return this.vaihdaParinPaikka(2);
//            } else if (pisteet == 10000.3) {
//                return this.vaihdaParinPaikka(3);
//            }
//            return false;
//        } else {
            do {
                ekaKohdePaikka = sitsit.getPaikka(Random.luo(sitsit.sitsaajienMaara() - 1));
            } while (ekaPaikka.equals(ekaKohdePaikka)
//                    || PaikanPisteet.tarkistaAvecJaPuoliso(ekaKohdePaikka) > 0 || ekaPaikka.isMiehenPaikka() != ekaKohdePaikka.isMiehenPaikka()
                    );

            return this.vaihdaPaikat();
//        }
    }

    private boolean vaihdaPaikat() {
        this.ekaSitsaaja = ekaPaikka.getSitsaaja();
        this.ekaKohdeSitsaaja = ekaKohdePaikka.getSitsaaja();

        ekaPaikka.setSitsaaja(ekaKohdeSitsaaja);
        ekaKohdePaikka.setSitsaaja(ekaSitsaaja);

        return true;
    }

    /**
     * Vaihtaa kahden ennaltamäärätyn paikkoja keskenään.
     * @param ekaPaikka
     * @param ekaKohdePaikka
     * @return true, jos onnistui.
     */
    protected boolean vaihdaPaikat(Paikka ekaPaikka, Paikka ekaKohdePaikka) {
        this.ekaPaikka = ekaPaikka;
        this.ekaKohdePaikka = ekaKohdePaikka;

        return vaihdaPaikat();
    }

    private boolean vaihdaParinPaikka(int millainenPari) {
        tokaPaikka = null;
        this.ekaSitsaaja = ekaPaikka.getSitsaaja();

        if (millainenPari == 1) {
            tokaPaikka = ekaPaikka.getMiehenAvecinPaikka();
        } else if (millainenPari == 2) {
            tokaPaikka = ekaPaikka.getNaisenAvecinPaikka();
        } else if (millainenPari == 3) {
            tokaPaikka = ekaPaikka.getPuolisonPaikka();
        }
        this.tokaSitsaaja = tokaPaikka.getSitsaaja();

        do {
            ekaKohdePaikka = sitsit.getPaikka(Random.luo(sitsit.sitsaajienMaara() - 1));
            tokaKohdePaikka = getTokaKohdePaikka(millainenPari);
        } while (tokaKohdePaikka == null || ekaPaikka.equals(ekaKohdePaikka) || tokaPaikka.equals(ekaKohdePaikka) || ekaPaikka.equals(tokaKohdePaikka) || tokaPaikka.equals(tokaKohdePaikka)
//                || PaikanPisteet.tarkistaAvecJaPuoliso(ekaKohdePaikka) > 0
//                || PaikanPisteet.tarkistaAvecJaPuoliso(tokaKohdePaikka) > 0 || ekaPaikka.isMiehenPaikka() != ekaKohdePaikka.isMiehenPaikka()
                );

        this.ekaKohdeSitsaaja = this.ekaKohdePaikka.getSitsaaja();
        this.tokaKohdeSitsaaja = this.tokaKohdePaikka.getSitsaaja();

        this.ekaPaikka.setSitsaaja(ekaKohdeSitsaaja);
        this.tokaPaikka.setSitsaaja(tokaKohdeSitsaaja);

        this.ekaKohdePaikka.setSitsaaja(ekaSitsaaja);
        this.tokaKohdePaikka.setSitsaaja(tokaSitsaaja);

        return true;
    }

    private Paikka getTokaKohdePaikka(int millainenPari) {
        if (millainenPari == 1) {
            if (this.ekaKohdePaikka.getMiehenAvecinPaikka() != null);
            return this.ekaKohdePaikka.getMiehenAvecinPaikka();
        } else if (millainenPari == 2) {
            if (this.ekaKohdePaikka.getNaisenAvecinPaikka() != null);
            return this.ekaKohdePaikka.getNaisenAvecinPaikka();
        } else if (millainenPari == 3) {
            if (this.ekaKohdePaikka.getPuolisonPaikka() != null) {
                return this.ekaKohdePaikka.getPuolisonPaikka();
            }
        }
        return null;
    }
}