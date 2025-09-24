package Lataaja;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import omatTietorakenteet.ArrayList;
import omatTietorakenteet.Hakemisto;
import omatTietorakenteet.Vektori;
import sitsiplaseeraus.Sitsaaja;

/**
 * Lukee tiedostosta asetukset, joiden avulla asetusten mukainen sitsi-olio on
 * mahdollista luoda.
 */
public class TiedostonKasittelija {

    private final String file;
    private Sitsaaja sitsaaja;
    private Hakemisto<Sitsaaja, Hakemisto> yhteydet;
    private Hakemisto<String, Sitsaaja> sitsaajat;
    private String[] tiedot;
    private Hakemisto<Sitsaaja, Integer> yhteys;
    private Sitsaaja kohdeSitsaaja;
    private Hakemisto<Sitsaaja, Integer> valiaikainenMap;
    private Hakemisto<Sitsaaja, Hakemisto> palautettavaMap;
    private ArrayList<Sitsaaja> sitsaajatLista;
    private int poytienMaara;
    private ArrayList<Integer> poytienKoot;
    private int arvo;
    private int index;

    /**
     * Alustaa ja asettaa tiedoston, josta tiedot otetaan.
     *
     * @param tiedosto
     */
    public TiedostonKasittelija(String tiedosto) {
        this.file = tiedosto;
        this.alustaLuokat();
    }

    /**
     * Palauttaa asetustiedostosta löytyneet sitsaajat.
     *
     * @return Sitsaajat
     */
    public ArrayList<Sitsaaja> getSitsaajat() {
        sitsaajatLista.clear();
        for (Vektori<String, Sitsaaja> sitsaaja : sitsaajat) {
            sitsaajatLista.add(sitsaaja.getValue());
            if (sitsaaja.getValue().yhteyksienMaara() == 0) {
                System.out.println("yhteyksiä nolla");
            }
        }
        return sitsaajatLista;
    }

    /**
     * Palauttaa pöytien määrän.
     *
     * @return Pöytien määrä.
     */
    public int getPoytienMaara() {
        return poytienMaara;
    }

    /**
     * Palauttaa pöytien koot taulukossa.
     *
     * @return Pöytien koot.
     */
    public ArrayList<Integer> getPoytienKoot() {
        return poytienKoot;
    }

    /**
     * Lukee tiedoston ja tallentaa muistiin siitä löytyvät tiedot.
     *
     * http://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
     */
    public void run() {
        yhteydet.clear();
        sitsaajat.clear();


        String csvFile = file;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
        } catch (FileNotFoundException ex) {
            System.out.println("tiedostoa ei löytynyt");
            System.exit(0);
        }
        try {
            poytienMaara(br, cvsSplitBy);
            sitsaajat(br, cvsSplitBy);
            avecit(br, cvsSplitBy);
            puolisot(br, cvsSplitBy);
            yhteydet(br, cvsSplitBy);

        } catch (IOException ex) {
            System.out.println("hässäkkää");
        }

        System.out.println("Tiedoston luku onnistui");
    }

    private Sitsaaja lisaaSitsaaja(String nimi, String sukupuoli2) {
        int sukupuoli = sukupuoli(sukupuoli2);
        if (sukupuoli == 0) {
            return new Sitsaaja(tiedot[0]);
        } else {
            if (sukupuoli == 1) {
                return new Sitsaaja(tiedot[0], true);
            } else {
                return new Sitsaaja(tiedot[0], false);
            }
        }
    }

    private Sitsaaja sitsaaja(String nimi) {
        return sitsaajat.get(nimi);
    }

    private int sukupuoli(String tieto) {
        if (tieto.equals("1")) {
            return 1;
        } else if (tieto.equals("-1")) {
            return -1;
        } else {
            return 0;
        }
    }

    private void alustaLuokat() {
        yhteydet = new Hakemisto<Sitsaaja, Hakemisto>();
        sitsaajat = new Hakemisto<String, Sitsaaja>();
        yhteys = new Hakemisto<Sitsaaja, Integer>();
        valiaikainenMap = new Hakemisto<Sitsaaja, Integer>();
        palautettavaMap = new Hakemisto<Sitsaaja, Hakemisto>();
        sitsaajatLista = new ArrayList<Sitsaaja>();
        poytienKoot = new ArrayList<Integer>();
    }

    private void poytienMaara(BufferedReader br, String cvsSplitBy) throws NumberFormatException, IOException {
        String line;
        //poytien määrä
        System.out.println("pöytien määrä");
        while ((line = br.readLine()) != null && line.equals("--sitsaajat--") == false) {

            tiedot = line.split(cvsSplitBy);
            this.poytienMaara = Integer.parseInt(tiedot[0]);

            for (int i = 0; i < getPoytienMaara(); i++) {
                this.poytienKoot.add((int) Integer.parseInt(tiedot[i + 1]));
                System.out.println(tiedot[i + 1]);
            }
        }
    }

    private void sitsaajat(BufferedReader br, String cvsSplitBy) throws IOException {
        String line;
        //sitsaajat
        System.out.println("sitsaajat");
        while ((line = br.readLine()) != null && line.equals("--avecit--") == false) {

            tiedot = line.split(cvsSplitBy);
            sitsaaja = lisaaSitsaaja(tiedot[0], tiedot[1]);

            System.out.println(sitsaaja.getNimi());

            sitsaajat.put(sitsaaja.getNimi(), sitsaaja);
        }
    }

    private void avecit(BufferedReader br, String cvsSplitBy) throws IOException {
        String line;
        //avecit
        System.out.println("avecit");
        while ((line = br.readLine()) != null && line.equals("--puolisot--") == false) {

            tiedot = line.split(cvsSplitBy);
            sitsaaja = sitsaaja(tiedot[0]);
            kohdeSitsaaja = sitsaaja(tiedot[1]);

            sitsaaja.setAvec(kohdeSitsaaja);

            System.out.println(sitsaaja.getNimi() + " ja " + sitsaaja.getAvec().getNimi());
        }
    }

    private void puolisot(BufferedReader br, String cvsSplitBy) throws IOException {
        String line;
        //puolisot
        System.out.println("puolisot");
        while ((line = br.readLine()) != null && line.equals("--yhteydet--") == false) {

            tiedot = line.split(cvsSplitBy);
            sitsaaja = sitsaaja(tiedot[0]);
            kohdeSitsaaja = sitsaaja(tiedot[1]);

            sitsaaja.setPuoliso(kohdeSitsaaja);

            System.out.println(sitsaaja.getNimi() + " ja " + sitsaaja.getPuoliso().getNimi());
        }
    }

    private void yhteydet(BufferedReader br, String cvsSplitBy) throws NumberFormatException, IOException {
        String line;
        //yhteydet
        System.out.println("yhteydet");
        for (String string : tiedot) {
        }
        while ((line = br.readLine()) != null) {
            tiedot = line.split(cvsSplitBy);

            sitsaaja = sitsaaja(tiedot[0]);
            kohdeSitsaaja = sitsaaja(tiedot[1]);
            arvo = Integer.parseInt(tiedot[2]);

            pyritaanLisaamaanYhteys();
        }
    }

    private void pyritaanLisaamaanYhteys() {
        sitsaaja.setYhteys(kohdeSitsaaja, arvo);
        if (sitsaaja.yhteyksienMaara() == 0) {
            System.out.println("yhteyden muodostus epäonnostui");
        }
    }
}
