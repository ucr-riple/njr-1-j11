package br.com.bit.ideias.reflection.cache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map.Entry;


/**
 * @author Leonardo Campos
 * @date 24/11/2009
 */
public class LRUCache implements Cache {
    private static final long serialVersionUID = 1L;
    private static LRUCache instance = new LRUCache();
    
    public static Cache getInstance() {
        return instance;
    }
    
    private class InnerCache extends LinkedHashMap<Object, SoftReference<Object>> {
        private static final long serialVersionUID = 1L;
        
        private final int maxEntries;

        public InnerCache(int maxEntries) {
            super(maxEntries+1, .75F,true);
            this.maxEntries = maxEntries;
        }

        @Override
        protected boolean removeEldestEntry(Entry<Object, SoftReference<Object>> eldest) {
            return this.size() > maxEntries;
        }
    }
    private InnerCache delegate;

    public LRUCache() {
        //verifica se existe arquivo de properties
        //TODO
        //se existir carrega e transforma em um config
        //TODO
        //caso contrário cria config padrão
        config(new CacheConfig(100));
    }
    
    public LRUCache(CacheConfig conf) {
        config(conf);
    }
    
    private void config(CacheConfig config) {
        delegate = new InnerCache(config.getMaxEntries());
    }

    public void add(Object key, Object value) {
        delegate.put(key, new SoftReference<Object>(value));
    }

    public Object get(Object key) {
        SoftReference<Object> softReference = delegate.get(key);
        if(softReference == null) return null;
        Object retorno = softReference.get();
        
        if(retorno == null) delegate.remove(key);
        
		return retorno;
    }
}
