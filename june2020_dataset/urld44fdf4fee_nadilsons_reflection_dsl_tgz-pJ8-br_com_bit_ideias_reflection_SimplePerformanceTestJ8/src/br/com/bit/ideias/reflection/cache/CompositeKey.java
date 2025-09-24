package br.com.bit.ideias.reflection.cache;

import java.util.Arrays;

/**
 * @author Leonardo Campos
 * @date 24/11/2009
 */
public class CompositeKey {
    private final Object[] keys;

    public CompositeKey(Object...keys) {
        this.keys = keys;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(keys);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CompositeKey other = (CompositeKey) obj;
        if (!Arrays.equals(keys, other.keys))
            return false;
        return true;
    }
}
