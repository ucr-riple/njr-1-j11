package sitsiplaseeraus.random;

/**
 * Toimii käyttöliittymänä nimien pyytämiseen
 */
public class RandomNimi {

    private Sukunimet sukunimet;
    private EtunimetMiehen etunimetMiehen;
    private EtunimetNaisen etunimetNaisen;

    /**
     * Alustaa käyttöön
     */
    public RandomNimi() {
        sukunimet = new Sukunimet();
        etunimetMiehen = new EtunimetMiehen();
        etunimetNaisen = new EtunimetNaisen();
    }
    
    /**
     * Palauttaa satunnaisen Sukunimen
     * @return Sukunimi
     */
    protected String palautaSukunimi() {
        return this.sukunimet.palautaSukunimi();
    }

    /**
     * Palauttaa satunnaisen miehen etunimen
     * @return Miehen etunimi
     */
    protected String palautaEtunimiMiehen() {
        return this.etunimetMiehen.palautaEtunimi();
    }

    /**
     * Palauttaa satunnaisen naisen etunimen
     * @return Naisen etunimi
     */
    protected String palautaEtunimiNaisen() {
        return this.etunimetNaisen.palautaEtunimi();
    }
}
