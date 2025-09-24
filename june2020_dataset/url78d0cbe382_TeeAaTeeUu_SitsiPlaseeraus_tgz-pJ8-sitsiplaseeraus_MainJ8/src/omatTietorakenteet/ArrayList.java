package omatTietorakenteet;

/**
 * Toimii kuin Javan oma ArrayList niiltä osin, kuin niitä metodeja olen
 * tarvinnut.
 *
 * @param <E>
 */
public class ArrayList<E> implements Iterable<E> {

    private Object[] varasto;
    private int alkukoko = 15;
    private int mahtuu;
    private int koko;
    public ArrayList tama = this;

    public ArrayList() {
        varasto = new Object[alkukoko];
        mahtuu = alkukoko;
        koko = 0;
    }

    public E get(int i) {
        if (i > koko || i < 0) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        return (E) varasto[i];
    }

    public boolean add(E e) {
        if (koko == mahtuu - 2) {
            kasvata();
        }
        varasto[koko] = (E) e;
        koko++;
        return true;
    }

    private void kasvata() {
        mahtuu *= 2;
        Object[] varasto = new Object[mahtuu];

        for (int i = 0; i < koko; i++) {
            varasto[i] = get(i);
        }
        this.varasto = varasto;
    }

    public int size() {
        return koko;
    }

    public void clear() {
        varasto = new Object[alkukoko];
        mahtuu = alkukoko;
        koko = 0;
    }

    public boolean isEmpty() {
        if (koko == 0) {
            return true;
        }
        return false;
    }

    public boolean contains(E e) {
        for (Object object : varasto) {
            if (e.equals(object)) {
                return true;
            }
        }
        return false;
    }

    public E remove(int monesko) {
        if (monesko > koko || monesko < 0) {
            return null;
        } else {
            E vanha = (E) varasto[monesko];
            for (int i = monesko; i < koko -1; i++) {
                    varasto[i] = varasto[i + 1];
            }
            koko--;
            return (E) vanha;
        }
    }

    public E remove(E e) {
        for (int i = 0; i < koko; i++) {
            if(varasto[i].equals(e)) {
                return remove(i);
            }
        }
        return null;
    }

    public java.util.Iterator<E> iterator() {
        return new java.util.Iterator() {
            private int paikka = 0;

            public boolean hasNext() {
                if (paikka < koko) {
                    return true;
                }
                return false;
            }

            public E next() {
                return (E) get(paikka++);
            }

            public void remove() {
                tama.remove(paikka);
            }
        };
    }

    /**
     * Päivittää annetun indexin sisällön.
     * @param i monesko päivitetään.
     * @param e mikä laitetaan tilalle.
     * @return true, index oli kelvollinen.
     */
    public boolean update(int i, E e) {
        if(i >= 0 && i < koko) {
            varasto[i] = e;
            return true;
        }
        return false;
    }
}
