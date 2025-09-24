package sitsiplaseeraus.random;

import omatTietorakenteet.ArrayList;

/**
 * Sisältää paljon erilaisia sukunimiä
 */
public class Sukunimet{

    ArrayList<String> sukunimet;

    /**
     * Alustaa käyttöön
     */
    public Sukunimet() {
        sukunimet = new ArrayList<String>();
        this.lisaaSukunimet();
    }

    /**
     * Palauttaa satunnaisen sukunimen
     * @return Sukunimi
     */
    public String palautaSukunimi() {
        int koko = this.sukunimet.size();
        int random = Random.luo(koko - 1);
        return this.sukunimet.get(random);
    }

    private void lisaaSukunimet() {
        sukunimet.add("Ahmavaara");
        sukunimet.add("Aho");
        sukunimet.add("Ahtisaari");
        sukunimet.add("Ahtovuo");
        sukunimet.add("Ailio");
        sukunimet.add("Äimä");
        sukunimet.add("Airo");
        sukunimet.add("Alaja");
        sukunimet.add("Alkio");
        sukunimet.add("Arajärvi");
        sukunimet.add("Aura");
        sukunimet.add("Äyräpää");
        sukunimet.add("Enäjärvi");
        sukunimet.add("Haarla");
        sukunimet.add("Hainari");
        sukunimet.add("Harmaja");
        sukunimet.add("Harva");
        sukunimet.add("Harva");
        sukunimet.add("Hattara");
        sukunimet.add("Heikinheimo");
        sukunimet.add("Helismaa");
        sukunimet.add("Helo");
        sukunimet.add("Heporauta");
        sukunimet.add("Hiekkala");
        sukunimet.add("Hiidenheimo");
        sukunimet.add("Hirvensalo");
        sukunimet.add("Honkajuuri");
        sukunimet.add("Ivalo");
        sukunimet.add("Jaari");
        sukunimet.add("Jalas");
        sukunimet.add("Jännes");
        sukunimet.add("Järviluoma");
        sukunimet.add("Jousi");
        sukunimet.add("Juva");
        sukunimet.add("Kaila");
        sukunimet.add("Kairamo");
        sukunimet.add("Kalela");
        sukunimet.add("Kallia");
        sukunimet.add("Kalliala");
        sukunimet.add("Kannila");
        sukunimet.add("Karikoski");
        sukunimet.add("Kettunen");
        sukunimet.add("Kivalo");
        sukunimet.add("Kivekäs");
        sukunimet.add("Kivikoski");
        sukunimet.add("Kivirikko");
        sukunimet.add("Koskenniemi");
        sukunimet.add("Koskimies");
        sukunimet.add("Koulumies");
        sukunimet.add("Kurki-Suonio");
        sukunimet.add("Kuusi");
        sukunimet.add("Lehto");
        sukunimet.add("Leikola");
        sukunimet.add("Linko");
        sukunimet.add("Linkola");
        sukunimet.add("Linkomies");
        sukunimet.add("Linnala");
        sukunimet.add("Loimaranta");
        sukunimet.add("Louhivuori");
        sukunimet.add("Maasalo");
        sukunimet.add("Malmivaara");
        sukunimet.add("Mannermaa");
        sukunimet.add("Merikanto");
        sukunimet.add("Mikkola");
        sukunimet.add("Nevanlinna");
        sukunimet.add("Nuorteva");
        sukunimet.add("Paasikivi");
        sukunimet.add("Paasilinna");
        sukunimet.add("Paasio");
        sukunimet.add("Paasivirta");
        sukunimet.add("Paasivuori");
        sukunimet.add("Paatela");
        sukunimet.add("Päivänsalo");
        sukunimet.add("Palo");
        sukunimet.add("Paloheimo");
        sukunimet.add("Palosuo");
        sukunimet.add("Pihkala");
        sukunimet.add("Pohjanpalo");
        sukunimet.add("Poijärvi");
        sukunimet.add("Rahola");
        sukunimet.add("Rauanheimo");
        sukunimet.add("Rautapää");
        sukunimet.add("Rautavaara");
        sukunimet.add("Rautavirta");
        sukunimet.add("Rautavuori");
        sukunimet.add("Reenkola");
        sukunimet.add("Reenpää");
        sukunimet.add("Rihtniemi");
        sukunimet.add("Rislakki");
        sukunimet.add("Ritavuori");
        sukunimet.add("Ruusuvaara");
        sukunimet.add("Ruutu");
        sukunimet.add("Sadeniemi");
        sukunimet.add("Salmiala");
        sukunimet.add("Särkilahti");
        sukunimet.add("Savonjousi");
        sukunimet.add("Siilasvuo");
        sukunimet.add("Simojoki");
        sukunimet.add("Sirola");
        sukunimet.add("Soini");
        sukunimet.add("Soininen");
        sukunimet.add("Somerjoki");
        sukunimet.add("Suolahti");
        sukunimet.add("Susitaival");
        sukunimet.add("Svento");
        sukunimet.add("Talas");
        sukunimet.add("Talvela");
        sukunimet.add("Tanner");
        sukunimet.add("Tarjanne");
        sukunimet.add("Tavastähti");
        sukunimet.add("Teräsvuori");
        sukunimet.add("Tulenheimo");
        sukunimet.add("Tulikoura");
        sukunimet.add("Tunkelo");
        sukunimet.add("Turkka");
        sukunimet.add("Tuuliluoto");
        sukunimet.add("Tuulio");
        sukunimet.add("Tuura");
        sukunimet.add("Utsjoki");
        sukunimet.add("Vähäkallio");
        sukunimet.add("Välikangas");
        sukunimet.add("Valste");
        sukunimet.add("Valvanne");
        sukunimet.add("Vanni");
        sukunimet.add("Veistaro");
        sukunimet.add("Vennamo");
        sukunimet.add("Virkkunen");
        sukunimet.add("Voionmaa");
        sukunimet.add("Voipio");
        sukunimet.add("Vuorenjuuri");
        sukunimet.add("Vuorjoki");
        sukunimet.add("Waltari");
        sukunimet.add("Waris");
        sukunimet.add("Wiherheimo");
        sukunimet.add("Wihuri");
        sukunimet.add("Wilkama");
        sukunimet.add("Wuorenheimo");
        sukunimet.add("Wuorimaa");
        sukunimet.add("Yrjö-Koskinen");
    }
}
