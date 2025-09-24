package br.com.bit.ideias.reflection.cache;


/**
 * @author Leonardo Campos
 * @date 24/11/2009
 */
public class CacheConfig {

    private int maxEntries;
    
    public CacheConfig(int maxEntries) {
        super();
        this.maxEntries = maxEntries;
    }

    public int getMaxEntries() {
        return maxEntries;
    }

    public void setMaxEntries(int maxEntries) {
        this.maxEntries = maxEntries;
    }
}
