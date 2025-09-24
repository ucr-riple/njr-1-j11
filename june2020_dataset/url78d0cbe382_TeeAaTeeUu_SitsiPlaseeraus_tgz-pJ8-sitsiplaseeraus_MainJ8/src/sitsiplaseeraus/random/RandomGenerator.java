package sitsiplaseeraus.random;

import omatTietorakenteet.ArrayList;
import omatTietorakenteet.Hakemisto;
import omatTietorakenteet.Vektori;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.Sitsit;

/**
 * Hoitaa kolmea asiaa: satunnaisten nimien luomista, niiden käyttämistä
 * satunnaisen sitsin luomiseen sekä sitsaajien ja yhteyksien tulostamiseen.
 */
public class RandomGenerator {

    /**
     * Tulostaa sitsaajien väliset yhteydet, eli kuka pitää kenestäkin ja miten
     * paljon.
     *
     * @param sitsit Josta tiedot tutkitaan
     */
    public static void tulostaYhteydet(Sitsit sitsit) {
        System.out.println("\n" + "Yhteydet" + "\n");

        Hakemisto<Sitsaaja, Hakemisto> kaikkiYhteydet = sitsit.palautaYhteydet();
        int moneskoYhteys = 0;
        int moneskoSitsaaja = 0;
        int moneskoyhteydellinen = 0;
        for (Vektori<Sitsaaja, Hakemisto> sitsaajanYhteydet : kaikkiYhteydet) {
            Sitsaaja sitsaaja = (Sitsaaja) sitsaajanYhteydet.getKey();

            if (sitsaaja.avecIsSet()) {
                System.out.println(sitsaaja.getNimi() + " haluaa olla sitsaajan " + sitsaaja.getAvec().getNimi() + " avec");
            }
            if (sitsaaja.puolisoIsSet()) {
                System.out.println(sitsaaja.getNimi() + " haluaa olla sitsaajan " + sitsaaja.getPuoliso().getNimi() + " puoliso");
            }

            Hakemisto<Sitsaaja, Integer> yhteydet = (Hakemisto<Sitsaaja, Integer>) sitsaajanYhteydet.getValue();
            moneskoSitsaaja++;
            boolean eka = true;
            for (Vektori<Sitsaaja, Integer> yhteys : yhteydet) {
                Sitsaaja kohdeSitsaaja = (Sitsaaja) yhteys.getKey();
                int arvo = (Integer) yhteys.getValue();
                System.out.println(sitsaaja.getNimi() + " pit\u00e4\u00e4 sitsaajasta " + kohdeSitsaaja.getNimi() + " arvolla " + arvo);
                moneskoYhteys++;
                if (eka) {
                    moneskoyhteydellinen++;
                    eka = false;
                }
            }
        }
        System.out.println("\n" + "----" + moneskoYhteys + "-" + moneskoSitsaaja + "-" + moneskoyhteydellinen + "--" + "\n");
    }

    /**
     * Tulostaa sitsaajat heidän oikeassa järjestyksessään pöydittäin
     *
     * @param sitsit Josta tiedot tutkitaan
     */
    public static void tulostaSitsaajat(Sitsit sitsit) {
        System.out.println("\n" + "Sitsaajat" + "\n");

        for (int i = 0; i < sitsit.poytienMaara(); i++) {
            boolean even = true;

            for (Sitsaaja sitsaaja : sitsit.palautaPoydanSitsaajat(i)) {
                if (even == true) {
                    System.out.print(sitsaaja.getNimi() + sukupuoli(sitsaaja) + "\t:\t");
                    even = false;
                } else {
                    System.out.println(sitsaaja.getNimi() + sukupuoli(sitsaaja));
                    even = true;
                }
            }
            if (even = true) {
                System.out.println("\n");
            } else {
                System.out.println();
            }
        }
        System.out.println("\n" + "--------" + "\n");
    }

    private static String sukupuoli(Sitsaaja sitsaaja) {
        if (sitsaaja.isMies()) {
            return " (mies) ";
        } else {
            return " (nainen) ";
        }
    }
    private RandomNimi nimet;

    public RandomGenerator() {
        this.nimet = new RandomNimi();
    }

    public String randomNimi() {
        int random = Random.luo(1);
        if (random == 1) {
            return this.palautaNimi(true);
        } else {
            return this.palautaNimi(false);
        }
    }

