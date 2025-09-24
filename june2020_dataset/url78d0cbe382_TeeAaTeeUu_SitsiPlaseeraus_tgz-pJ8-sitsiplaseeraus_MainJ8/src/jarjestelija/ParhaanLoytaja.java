package jarjestelija;

import omatTietorakenteet.Hakemisto;
import omatTietorakenteet.Vektori;
import sitsiplaseeraus.Paikka;
import sitsiplaseeraus.Sitsaaja;
import sitsiplaseeraus.Sitsit;
import sitsiplaseeraus.random.RandomGenerator;

/**
 * Toimii paikallisten maksimien hallinoijana, eli aloittaa aina uuden
 * satunnaisen optimoidun paikan haun ja vertaa, josko tämänkertainen olisi
 * parempi kuin edelliset.
 *
 * Pitää kirjaa parhaasta löydetystä järjestyksestä samalla, kun parempia
 * yritetään löytää tyhjästä alkaen.
 */
public class ParhaanLoytaja {

    private Optimoija optimoija;
    private Hakemisto<Paikka, Sitsaaja> ajonParhaatPaikat;
    private Hakemisto<Integer, Hakemisto> parhaatPoydat;
    private Hakemisto<Integer, Hakemisto> ekatPoydat;
    private long aika;
    private double parhaanPisteet;
    private final Sitsit sitsit;
    private Hakemisto<Integer, Sitsaaja> ekatPaikatPoydassa;
    private Hakemisto<Integer, Paikka> kohdePaikat;
    private double ajossaPisteet;
    private double parhaatPariJaSukupuoliPisteet;
    private double ajossaParhaatPariJaSukupuoliPisteet;

    /**
     * Alustaa olion-käyttöön.
     *
     * @param sitsit
     */
    public ParhaanLoytaja(Sitsit sitsit) {
        this.sitsit = sitsit;
        this.optimoija = new Optimoija(sitsit);
        this.parhaatPoydat = new Hakemisto<Integer, Hakemisto>();
        this.ekatPoydat = new Hakemisto<Integer, Hakemisto>();
        this.parhaanPisteet = 0.0;
        this.parhaatPariJaSukupuoliPisteet = 0.0;

        asetaLopetusHook();
    }

    /**
     * Käy annetun ajan verran läpi järjestyksiä, ja lopulta tulostaa parhaan
     * löytämänsä.
     *
     * @param sekunttia
     */
    public void optimoiIstumapaikat(int sekunttia) {
        this.aika = System.currentTimeMillis();

        RandomGenerator.tulostaSitsaajat(sitsit);

        tallennaMuistiin(this.sitsit.palautaPaikkaSitsaajaParit(), this.ekatPoydat);

        while (aika + 1000 * sekunttia > System.currentTimeMillis()) {
            this.ajonParhaatPaikat = optimoija.optimoiIstumapaikat(sekunttia, aika, parhaatPariJaSukupuoliPisteet);
            this.ajossaPisteet = this.optimoija.getVanhassaPisteita();
            this.ajossaParhaatPariJaSukupuoliPisteet = this.optimoija.getVanhassaPariJaSukupuoliPisteita();
            if(this.ajossaParhaatPariJaSukupuoliPisteet > this.parhaatPariJaSukupuoliPisteet - 10) {
                this.parhaatPariJaSukupuoliPisteet = this.ajossaParhaatPariJaSukupuoliPisteet;
            }
            if (this.parhaanPisteet < this.ajossaPisteet) {
                tallennaMuistiin(this.ajonParhaatPaikat, this.parhaatPoydat);
            }
            palautaEkatPaikat(this.ekatPoydat);

            System.out.println("tähän mennessä paras on saanut pisteitä " + this.parhaanPisteet);
        }
        palautaEkatPaikat(this.parhaatPoydat);

        RandomGenerator.tulostaSitsaajat(sitsit);

        System.out.println("löydettiin lopulta sellainen, joka sai pisteitä " + this.parhaanPisteet);
    }

    private void tallennaMuistiin(Hakemisto<Paikka, Sitsaaja> ajonParhaatPaikat, Hakemisto<Integer, Hakemisto> parhaatPoydat) {
        parhaatPoydat.clear();

        for (Vektori<Paikka, Sitsaaja> paikka : ajonParhaatPaikat) {
            if (parhaatPoydat.containsKey(paikka.getKey().getPoyta()) == false) {
                parhaatPoydat.put(paikka.getKey().getPoyta(), new Hakemisto<Integer, Sitsaaja>());
            }
            parhaatPoydat.get(paikka.getKey().getPoyta()).put(paikka.getKey().getPaikka(), paikka.getValue());
        }
        this.parhaanPisteet = this.ajossaPisteet;
    }

    private void palautaEkatPaikat(Hakemisto<Integer, Hakemisto> ekatPoydat) {
        for (Vektori<Integer, Hakemisto> poyta : ekatPoydat) {
            kohdePaikat = this.sitsit.palautaPoydanPaikat(poyta.getKey());
            ekatPaikatPoydassa = poyta.getValue();

            for (Vektori<Integer, Sitsaaja> paikka : this.ekatPaikatPoydassa) {
                kohdePaikat.get(paikka.getKey()).setSitsaaja(paikka.getValue());
            }
        }
        System.out.println("\n" + "\n" + "\n" + "vanhat Palautettu" + "\n" + "\n" + "\n");
    }

    private void asetaLopetusHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                    palautaEkatPaikat(parhaatPoydat);

                    RandomGenerator.tulostaSitsaajat(sitsit);

                    System.out.println("löydettiin lopulta sellainen, joka sai pisteitä " + parhaanPisteet);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }
}
