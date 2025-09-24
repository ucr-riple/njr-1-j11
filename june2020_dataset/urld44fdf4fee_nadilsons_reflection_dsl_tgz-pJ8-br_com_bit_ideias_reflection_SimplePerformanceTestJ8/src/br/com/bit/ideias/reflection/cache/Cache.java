package br.com.bit.ideias.reflection.cache;

/**
 * @author Leonardo Campos
 * @date 24/11/2009
 */
public interface Cache {
    void add(Object key, Object value);
    Object get(Object key);
}