    /**
     * Luo koko nimen, joko miehen tai naisen
     *
     * @param mies Onko mies vai ei
     * @return Naisen tai miehen koko nimen
     */
    public String palautaNimi(Boolean mies) {
        if (mies) {
            return this.nimet.palautaEtunimiMiehen() + " " + this.nimet.palautaSukunimi();
        } else {
            return this.nimet.palautaEtunimiNaisen() + " " + this.nimet.palautaSukunimi();
        }
    }

    /**
     * Täyttää sitsit satunnaisilla tiedoilla, eli lisää halutun määrän
     * sitsaajia ja heidän välisiä yhteyksiä sekä avecit ja puolisot
     *
     * @param montakoSitsaajaa
     * @param montakoYhteytta
     * @param sitsit
     */
    public void taytaRandomDatalla(int montakoSitsaajaa, int montakoYhteytta, Sitsit sitsit) {
        this.lisaaNimia(montakoSitsaajaa, sitsit);

        lisaaPuolisojaJaAveceja(montakoSitsaajaa / 5 / 2, sitsit);

        while (sitsit.yhteyksienMaara() < montakoYhteytta) {
            Yhteydet.lisaaYhteyksia(montakoYhteytta, sitsit);
        }
    }

    private void lisaaNimia(int montakoSitsaajaa, Sitsit sitsit) {
        for (int i = 0; i < montakoSitsaajaa; i++) {
            int kumpi = Random.luo(1);

            Sitsaaja sitsaaja;
            if (kumpi == 1) {
                sitsaaja = new Sitsaaja(this.palautaNimi(true), true);
            } else {
                sitsaaja = new Sitsaaja(this.palautaNimi(false), false);
            }
            sitsit.addPaikka().setSitsaaja(sitsaaja);
        }
    }

    private static void lisaaPuolisojaJaAveceja(int kuinkaPaljonLuodaan, Sitsit sitsit) {
        ArrayList<Sitsaaja> sitsaajat = sitsit.getSitsaajat();

        int puolisoja = kuinkaPaljonLuodaan / 3;

        lisaaPuolisoja(puolisoja, sitsaajat);
        lisaaAveceja(kuinkaPaljonLuodaan - puolisoja, sitsaajat);
    }

    private static void lisaaPuolisoja(int puolisoja, ArrayList<Sitsaaja> sitsaajat) {
        int i = 0;
        do {
            Sitsaaja sitsaaja = Yhteydet.annaRandomSitsaaja(sitsaajat);
            do {
                sitsaaja = Yhteydet.annaRandomSitsaaja(sitsaajat);
            } while (sitsaaja.puolisoIsSet() == true || sitsaaja.avecIsSet() == true);

            Sitsaaja kohdeSitsaaja = Yhteydet.annaToinenRandomSitsaaja(sitsaaja, sitsaajat);
            do {
                kohdeSitsaaja = Yhteydet.annaToinenRandomSitsaaja(sitsaaja, sitsaajat);
            } while (kohdeSitsaaja.puolisoIsSet() == true || kohdeSitsaaja.avecIsSet() == true);

            sitsaaja.setPuoliso(kohdeSitsaaja);
            kohdeSitsaaja.setPuoliso(sitsaaja);

            i++;
        } while (i < puolisoja);
    }

    private static void lisaaAveceja(int aveceja, ArrayList<Sitsaaja> sitsaajat) {
        int i = 0;
        do {
            Sitsaaja sitsaaja = Yhteydet.annaRandomSitsaaja(sitsaajat);;
            do {
                sitsaaja = Yhteydet.annaRandomSitsaaja(sitsaajat);
            } while (sitsaaja.puolisoIsSet() == true || sitsaaja.avecIsSet() == true);

            Sitsaaja kohdeSitsaaja = Yhteydet.annaRandomSitsaaja(sitsaajat);
            do {
                kohdeSitsaaja = Yhteydet.annaRandomSitsaaja(sitsaajat);
            } while (kohdeSitsaaja.puolisoIsSet() == true || kohdeSitsaaja.avecIsSet() == true);

            sitsaaja.setAvec(kohdeSitsaaja);
            kohdeSitsaaja.setAvec(sitsaaja);

            i++;
        } while (i < aveceja);
    }
}
