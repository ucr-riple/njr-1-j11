package br.com.bit.ideias.reflection.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author Leonardo Campos
 * @date 02/08/2009
 */
public class CollectionUtil {
    public static boolean isEmpty(Map<?,?> map) {
        return map == null || map.isEmpty();
    }
    
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

}
