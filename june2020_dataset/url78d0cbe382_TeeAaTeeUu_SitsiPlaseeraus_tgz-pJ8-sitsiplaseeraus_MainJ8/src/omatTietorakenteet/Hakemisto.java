package omatTietorakenteet;

/**
 * Ulkoisesti toimii kuin Javan oma Hakemisto (melkein), mutta toteutus on vain
 * tällä hetkellä normaali hakemisto. Luodaan vielä se oikea Hakemisto
 * myöhemmin.
 *
 * @param <K>
 * @param <V>
 */
public class Hakemisto<K, V> implements Iterable<Vektori> {

    private Object[] avainVarasto;
    private Object[] arvoVarasto;
    private int alkukoko = 15;
    private int mahtuu;
    private int koko;
    public Hakemisto tama = this;

    public Hakemisto() {
        avainVarasto = new Object[alkukoko];
        arvoVarasto = new Object[alkukoko];
        mahtuu = alkukoko;
        koko = 0;
    }

    public V get(K key) {
        return getArvo(key);
    }

    public K getAvain(V value) {
        for (int i = 0; i < koko; i++) {
            if (arvoVarasto[i].equals(value)) {
                return (K) avainVarasto[i];
            }
        }
        return null;
    }

    public V getArvo(K key) {
        for (int i = 0; i < koko; i++) {
            if (avainVarasto[i].equals(key)) {
                return (V) arvoVarasto[i];
            }
        }
        return null;
    }

    public K getAvainIndexilla(int i) {
        if (i > koko || i < 0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        return (K) avainVarasto[i];
    }

    public V getArvoIndexilla(int i) {
        if (i > koko || i < 0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        return (V) arvoVarasto[i];
    }

    public Vektori<K, V> getVektoriAvaimella(K key) {
        int monesko = getAvainIndexAvaimella(key);
        return new Vektori<K, V>((K) avainVarasto[monesko], (V) arvoVarasto[monesko]);
    }

    public Vektori<K, V> getVektoriArvolla(V value) {
        int monesko = getAvainIndexArvolla(value);
        return new Vektori<K, V>((K) avainVarasto[monesko], (V) arvoVarasto[monesko]);
    }

    /**
     * Otetaan vielä parempaan käyttöön itse ohjelmassa, lisää vain loppuun
     * tiedot eikä varmista löytyykö samanlaista jo aiemmin.
     *
     * @param key
     * @param value
     */
    public void add(K k, V v) {
        if (koko == mahtuu - 2) {
            kasvata();
        }
        avainVarasto[koko] = (K) k;
        arvoVarasto[koko] = (V) v;
        koko++;
    }

    public boolean put(K k, V v) {
        int monesko = getAvainIndexAvaimella(k);

        if (monesko == -1) {
            if (koko == mahtuu - 2) {
                kasvata();
            }
            avainVarasto[koko] = (K) k;
            arvoVarasto[koko] = (V) v;
            koko++;
            return true;
        } else {
            arvoVarasto[monesko] = v;
            return true;
        }
    }

    public int getAvainIndexArvolla(V value) {
        for (int i = 0; i < koko; i++) {
            if (arvoVarasto[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public V remove(K key) {
        int index = getAvainIndexAvaimella(key);
        if (index == -1) {
            return null;
        } else {
            V vanha = (V) arvoVarasto[index];
            for (int i = index; i < koko - 1; i++) {
                avainVarasto[i] = avainVarasto[i + 1];
                arvoVarasto[i] = arvoVarasto[i + 1];
            }
            koko--;
            return vanha;
        }
    }

    public int getArvoIndexAvaimella(K key) {
        for (int i = 0; i < koko; i++) {
            if (avainVarasto[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    public int getAvainIndexAvaimella(K key) {
        for (int i = 0; i < koko; i++) {
            if (avainVarasto[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    public int getArvoIndexArvolla(V value) {
        for (int i = 0; i < koko; i++) {
            if (arvoVarasto[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    private void kasvata() {
        mahtuu *= 2;
        Object[] avainVarasto = new Object[mahtuu];
        Object[] arvoVarasto = new Object[mahtuu];

        for (int i = 0; i < koko; i++) {
            avainVarasto[i] = this.avainVarasto[i];
            arvoVarasto[i] = this.arvoVarasto[i];
        }
        this.avainVarasto = avainVarasto;
        this.arvoVarasto = arvoVarasto;
    }

    public int size() {
        return koko;
    }

    public int clear() {
        return koko;
    }

    public boolean isEmpty() {
        if (koko == 0) {
            return true;
        }
        return false;
    }

    public boolean containsKey(K e) {
        for (int i = 0; i < koko; i++) {
            if (avainVarasto[i].equals(e)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(V v) {
        for (int i = 0; i < koko; i++) {
            if (arvoVarasto[0].equals(v)) {
                return true;
            }
        }
        return false;
    }

    public java.util.Iterator<Vektori> iterator() {
        return new java.util.Iterator() {
            private int paikka = 0;

            public boolean hasNext() {
                if (paikka < koko) {
                    return true;
                }
                return false;
            }

            public Vektori next() {
                return new Vektori(avainVarasto[paikka], arvoVarasto[paikka++]);
            }

            public void remove() {
                tama.remove(paikka);
            }
        };
    }
}
