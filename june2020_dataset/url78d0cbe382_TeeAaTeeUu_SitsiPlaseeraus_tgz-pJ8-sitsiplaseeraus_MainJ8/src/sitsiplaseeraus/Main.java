package sitsiplaseeraus;

import Lataaja.AsetustenTulostaja;
import Lataaja.TiedostonKasittelija;
import jarjestelija.ParhaanLoytaja;
import omatTietorakenteet.ArrayList;
import sitsiplaseeraus.random.RandomGenerator;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            normiAjo(false, 100);
        } else if (args.length == 1) {
            normiAjo(false, Integer.parseInt(args[0]));
        } else if (args.length == 2) {
            if (args[1].equals("tallenna")) {
                normiAjo(true, Integer.parseInt(args[0]));
            } else {
                asetusTiedostoAnnettu(args[1], Integer.parseInt(args[0]));
            }
        }
    }

    private static void vieTiedostoon(Sitsit sitsit, String tiedosto) {
        AsetustenTulostaja at = new AsetustenTulostaja(sitsit);
        at.asetaPoytienMaara(3);

        int[] poytienKoot = new int[3];
        poytienKoot[0] = sitsit.sitsaajienMaara() / 3;
        poytienKoot[1] = sitsit.sitsaajienMaara() / 3;
        poytienKoot[2] = sitsit.sitsaajienMaara() - poytienKoot[0] - poytienKoot[1];

        at.asetaPoytienKoot(poytienKoot);

        at.vieAsetuksetTiedostoon(tiedosto);
    }

    private static Sitsit tuoTiedostosta(String tiedosto) {
        TiedostonKasittelija tk = new TiedostonKasittelija(tiedosto);
        tk.run();

        Sitsit sitsit = new Sitsit(tk.getPoytienMaara());
        ArrayList<Sitsaaja> sitsaajat = tk.getSitsaajat();

        int monesko = 0;
        for (int i = 0; i < tk.getPoytienMaara(); i++) {
            int sitsaajienMaaraPoydassa = tk.getPoytienKoot().get(i);
            for (int j = 0; j < sitsaajienMaaraPoydassa; j++) {
                sitsit.addPaikka(i);
                sitsit.getPaikka(monesko).setSitsaaja(sitsaajat.get(monesko));
                monesko++;
            }
        }
        return sitsit;
    }

    private static void normiAjo(boolean tallennetaankoTiedostoon, int sekunnit) {
        Sitsit sitsit = new Sitsit(3);
        
        RandomGenerator random = new RandomGenerator();

        int arvo = 80;
        random.taytaRandomDatalla(arvo, arvo * arvo / 2, sitsit);

        RandomGenerator.tulostaSitsaajat(sitsit);

        if (tallennetaankoTiedostoon) {
            vieTiedostoon(sitsit, "testidata.sjt");
        }

        ParhaanLoytaja parhaanLoytaja = new ParhaanLoytaja(sitsit);
        parhaanLoytaja.optimoiIstumapaikat(sekunnit);
    }

    private static void asetusTiedostoAnnettu(String tiedosto, int sekunnit) {
        Sitsit sitsit = tuoTiedostosta(tiedosto);

        vieTiedostoon(sitsit, "testidata2.sjt");

        ParhaanLoytaja parhaanLoytaja = new ParhaanLoytaja(sitsit);
        parhaanLoytaja.optimoiIstumapaikat(sekunnit);
    }
}
