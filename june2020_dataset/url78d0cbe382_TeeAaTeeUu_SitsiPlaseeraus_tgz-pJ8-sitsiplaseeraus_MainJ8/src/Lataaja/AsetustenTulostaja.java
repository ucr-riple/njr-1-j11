package Lataaja;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import omatTietorakenteet.Vektori;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.Sitsit;

/**
 * Kerää sitsi-oliosta tiedot ja tallentaa sen mukaisen asetustiedoston.
 */
public class AsetustenTulostaja {

    private Sitsit sitsit;
    private int poytienMaara;
    private String tiedosto;
    private String sisalto;
    private BufferedWriter bw;
    private FileWriter fw;
    private int[] poytienKoot;

    /**
     * Alustaa olion.
     * @param sitsit
     */
    public AsetustenTulostaja(Sitsit sitsit) {
        this.sitsit = sitsit;
    }

    /**
     * Kuinka monta pöytää halutaan asetustiedoston määrittävän.
     * @param poytienMaara
     */
    public void asetaPoytienMaara(int poytienMaara) {
        this.poytienMaara = poytienMaara;
    }

    /**
     * Asettaa pöytien koot annetussa taulukossa.
     * @param poytienKoot
     * @return true, jos pöytien määrä on OK.
     */
    public boolean asetaPoytienKoot(int[] poytienKoot) {
        if (poytienKoot.length == poytienMaara) {
            this.poytienKoot = poytienKoot;
            return true;
        }
        this.poytienKoot = new int[poytienMaara];
        System.arraycopy(poytienKoot, 0, this.poytienKoot, 0, poytienMaara);
        return false;
    }

    /**
     * Tallentaa annettuun tiedostoon asetukset. Jos tiedosto puuttuu, se luodaan.
     * @param tiedosto
     */
    public void vieAsetuksetTiedostoon(String tiedosto) {
        this.tiedosto = tiedosto;

        tuotaTiedostonSisalto();

        laitaTiedostoon();
    }

    /**
     * http://www.mkyong.com/java/how-to-write-to-file-in-java-bufferedwriter-example/
     */
    private void laitaTiedostoon() {
        try {
            File file = new File(tiedosto);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            fw = new FileWriter(file.getAbsoluteFile());
            bw = new BufferedWriter(fw);
            bw.write(sisalto);
            bw.close();

            System.out.println("Kirjoitettiin tiedostoon " + tiedosto);

        } catch (IOException e) {
            System.out.println("jotain hässäkkää tapahtui");
        }
    }

    private void tuotaTiedostonSisalto() {      
        sisalto = luoPoytienMaaraRivi();
        sisalto += luoSitsaajaRivit();
        sisalto += luoAvecRivit();
        sisalto += luoPuolisoRivit();
        sisalto += luoYhteysRivit();
    }

    private String luoPoytienMaaraRivi() {
        String palautettava = Integer.toString(poytienMaara);

        for (int poydanKoko : poytienKoot) {
            palautettava += "," + Integer.toString(poydanKoko);
        }
        return palautettava + "\n";
    }

    private String luoSitsaajaRivit() {
        String palautettava = "--sitsaajat--" + "\n";

        for (Sitsaaja sitsaaja : sitsit.getSitsaajat()) {
            String sukupuoli;
            if (sitsaaja.isMies()) {
                sukupuoli = "1";
            } else {
                sukupuoli = "-1";
            }
            palautettava += sitsaaja.getNimi() + "," + sukupuoli + "\n";
        }
        return palautettava;
    }

    private String luoAvecRivit() {
        String palautettava = "--avecit--" + "\n";

        for (Sitsaaja sitsaaja : sitsit.getSitsaajat()) {
            if (sitsaaja.avecIsSet()) {
                palautettava += sitsaaja.getNimi() + "," + sitsaaja.getAvec().getNimi() + "\n";
            }
        }
        return palautettava;
    }

    private String luoPuolisoRivit() {
        String palautettava = "--puolisot--" + "\n";

        for (Sitsaaja sitsaaja : sitsit.getSitsaajat()) {
            if (sitsaaja.puolisoIsSet()) {
                palautettava += sitsaaja.getNimi() + "," + sitsaaja.getPuoliso().getNimi() + "\n";
            }
        }
        return palautettava;
    }

    private String luoYhteysRivit() {
        String palautettava = "--yhteydet--" + "\n";

        for (Sitsaaja sitsaaja : sitsit.getSitsaajat()) {
            for (Vektori<Sitsaaja, Integer> yhteys : sitsaaja.palautaYhteydet()) {
                palautettava += sitsaaja.getNimi() + "," + yhteys.getKey().getNimi() + "," + yhteys.getValue() + "\n";
            }
        }
        return palautettava;
    }
}
