package org.washcom.util;

import java.util.Collection;

/**
 * Filters a collection into a potentially smaller collection, but not necessarily.
 * 
 * @author Joe
 */
public interface CollectionFilter<T, C extends Collection<T>> {
    
    C filter(C candidates);
    
}
